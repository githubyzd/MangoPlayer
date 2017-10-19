package com.mango.player.activity;

import android.app.Application;

import com.mango.player.bean.Music;
import com.mango.player.bean.Video;
import com.mango.player.util.FileManager;

import java.util.List;

/**
 * Created by yzd on 2017/10/19 0019.
 */

public class App extends Application {
    public static List<Music> musicList;
    private static List<Video> videoList;

    @Override
    public void onCreate() {
        super.onCreate();

        initData();
    }

    private void initData() {

        Thread music = new Thread(new Runnable() {
            public void run() {
                musicList = FileManager.getInstance(App.this).getMusics(App.this);
            }
        });
        final Thread video = new Thread(new Runnable() {
            public void run() {
                videoList = FileManager.getInstance(App.this).getVideos();
            }
        });
        music.start();
        video.start();
    }
}
