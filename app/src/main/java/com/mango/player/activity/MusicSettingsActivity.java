package com.mango.player.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.util.AppUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicSettingsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.in_out)
    CheckBox inOut;
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.shake)
    CheckBox shake;
    @BindView(R.id.out)
    CheckBox out;
    @BindView(R.id.in)
    CheckBox in;
    @BindView(R.id.allow)
    CheckBox allow;
    @BindView(R.id.container)
    RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_settings);
        App.addActivity(this);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initToolBar();
        initView("skin");
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = false, priority = 100)
    public void initView(String type) {
        if (!type.equals("skin")) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        App.removeActivity(this);
    }
}
