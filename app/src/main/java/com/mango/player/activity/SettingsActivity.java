package com.mango.player.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mango.player.R;
import com.mango.player.util.AppUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.equalizer)
    LinearLayout equalizer;
    @BindView(R.id.skin)
    LinearLayout skin;
    @BindView(R.id.sleep)
    LinearLayout sleep;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.share)
    LinearLayout share;
    @BindView(R.id.quit)
    LinearLayout quit;
    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initToolBar();
        initView("skin");
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = false,priority = 100)
    public void initView(String type) {
        if (!type.equals("skin")){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            container.setBackground(AppUtil.loadImageFromAsserts(this));
        }
    }



    private void initToolBar() {
        toolbar.setBackgroundColor(Color.GRAY);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick(R.id.equalizer)
    void equalizer(){
        Intent intent = new Intent(this,SoundSettingActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.skin)
    void skin(){
        Intent intent = new Intent(this,SkinActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.sleep)
    void sleep(){

    }

    @OnClick(R.id.setting)
    void setting(){
        Intent intent = new Intent(this,MusicSettingsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.share)
    void share(){

    }

    @OnClick(R.id.quit)
    void quit(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
