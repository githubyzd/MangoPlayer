package com.mango.player.util;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.activity.MusicService;
import com.mango.player.bean.Music;

import java.util.List;

/**
 * Created by yzd on 2017/10/19 0019.
 */

public class MusicController implements MusicService.OnCompletionListenner, View.OnClickListener {
    private static MusicController instance;
    private static View mRooView, progress;
    private static List<Music> mMusics;
    private ImageView music_img, music_play, music_next, music_list;
    private TextView music_name, music_author;
    private int currentIndex;
    private ServiceConnection conn;
    private MusicService mMusicService;
    private Music music;
    private Context mContext;

    private MusicController() {
    }

    public static MusicController getInstance() {
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
        mContext = rootView.getContext();
        progress = mRooView.findViewById(R.id.progress);
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
        }
        updateView();
    }

    public void updateView() {
        if (music.getThumbnail() != null) {
            music_img.setImageBitmap(music.getThumbnail());
        } else {
            music_img.setImageResource(R.drawable.music);
        }
        if (isPlaying()){
            music_play.setImageResource(R.drawable.pause_music);
        }else {
            music_play.setImageResource(R.drawable.play_music);
        }
        music_name.setText(music.getName());
        music_author.setText(music.getArtist());
    }

    @Override
    public void onCompletion() {
        playNext();
    }

    private void playNext(){
        if (currentIndex == mMusics.size() - 1) {
            currentIndex = 0;
        } else {
            currentIndex++;
        }
        playMusic(currentIndex);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.music_play_controller:
                // TODO: 2017/10/19 0019
                LogUtil.logByD("跳转详情界面");
                break;
            case R.id.music_play:
                if (isPlaying()){
                    pause();
                }else {
                    play();
                }
                break;
            case R.id.music_next:
                playNext();
                break;
            case R.id.music_list:
                break;
        }
    }

    public void play() {
        if (mMusicService != null)
            mMusicService.conPlay();
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
            mMusicService.playMusic(music.getPath());
            updateView();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMusicService = null;
        }
    }

}
