package com.mango.player.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.bean.Video;
import com.mango.player.util.CustomMediaController;
import com.mango.player.util.ExceptionUtil;
import com.mango.player.util.LogUtil;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

public class VideoPlayActivity extends AppCompatActivity implements MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener {
    //视频地址
    private String path = "http://baobab.wdjcdn.com/145076769089714.mp4";
    private Uri uri;
    private ProgressBar pb;
    private TextView downloadRateView, loadRateView;
    private CustomMediaController mCustomMediaController;
    private VideoView mVideoView;
    private Video video;
    private long currentPosition;
    private boolean isOrientation = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
        currentPosition = savedInstanceState.getLong("currentPosition");
        LogUtil.logByD("test:onCreate" + currentPosition);
        initConfig();
        setContentView(R.layout.activity_video_play);
        //获取上一次保存的进度
        initView();
        initData();
    }

    private void initConfig() {
        //定义全屏参数
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window = getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        //必须写这个，初始化加载库文件
        Vitamio.isInitialized(this);
    }

    //初始化控件
    private void initView() {
        mVideoView = (VideoView) findViewById(R.id.buffer);
        mCustomMediaController = new CustomMediaController(this, mVideoView, this);
        pb = (ProgressBar) findViewById(R.id.probar);
        downloadRateView = (TextView) findViewById(R.id.download_rate);
        loadRateView = (TextView) findViewById(R.id.load_rate);
    }

    //初始化数据
    private void initData() {
        if (!isOrientation) {
            Intent intent = getIntent();
            if (intent == null) {
                ExceptionUtil.illegaArgument("intent is null");
            }
            video = intent.getParcelableExtra("video");
        }

        mCustomMediaController.setVideoName(video.getName());
        uri = Uri.parse(video.getPath());
        //设置视频播放地址
        mVideoView.setVideoURI(uri);
        mCustomMediaController.show(5000);
        mVideoView.setMediaController(mCustomMediaController);
        //高画质
        mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
        mVideoView.requestFocus();
        mVideoView.setOnInfoListener(this);
        mVideoView.setOnBufferingUpdateListener(this);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                    pb.setVisibility(View.VISIBLE);
                    downloadRateView.setText("");
                    loadRateView.setText("");
                    downloadRateView.setVisibility(View.VISIBLE);
                    loadRateView.setVisibility(View.VISIBLE);

                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                mVideoView.start();
                pb.setVisibility(View.GONE);
                downloadRateView.setVisibility(View.GONE);
                loadRateView.setVisibility(View.GONE);
                break;
            case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                downloadRateView.setText("" + extra + "kb/s" + "  ");
                break;
        }
        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        loadRateView.setText(percent + "%");
    }




    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        LogUtil.logByE("test屏幕切换");
        //屏幕切换时，设置全屏
        if (mVideoView != null) {
            mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        }
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.logByD("test:onDestroy");
    }
    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.logByD("test:onPause");
    }
    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.logByD("test:onResume");
    }
    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.logByD("test:onStart");
    }
    @Override
    protected void onStop() {
        super.onStop();
       LogUtil.logByD("test:onStop");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        currentPosition = mVideoView.getCurrentPosition();
        outState.putLong("currentPosition",currentPosition);
        LogUtil.logByD("test:onSaveInstanceState" + currentPosition);
    }
}
