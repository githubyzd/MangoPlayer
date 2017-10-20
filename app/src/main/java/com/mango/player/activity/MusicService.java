package com.mango.player.activity;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

public class MusicService extends Service {
    private MusiceBinder binder = new MusiceBinder();
    private MediaPlayer mMediaPlayer;
    private OnCompletionListenner mListenner;
    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(new MyCompletionListener());
    }

    public class MusiceBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
    }

    private class MyCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            if (mListenner != null){
                mListenner.onCompletion();
            }
        }
    }

    public interface OnCompletionListenner{
        void onCompletion();
    }

    public void setOnCompletionListenner(OnCompletionListenner listenner){
        mListenner = listenner;
    }
    public void playMusic(String path){
        mMediaPlayer.reset();
        try {
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }

    public void conPlay(){
        mMediaPlayer.start();
    }

    public void pauseMusic(){
        mMediaPlayer.pause();
    }

    public void stopMusic(){
        mMediaPlayer.stop();
    }

    public void setOnPreparedListener (MediaPlayer.OnPreparedListener listener){
        mMediaPlayer.setOnPreparedListener(listener);
    }

    public void seekTo(int msec){
        mMediaPlayer.seekTo(msec);
    }

    public int getDuration(){
        return mMediaPlayer.getDuration();
    }

    public int getCurrentPosition(){
        return mMediaPlayer.getCurrentPosition();
    }

    public void setLooping(boolean isLooping){
        mMediaPlayer.setLooping(isLooping);
    }

    public void setVolume(float left,float right){
        mMediaPlayer.setVolume(left,right);
    }

    public boolean isPlaying(){
        return mMediaPlayer.isPlaying();
    }
}
