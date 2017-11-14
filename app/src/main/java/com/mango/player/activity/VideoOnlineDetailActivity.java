package com.mango.player.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mango.player.R;
import com.mango.player.bean.VideoBean;
import com.mango.player.util.ApplicationConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class VideoOnlineDetailActivity extends AppCompatActivity {

    @BindView(R.id.thumbnail)
    ImageView thumbnail;
    @BindView(R.id.play)
    Button play;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.time)
    TextView time;
    private VideoBean video;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_online_detail);
        App.addActivity(this);
        unbinder = ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        Glide.with(App.mContext)
                .load(video.getThumbnail())
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .error(R.mipmap.ic_launcher)
                .into(thumbnail);
        name.setText(video.getName());
        time.setText("时间： " + video.getTime());
    }

    @OnClick(R.id.play)
    void play(){
        Intent intent = new Intent(this, VideoPlayActivity.class);
        intent.putExtra(ApplicationConstant.VIDEO_TYPE,ApplicationConstant.VIDEO_ONLINE_TYPE);
        intent.putExtra(ApplicationConstant.VIDEO, video);
        startActivity(intent);
    }
    private void initData() {
        Intent intent = getIntent();
        video = intent.getParcelableExtra(ApplicationConstant.VIDEO);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        App.removeActivity(this);
    }
}
