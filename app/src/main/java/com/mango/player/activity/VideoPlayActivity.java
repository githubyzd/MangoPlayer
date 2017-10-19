package com.mango.player.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.adapter.VideoNativePopuAdapter;
import com.mango.player.bean.Video;
import com.mango.player.util.AppUtil;
import com.mango.player.util.ApplicationConstant;
import com.mango.player.util.CustomMediaController;
import com.mango.player.util.ExceptionUtil;
import com.mango.player.util.LogUtil;
import com.mango.player.view.DividerItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;


public class VideoPlayActivity extends AppCompatActivity implements MediaPlayer.OnInfoListener,
        MediaPlayer.OnBufferingUpdateListener, View.OnClickListener, VideoNativePopuAdapter.OnItemClickListener {
    @BindView(R.id.buffer)
    VideoView mVideoView;
    @BindView(R.id.probar)
    ProgressBar pb;
    @BindView(R.id.download_rate)
    TextView downloadRateView;
    @BindView(R.id.load_rate)
    TextView loadRateView;
    @BindView(R.id.container)
    RelativeLayout container;
    //视频地址
//    private String path = "http://baobab.wdjcdn.com/145076769089714.mp4";
    private String path = "http://www.t02y.com/get_file/3/fda4429d142ecdc0b178b8f2b4902b53/46000/46563/46563.mp4/?rnd=1508336799051";
    private Uri uri;
    private CustomMediaController mCustomMediaController;
    private MediaPlayer mMediaPlayer;
    private int currentPosition;
    private Video video;
    private List<Video> videos;
    private float playSpeed = 1.0f;
    private VideoNativePopuAdapter mPopuAdapter;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            initConfig();
        setContentView(R.layout.activity_video_play);
        ButterKnife.bind(this);
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
        Intent intent = getIntent();
        if (intent == null) {
            ExceptionUtil.illegaArgument("intent is null");
        }
        Bundle bundle = intent.getBundleExtra(ApplicationConstant.VIDEO_DATA_KEY);
        if (bundle == null) {
            ExceptionUtil.illegaArgument("bundle is null");
        }
        videos = bundle.getParcelableArrayList(ApplicationConstant.VIDEO_LIST_KEY);
        currentPosition = bundle.getInt(ApplicationConstant.VIDEO_POSITION_KEY);
        video = videos.get(currentPosition);

        toPlay();
    }

    private void toPlay() {
        mCustomMediaController.setVideoName(video.getName());
//        uri = Uri.parse(video.getPath());
        uri = Uri.parse(path);
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
                mMediaPlayer = mediaPlayer;
                mediaPlayer.setPlaybackSpeed(playSpeed);
                enablePre(true);
                enableBeh(true);
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
        //屏幕切换时，设置全屏
        if (mVideoView != null) {
            mVideoView.setVideoLayout(VideoView.VIDEO_LAYOUT_SCALE, 0);
        }
        super.onConfigurationChanged(newConfig);
    }

    //popu的item的点击事件
    @Override
    public void onItemClick(View view, int position) {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        switch (view.getId()) {
            case R.id.video_native_item_popu:
                video = videos.get(position);
                toPlay();
                break;
            case R.id.tv_delete:
                deletePlayList(position);
                break;
        }
    }

    //播放控制器的点击事件
    public void onItemClick(View v) {
        switch (v.getId()) {
            case R.id.mediacontroller_list:
                showList();
                break;
            case R.id.mediacontroller_more:
                showMore();
                break;
            case R.id.speed_up:
                changePlaySpeed(true);
                break;
            case R.id.speed_increase:
                changePlaySpeed(false);
                break;
            case R.id.lock:
                LogUtil.logByD("lock");
                // TODO: 2017/10/16 0016 锁屏
                break;
            case R.id.pre:
                playPre();
                break;
            case R.id.beh:
                playBeh();
                break;
            case R.id.back_speed:
                mVideoView.seekTo(mVideoView.getCurrentPosition() - mVideoView.getDuration() / 50);
                break;
            case R.id.speed:
                mVideoView.seekTo(mVideoView.getCurrentPosition() + mVideoView.getDuration() / 50);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popu_list_close:
                if (alertDialog != null)
                    alertDialog.dismiss();
                break;
            case R.id.tv_setting:
                // TODO: 2017/10/16 0016 设置
                break;
            case R.id.tv_helper:
                // TODO: 2017/10/16 0016 帮助
                break;
            case R.id.tv_quit:
                finish();
                break;
        }
    }

    private void deletePlayList(int position) {
        if (position == currentPosition) {
            if (videos.size() == 1) {
                return;
            }
            if (position == videos.size() - 1) {
                currentPosition = 0;
            } else {
                currentPosition = position + 1;
            }
            video = videos.get(position);
            toPlay();

        }
        videos.remove(position);
        if (mPopuAdapter != null) {
            mPopuAdapter.setData(videos);
        }
    }


    private void playPre() {
        if (enablePre(false)) {
            currentPosition = currentPosition - 1;
            video = videos.get(currentPosition);
            toPlay();
        }
    }

    private void playBeh() {
        if (enableBeh(false)) {
            currentPosition = currentPosition + 1;
            video = videos.get(currentPosition);
            toPlay();
        }
    }

    private void changePlaySpeed(boolean isUp) {
        playSpeed = AppUtil.changePlaySpeed(playSpeed, isUp);
        mMediaPlayer.setPlaybackSpeed(playSpeed);
        mCustomMediaController.setPlaySpeed(playSpeed);
    }

    private void showList() {
        View contentView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).
                inflate(getResources().getIdentifier("video_native_list", "layout", getPackageName()), null);
        ImageView iv_close = (ImageView) contentView.findViewById(R.id.popu_list_close);
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.popu_list_recycler);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.HORIZONTAL_LIST));

        mPopuAdapter = new VideoNativePopuAdapter(videos);
        recyclerView.setAdapter(mPopuAdapter);
        mPopuAdapter.setOnItemClickListener(this);

        iv_close.setOnClickListener(this);
        alertDialog = new AlertDialog.Builder(this, R.style.dialog)
                .setView(contentView)
                .show();
    }

    private void showMore() {
        View contentView = View.inflate(this,R.layout.video_native_more,null);
        TextView setting = (TextView) contentView.findViewById(R.id.tv_setting);
        TextView helper = (TextView) contentView.findViewById(R.id.tv_helper);
        TextView quit = (TextView) contentView.findViewById(R.id.tv_quit);

        setting.setOnClickListener(this);
        helper.setOnClickListener(this);
        quit.setOnClickListener(this);


        AlertDialog dialog = new AlertDialog.Builder(this,R.style.dialog2).create();
        dialog.setView(contentView);
        dialog.show();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值

        Window dialogWindow = dialog.getWindow();

        p.height = (int) (d.getHeight() * 0.5);   //高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.6);    //宽度设置为屏幕的0.5
        p.x = 0;
        p.y = 0;
        dialogWindow.setAttributes(p);     //设置生效
    }


    private boolean enablePre(boolean isInit) {
        if (videos.size() == 1) {
            if (!isInit) {
                AppUtil.showToastMsg(this, "当前列表仅有本视频");
            }
            mCustomMediaController.enablePre(false);
            mCustomMediaController.enableBeh(false);
            return false;
        }
        if (currentPosition == 0) {
            if (!isInit) {
                AppUtil.showToastMsg(this, "没有上一个视频了");
            }
            mCustomMediaController.enablePre(false);
            mCustomMediaController.enableBeh(true);
            return false;
        }

        return true;

    }

    public boolean enableBeh(boolean isInit) {
        if (videos.size() == 1) {
            if (!isInit) {
                AppUtil.showToastMsg(this, "当前列表仅有本视频");
            }
            mCustomMediaController.enablePre(false);
            mCustomMediaController.enableBeh(false);
            return false;
        }
        if (currentPosition == videos.size() - 1) {
            if (!isInit) {
                AppUtil.showToastMsg(this, "没有下一个视频了");
            }
            mCustomMediaController.enablePre(true);
            mCustomMediaController.enableBeh(false);
            return false;
        }
        return true;
    }

}
