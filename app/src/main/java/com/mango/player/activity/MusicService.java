package com.mango.player.activity;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import com.mango.player.R;
import com.mango.player.bean.Music;
import com.mango.player.bean.MusicServiceBean;
import com.mango.player.bean.PlayMode;
import com.mango.player.bean.UpdateViewBean;
import com.mango.player.util.ACache;
import com.mango.player.util.ApplicationConstant;
import com.mango.player.util.ExceptionUtil;
import com.mango.player.util.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MusicService extends Service {
    private MusiceBinder binder = new MusiceBinder();
    private MediaPlayer mMediaPlayer;
    private Notification.Builder mBuilder;
    private Notification mNotification;

    /**
     * 播放状态标志
     */
    public static final int STATE_PLAYING = 0;
    //    public static final int STATE_PAUSE = 1;
    public static final int STATE_STOP = 2;
    //播放状态变量
    private int play_state = STATE_STOP;

    //循环模式变量
    private int play_mode = MODE_LOOP_ALL;
    /**
     * 循环模式标志
     */
    public static final int MODE_LOOP_ALL = 0;
    public static final int MODE_LOOP_ONE = 1;
    public static final int MODE_RADOM = 2;
    private boolean isFirstPlay = true;

    private List<Music> musics = new ArrayList<>();
    private Music music = null;
    private int currentIndex = -1;
    private UpdateViewBean viewBean = new UpdateViewBean();
    private TimerTask mTimerTask;
    private Timer mTimer;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.logByD("onCreat");
        EventBus.getDefault().register(this);
        initMediaPlayer();
        simpleNotification();
    }

    public class MusiceBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    /**
     * 初始化播放器
     */
    private void initMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(new MyCompletionListener());
    }

    /**
     * 开启 前台服务
     */
    private void simpleNotification() {
        mBuilder = new Notification.Builder(this);
        //状态栏提示
        mBuilder.setTicker("RaymondCQK 正在运行");
        //下拉通知栏标题
        mBuilder.setContentTitle("暂无歌曲");
        //下拉通知正文
        mBuilder.setContentText("...");
        //内容摘要（低版本不一定显示）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mBuilder.setSubText("...");
        }
        //状态栏小图标
        mBuilder.setSmallIcon(R.mipmap.blueball_72px);
        //下拉通知栏图标
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.blueball_72px);
        mBuilder.setLargeIcon(icon);
        //跳转intent
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent, 0);//参数2：requestCode 参数4：flag
        mBuilder.setContentIntent(pendingIntent);

        //开启Notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mNotification = mBuilder.build();
        }
        //        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //        manager.notify(1,notification);//参数1：样式
        startForeground(1, mNotification);
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
        EventBus.getDefault().unregister(this);
    }


    private class MyCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            playNext();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 100)
    public void playWithMode(MusicServiceBean bean) {
        LogUtil.logByD("playWithMode: " + bean.toString());
        PlayMode playMode = bean.getPlayMode();
        switch (playMode) {
            case PLAY_MUSIC:
                playMusic();
                break;
            case PLAY_NEXT:
                playNext();
                break;
            case PLAY_PRE:
                playPreview();
                break;
            case PLAY_STOP:
                mMediaPlayer.stop();
                break;
            case PLAY_INDEX:
                playWithIndex(bean.getIndex());
                break;
            case PALY_SEEK:
                seekTo(bean.getMsec());
                break;
            case SET_INDEX:
                setIndex(bean.getIndex());
                break;
        }
    }

    private void updateView() {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    postUpdateView();
                }
            };
            mTimer.schedule(mTimerTask, 0, 1000);
        }
        if (mMediaPlayer.isPlaying()) {

        } else {
            mTimer.cancel();
            mTimer = null;
            mTimerTask.cancel();
            mTimerTask = null;
            postUpdateView();
        }
    }

    private void postUpdateView() {
        viewBean.setIndex(currentIndex);
        viewBean.setMusic(music);
        viewBean.setDuration(mMediaPlayer.getDuration());
        viewBean.setCurrentDuration(mMediaPlayer.getCurrentPosition());
        viewBean.setPlaying(mMediaPlayer.isPlaying());
        EventBus.getDefault().post(viewBean);
    }

    private void playMusic() {
        if (isFirstPlay) {
            play();
        } else {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
            } else {
                mMediaPlayer.start();
            }
        }
        updateView();
        ACache.getInstance(this).put(ApplicationConstant.MUSIC_INDEX, currentIndex + "");
    }

    private void playPreview() {
        if (currentIndex == 0) {
            currentIndex = musics.size() - 1;
        } else {
            currentIndex--;
        }
        play();
    }

    private void playNext() {
        if (currentIndex == musics.size() - 1) {
            currentIndex = 0;
        } else {
            currentIndex++;
        }
        play();
    }

    private void playWithIndex(int index) {
        if (index >= musics.size()) {
            ExceptionUtil.illegaArgument("index is outsize if list");
        }
        currentIndex = index;
        music = musics.get(currentIndex);
        play();
    }

    private void play() {
        mMediaPlayer.reset();
        music = musics.get(currentIndex);
        try {
            mMediaPlayer.setDataSource(music.getPath());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
        isFirstPlay = false;
        updateView();
        ACache.getInstance(this).put(ApplicationConstant.MUSIC_INDEX, currentIndex + "");
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 100)
    public void setMusic(List<Music> musics) {
        LogUtil.logByD("setMusic: " + musics.size());
        this.musics = musics;
    }

    private void setIndex(int index) {
        this.currentIndex = index;
    }

    public void conPlay() {
        mMediaPlayer.start();
    }

    public void pauseMusic() {
        mMediaPlayer.pause();
    }

    public void stopMusic() {
        mMediaPlayer.stop();
    }

    public void setOnPreparedListener(MediaPlayer.OnPreparedListener listener) {
        mMediaPlayer.setOnPreparedListener(listener);
    }

    public void seekTo(int msec) {
        mMediaPlayer.seekTo(msec);
    }

    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    /**
     * 获取当前循环模式
     *
     * @return
     */
    public int getPlay_mode() {
        return play_mode;
    }

    /**
     * 获取当前播放器状态：暂停、播放
     *
     * @return
     */
    public int getPlay_state() {
        return play_state;
    }

    public void setLooping(boolean isLooping) {
        mMediaPlayer.setLooping(isLooping);
    }

    public void setVolume(float left, float right) {
        mMediaPlayer.setVolume(left, right);
    }

    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }
}
