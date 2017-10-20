package com.mango.player.util;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
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

import java.util.List;
import java.util.TimerTask;

import static com.mango.player.R.id.recyclerview;

/**
 * Created by yzd on 2017/10/19 0019.
 */

public class MusicController implements MusicService.OnCompletionListenner, View.OnClickListener, MediaPlayer.OnPreparedListener, MusicListPopuAdapter.OnItemClickListener {
    private static MusicController instance;
    private static View mRooView;
    private static ProgressBar progress;
    private static List<Music> mMusics;
    private ImageView music_img, music_play, music_next, music_list;
    private TextView music_name, music_author;
    private int currentIndex;
    private ServiceConnection conn;
    private MusicService mMusicService;
    private Music music;
    public static Activity mContext;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 101) {
                progress.setProgress(mMusicService.getCurrentPosition());
            }
        }
    };
    private TimerTask mTimerTask;
    private PopupHelper popupHelper;
    private View contentView;
    private RecyclerView recyclerView;
    private TextView close;
    private TextView tv_random;
    private ImageView iv_random;
    private ImageView delete;
    private MusicListPopuAdapter mAdapter;

    private MusicController() {
    }

    public static MusicController getInstance(Activity activity) {
        if (instance == null) {
            synchronized (MusicController.class) {
                if (instance == null) {
                    instance = new MusicController();
                }
            }
        }
        mContext = activity;
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

    public void initData(List<Music> musics) {
        mMusics = musics;
    }

    public void playMusic(int index) {
        currentIndex = index;
        music = mMusics.get(index);
        if (mMusicService == null) {
            Intent intent = new Intent(mContext, MusicService.class);
            conn = new MyConnection();
            mContext.bindService(intent, conn, Service.BIND_AUTO_CREATE);
        } else {
            mMusicService.setOnCompletionListenner(this);
            mMusicService.playMusic(music.getPath());
            mMusicService.setOnPreparedListener(this);
        }
        updateView();
    }

    public void updateView() {
        ACache.getInstance(mContext).put(ApplicationConstant.MUSIC_INDEX, currentIndex + "");
        if (music.getThumbnail() != null) {
            music_img.setImageBitmap(music.getThumbnail());
        } else {
            music_img.setImageResource(R.drawable.music);
        }
        if (isPlaying()) {
            music_play.setImageResource(R.drawable.pause_music);
        } else {
            music_play.setImageResource(R.drawable.play_music);
        }
        music_name.setText(music.getName());
        music_author.setText(music.getArtist());
    }

    private void setProgress() {
        progress.setMax(mMusicService.getDuration());
        progress.setProgress(mMusicService.getCurrentPosition());
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(101);
            }
        };
        App.timer.schedule(mTimerTask, 0, 1000);
    }

    @Override
    public void onCompletion() {
        playNext();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        setProgress();
    }

    private void playNext() {
        if (currentIndex == mMusics.size() - 1) {
            currentIndex = 0;
        } else {
            currentIndex++;
        }
        playMusic(currentIndex);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.item_music_list_popu:
                currentIndex = position;
                music = mMusics.get(currentIndex);
                playMusic(currentIndex);
                break;
            case R.id.delete:
                if (position == currentIndex){
                    mMusics.remove(position);
                    playMusic(currentIndex);
                }else {
                    mMusics.remove(position);
                    currentIndex = mMusics.indexOf(music);
                }
                mAdapter.setData(mMusics);
                mAdapter.setIndex(currentIndex);
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.music_play_controller:
                // TODO: 2017/10/20 0020  
                mContext.startActivity(new Intent(mContext, MusicPlayActivity.class));
                break;
            case R.id.music_play:
                if (isPlaying()) {
                    pause();
                } else {
                    play();
                }
                break;
            case R.id.music_next:
                playNext();
                break;
            case R.id.music_list:
                showList();
                break;
            case R.id.close:
                popupHelper.dismiss();
                break;
        }
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

    public void play() {
        if (mMusicService != null) {
            if (isPlaying()) {
                mMusicService.conPlay();
            } else {
                mMusicService.conPlay();
            }
        } else {
            playMusic(currentIndex);
        }
        updateView();
    }

    public void rePlay() {
        if (mMusicService != null)
            mMusicService.playMusic(music.getPath());
    }

    public void pause() {
        if (mMusicService != null)
            mMusicService.pauseMusic();
        updateView();
    }

    public void stop() {
        if (mMusicService != null)
            mMusicService.stopMusic();
    }

    public void seekTo(int msecm) {
        if (mMusicService != null)
            mMusicService.seekTo(msecm);
    }

    public boolean isPlaying() {
        if (mMusicService != null)
            return mMusicService.isPlaying();
        return false;
    }

    private class MyConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtil.logByD("music service 绑定成功");
            MusicService.MusiceBinder binder = (MusicService.MusiceBinder) service;
            mMusicService = binder.getService();
            mMusicService.setOnCompletionListenner(MusicController.this);
            mMusicService.setOnPreparedListener(MusicController.this);
            mMusicService.playMusic(music.getPath());
            updateView();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMusicService = null;
        }
    }

    public void setCurrentIndex(int index) {
        this.currentIndex = index;
        music = mMusics.get(currentIndex);
    }

}
