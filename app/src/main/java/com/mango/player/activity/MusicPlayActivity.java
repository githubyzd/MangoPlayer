package com.mango.player.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.adapter.MusicDetailListPopuAdapter;
import com.mango.player.bean.Music;
import com.mango.player.bean.MusicServiceBean;
import com.mango.player.bean.UpdateViewBean;
import com.mango.player.util.AppUtil;
import com.mango.player.util.ApplicationConstant;
import com.mango.player.util.LogUtil;
import com.mango.player.util.PopupHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mango.player.R.id.music_play;
import static com.mango.player.R.id.recyclerview;
import static com.mango.player.bean.PlayMode.PALY_SEEK;
import static com.mango.player.bean.PlayMode.PLAY_INDEX;
import static com.mango.player.bean.PlayMode.PLAY_MUSIC;
import static com.mango.player.bean.PlayMode.PLAY_NEXT;
import static com.mango.player.bean.PlayMode.PLAY_PRE;
import static com.mango.player.util.MusicController.mContext;

public class MusicPlayActivity extends AppCompatActivity implements View.OnClickListener, MusicDetailListPopuAdapter.OnItemClickListener, SeekBar.OnSeekBarChangeListener {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.list)
    ImageView list;
    @BindView(R.id.singer)
    TextView singer;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.like)
    ImageView like;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.progress)
    SeekBar progress;
    @BindView(R.id.pattern)
    ImageView pattern;
    @BindView(R.id.music_pre)
    ImageView musicPre;
    @BindView(R.id.music_next)
    ImageView musicNext;
    @BindView(R.id.sound_effect)
    ImageView soundEffect;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.current_duration)
    TextView currentDuration;
    @BindView(R.id.duration)
    TextView duration;
    @BindView(R.id.thumbnail)
    ImageView thumbnail;
    @BindView(R.id.music_play)
    ImageView musicPlay;
    private ArrayList<Music> musics;
    private Music music;
    private int currentIndex = -1;
    private View contentView;
    private RecyclerView recyclerView;
    private MusicServiceBean serviceBean = new MusicServiceBean();
    private PopupHelper popupHelper;
    private TextView index;
    private ImageView delete;
    private ImageView save;
    private ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initData();
        initView();
    }

    private void initView() {
        progress.setOnSeekBarChangeListener(this);
        name.setText(music.getName());
        singer.setText(music.getArtist());
        duration.setText(AppUtil.timeLenghtFormast(music.getDuration()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private void initData() {
        Intent intent = getIntent();
        currentIndex = intent.getIntExtra(ApplicationConstant.MUSIC_INDEX, -1);
        musics = App.musicList;
        music = musics.get(currentIndex);
    }

    @OnClick(music_play)
    void play() {
        serviceBean.setPlayMode(PLAY_MUSIC);
        postPlay();
    }

    @OnClick(R.id.music_pre)
    void playPre() {
        serviceBean.setPlayMode(PLAY_PRE);
        postPlay();
    }

    @OnClick(R.id.music_next)
    void playNext() {
        serviceBean.setPlayMode(PLAY_NEXT);
        postPlay();
    }

    @OnClick(R.id.list)
    void shoList() {
        if (contentView == null) {
            contentView = View.inflate(mContext, R.layout.music_detail_play_list_popu, null);
            recyclerView = (RecyclerView) contentView.findViewById(recyclerview);
            index = (TextView) contentView.findViewById(R.id.index);
            delete = (ImageView) contentView.findViewById(R.id.delete);
            save = (ImageView) contentView.findViewById(R.id.save);
            close = (ImageView) contentView.findViewById(R.id.close);

            delete.setOnClickListener(this);
            save.setOnClickListener(this);
            close.setOnClickListener(this);
            initRecyclerview();
        }
        index.setText(" (" + currentIndex + "/" + musics.size() + ")");
        if (music.getThumbnail() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                contentView.setBackground(new BitmapDrawable(music.getThumbnail()));
            }
        } else {
            contentView.setBackgroundResource(R.drawable.background4);
        }
        popupHelper = new PopupHelper.Builder(mContext)
                .contentView(contentView)
                .width(ViewGroup.LayoutParams.MATCH_PARENT)
                .height(ViewGroup.LayoutParams.MATCH_PARENT)
                .anchorView(list)
                .parentView(list)
                .gravity(Gravity.TOP)
                .outSideTouchable(true)
                .animationStyle(R.style.anim_top)
                .build()
                .showAtLocation();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 100)
    public void updateView(UpdateViewBean viewBean) {
//        LogUtil.logByD("updateView: " + viewBean.toString());
        music = viewBean.getMusic();
        currentIndex = viewBean.getIndex();
        if (viewBean.getPlaying()) {
            musicPlay.setImageResource(R.drawable.pause_music);
        } else {
            musicPlay.setImageResource(R.drawable.play_music);
        }
        name.setText(music.getName());
        singer.setText(music.getArtist());
        progress.setMax(viewBean.getDuration());
        progress.setProgress(viewBean.getCurrentDuration());
        duration.setText(AppUtil.timeLenghtFormast(viewBean.getDuration()));
        currentDuration.setText(AppUtil.timeLenghtFormast(viewBean.getCurrentDuration()));
        if (music.getThumbnail() == null) {
            thumbnail.setImageResource(R.drawable.holder);
        }else {
            thumbnail.setImageBitmap(music.getThumbnail());
        }
    }

    private void initRecyclerview() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        MusicDetailListPopuAdapter mAdapter = new MusicDetailListPopuAdapter(musics);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete:

                break;
            case R.id.save:

                break;
            case R.id.close:
                popupHelper.dismiss();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (popupHelper != null) {
            if (popupHelper.isShowing()) {
                popupHelper.dismiss();
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.item_music_list_popu:
                currentIndex = position;
                serviceBean.setPlayMode(PLAY_INDEX);
                serviceBean.setIndex(currentIndex);
                postPlay();
                break;
            case R.id.more:
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            LogUtil.logByD(progress + "");
            serviceBean.setPlayMode(PALY_SEEK);
            serviceBean.setMsec(progress);
            postPlay();
        }
    }

    private void postPlay() {
        EventBus.getDefault().post(serviceBean);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
