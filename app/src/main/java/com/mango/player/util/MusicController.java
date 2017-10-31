package com.mango.player.util;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.activity.App;
import com.mango.player.activity.MusicPlayActivity;
import com.mango.player.activity.MusicService;
import com.mango.player.adapter.MusicListPopuAdapter;
import com.mango.player.bean.Music;
import com.mango.player.bean.MusicServiceBean;
import com.mango.player.bean.UpdateViewBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import static com.mango.player.R.id.recyclerview;
import static com.mango.player.bean.PlayMode.PLAY_INDEX;
import static com.mango.player.bean.PlayMode.PLAY_MUSIC;
import static com.mango.player.bean.PlayMode.PLAY_NEXT;
import static com.mango.player.bean.PlayMode.SET_INDEX;

/**
 * Created by yzd on 2017/10/19 0019.
 */

public class MusicController implements View.OnClickListener,  MusicListPopuAdapter.OnItemClickListener {
    private static MusicController instance;
    private static View mRooView;
    private static ProgressBar progress;
    private static ArrayList<Music> mMusics;
    private ImageView music_img, music_play, music_next, music_list;
    private TextView music_name, music_author;
    private int currentIndex;
    private Music music;
    public static Activity mContext;
    private MusicServiceBean serviceBean = new MusicServiceBean();
    private PopupHelper popupHelper;
    private View contentView;
    private RecyclerView recyclerView;
    private TextView close;
    private TextView tv_random;
    private ImageView iv_random;
    private ImageView delete;
    private MusicListPopuAdapter mAdapter;

    private MusicController() {
        EventBus.getDefault().register(this);
        Intent intent = new Intent(mContext,MusicService.class);
        mContext.startService(intent);
    }

    public static MusicController getInstance(Activity activity) {
        mContext = activity;
        if (instance == null) {
            synchronized (MusicController.class) {
                if (instance == null) {
                    instance = new MusicController();
                }
            }
        }
        return instance;
    }

    public void initView(View rootView) {
        mRooView = rootView;
        progress = (ProgressBar) mRooView.findViewById(R.id.progress);
        music_img = (ImageView) mRooView.findViewById(R.id.music_img);
        music_play = (ImageView) mRooView.findViewById(R.id.music_play);
        music_next = (ImageView) mRooView.findViewById(R.id.music_next);
        music_list = (ImageView) mRooView.findViewById(R.id.music_list);
        music_name = (TextView) mRooView.findViewById(R.id.music_name);
        music_author = (TextView) mRooView.findViewById(R.id.music_author);
        initListenner();
    }

    private void initListenner() {
        mRooView.setOnClickListener(this);
        music_play.setOnClickListener(this);
        music_next.setOnClickListener(this);
        music_list.setOnClickListener(this);
    }

    public void initData(ArrayList<Music> musics) {
        mMusics = musics;
        EventBus.getDefault().postSticky(mMusics);
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 100)
    public void updateView(UpdateViewBean viewBean){
//        LogUtil.logByD("updateView: " + viewBean.toString());
        music = viewBean.getMusic();
        currentIndex = viewBean.getIndex();
        if (music.getThumbnail() != null) {
            music_img.setImageBitmap(music.getThumbnail());
        } else {
            music_img.setImageResource(R.drawable.music);
        }
        if (viewBean.getPlaying()) {
            music_play.setImageResource(R.drawable.pause_music);
        } else {
            music_play.setImageResource(R.drawable.play_music);
        }
        music_name.setText(music.getName());
        music_author.setText(music.getArtist());
        progress.setMax(viewBean.getDuration());
        progress.setProgress(viewBean.getCurrentDuration());
    }

    public void initController(){
        if (music.getThumbnail() != null) {
            music_img.setImageBitmap(music.getThumbnail());
        } else {
            music_img.setImageResource(R.drawable.music);
        }

        music_name.setText(music.getName());
        music_author.setText(music.getArtist());
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.item_music_list_popu:
                serviceBean.setPlayMode(PLAY_INDEX);
                serviceBean.setIndex(position);
                postPlay();
                break;
            case R.id.delete:
                if (position == currentIndex) {
                    mMusics.remove(position);
                } else {
                    mMusics.remove(position);
                    currentIndex = mMusics.indexOf(music);
                }
                EventBus.getDefault().post(mMusics);
                serviceBean.setPlayMode(PLAY_INDEX);
                serviceBean.setIndex(currentIndex);
                postPlay();
                mAdapter.setData(mMusics);
                mAdapter.setIndex(currentIndex);
                App.musicList = mMusics;
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.music_play_controller:
                // TODO: 2017/10/20 0020
                Intent intent = new Intent(mContext, MusicPlayActivity.class);
                intent.putExtra(ApplicationConstant.MUSIC_INDEX, currentIndex);
                mContext.startActivity(intent);
                break;
            case R.id.music_play:
                serviceBean.setPlayMode(PLAY_MUSIC);
                postPlay();
                break;
            case R.id.music_next:
                serviceBean.setPlayMode(PLAY_NEXT);
                postPlay();
                break;
            case R.id.music_list:
                showList();
                break;
            case R.id.close:
                popupHelper.dismiss();
                break;
        }
    }

    private void postPlay() {
        EventBus.getDefault().post(serviceBean);
    }

    private void showList() {
        if (contentView == null) {
            contentView = View.inflate(mContext, R.layout.music_play_list_popu, null);
            recyclerView = (RecyclerView) contentView.findViewById(recyclerview);
            close = (TextView) contentView.findViewById(R.id.close);
            tv_random = (TextView) contentView.findViewById(R.id.tv_random);
            iv_random = (ImageView) contentView.findViewById(R.id.iv_random);
            delete = (ImageView) contentView.findViewById(R.id.delete);
            initRecyclerview();

            close.setOnClickListener(this);
            tv_random.setOnClickListener(this);
            iv_random.setOnClickListener(this);
            delete.setOnClickListener(this);
        }
        popupHelper = new PopupHelper.Builder(mContext)
                .contentView(contentView)
                .width(ViewGroup.LayoutParams.MATCH_PARENT)
                .height(AppUtil.getScreenHeight(mContext) / 2)
                .anchorView(mRooView)
                .parentView(mRooView)
                .gravity(Gravity.BOTTOM)
                .outSideTouchable(true)
                .animationStyle(R.style.anim_bottom)
                .build()
                .showAtLocation();
    }

    private void initRecyclerview() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MusicListPopuAdapter(mMusics);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    public void setCurrentIndex(int index) {
        this.currentIndex = index;
        music = mMusics.get(currentIndex);
        serviceBean.setPlayMode(SET_INDEX);
        serviceBean.setIndex(currentIndex);
        EventBus.getDefault().postSticky(serviceBean);
    }

}
