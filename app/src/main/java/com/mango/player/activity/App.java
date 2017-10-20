package com.mango.player.activity;

import android.app.Application;

import com.mango.player.bean.Music;
import com.mango.player.bean.Video;
import com.mango.player.util.FileManager;

import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yzd on 2017/10/19 0019.
 */

public class App extends Application {
    public static List<Music> musicList;
    private static List<Video> videoList;
    public static ExecutorService executorService;
    public static Timer timer = new Timer();
    @Override
    public void onCreate() {
        super.onCreate();
        executorService = Executors.newCachedThreadPool();
        initData();
    }

    private void initData() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                musicList = FileManager.getInstance(App.this).getMusics(App.this);
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                videoList = FileManager.getInstance(App.this).getVideos();
            }
        });
    }

}
