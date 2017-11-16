package com.mango.player.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.mango.player.R;
import com.mango.player.util.ACache;
import com.mango.player.util.ApplicationConstant;

public class SplashActivity extends AppCompatActivity {
    private final int SKIP_NUMBER = 100;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startMainActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        App.addActivity(this);
        initConfig();
        handler.sendEmptyMessageDelayed(SKIP_NUMBER, 1000);
    }

    private void initConfig() {
        ACache.getInstance(this).put(ApplicationConstant.MUSIC_PLAY_WITH_EFFECTS,false);
        ACache.getInstance(this).put(ApplicationConstant.MUSIC_PLAY_WITH_SHAKE,false);
        ACache.getInstance(this).put(ApplicationConstant.MUSIC_STOP_WITH_OUT,false);
        ACache.getInstance(this).put(ApplicationConstant.MUSIC_PLAY_WITH_IN,false);
        ACache.getInstance(this).put(ApplicationConstant.MUSIC_PLAY_WITH_GESTURE,false);
    }


    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.removeActivity(this);
    }
}
