package com.mango.player.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.bean.SleepBean;
import com.mango.player.util.AppUtil;
import com.mango.player.util.LogUtil;

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
    @BindView(R.id.sleep_tv)
    TextView sleepTv;
    @BindView(R.id.setting)
    LinearLayout setting;
    private int choice = 0;
    private SleepBean sleepBean = new SleepBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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

    @OnClick(R.id.equalizer)
    void equalizer() {
        Intent intent = new Intent(this, SoundSettingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.skin)
    void skin() {
        Intent intent = new Intent(this, SkinActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.sleep)
    void sleep() {
        choice = 0;
        final String[] items = {"关闭睡眠", "10分钟", "20分钟", "30分钟", "60分钟", "90分钟", "120分钟"};
        new AlertDialog.Builder(this)
                .setTitle("设置睡眠时间")
                .setCancelable(true)
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choice = which;
                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setSleepTime(items[choice]);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void setSleepTime(String item) {
        if (item.equals("关闭睡眠")) {
            AppUtil.showSnackbar(sleepTv, "关闭睡眠");
        } else {
            AppUtil.showSnackbar(sleepTv, "将在" + item + "后启动睡眠模式");
        }
        long time = -1;
        LogUtil.logByD(item);
        switch (item) {
            case "关闭睡眠":
                time = 0;
                break;
            case "10分钟":
                time = 1 * 60 * 1000;
                break;
            case "20分钟":
                time = 20 * 60 * 1000;
                break;
            case "30分钟":
                time = 30 * 60 * 1000;
                break;
            case "60分钟":
                time = 60 * 60 * 1000;
                break;
            case "90分钟":
                time = 90 * 60 * 1000;
                break;
            case "120分钟":
                time = 120 * 60 * 1000;
                break;
        }
        LogUtil.logByD(time + "aaa");
        if (time == 0) {
            sleepTv.setText("关闭");
            sleepBean.setStop(true);
            LogUtil.logByD("b");
        } else {
            LogUtil.logByD("c");
            sleepBean.setUpdateView(false);
            sleepBean.setStop(false);
            sleepBean.setSleepTime(time);
        }
        sleepBean.setUpdateView(false);
        sleepBean.setUpdateView(false);
        EventBus.getDefault().post(sleepBean);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getSleepTime(SleepBean bean) {
        LogUtil.logByD(bean.toString());
        if (bean.isUpdateView()) {
            int millisUntilFinished = (int) bean.getMillisUntilFinished();
            sleepTv.setText(AppUtil.timeLenghtFormast(millisUntilFinished));
        }
    }

    @OnClick(R.id.setting)
    void setting() {
        Intent intent = new Intent(this, MusicSettingsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.share)
    void share() {

    }

    @OnClick(R.id.quit)
    void quit() {
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("退出")
                .setMessage("您确认要退出吗？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        App.closeAllActivity();
                    }
                })
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        App.removeActivity(this);
    }

}
