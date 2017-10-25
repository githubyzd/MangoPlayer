package com.mango.player.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.bean.Music;
import com.mango.player.util.ApplicationConstant;
import com.mango.player.util.MusicController;

import java.util.ArrayList;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MusicPlayActivity extends AppCompatActivity {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.list)
    ImageView list;
    @BindView(R.id.singer)
    TextView singer;
    @BindView(R.id.lyric)
    TextView lyric;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.like)
    ImageView like;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.pattern)
    ImageView pattern;
    @BindView(R.id.music_pre)
    ImageView musicPre;
    @BindView(R.id.music_next)
    ImageView musicNext;
    @BindView(R.id.sound_effect)
    ImageView soundEffect;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.current_duration)
    TextView currentDuration;
    @BindView(R.id.duration)
    TextView duration;
    @BindView(R.id.music_play)
    ImageView musicPlay;
    private ArrayList<Music> musics;
    private Music music;
    private MusicController musicController;
    private int currentIndex = -1;
    private TimerTask mTimerTask;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 101) {
                progress.setProgress(musicController.getCurrentPosition());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
        ButterKnife.bind(this);
        musicController = MusicController.getInstance(this);
        initData();
        updateView();
    }

    private void initData() {
        Intent intent = getIntent();
        currentIndex = intent.getIntExtra(ApplicationConstant.MUSIC_INDEX, -1);
        musics = App.musicList;
        music = musics.get(currentIndex);
    }

    @OnClick(R.id.music_play)
    void play() {
        if (musicController.isPlaying()) {
            musicController.pause();
        } else {
            musicController.play();
        }
        updateView();
    }

    @OnClick(R.id.music_pre)
    void playPre() {
        if (currentIndex == 0) {
            currentIndex = musics.size() - 1;
        } else {
            currentIndex--;
        }
        musicController.playNext();
        updateView();
    }

    @OnClick(R.id.music_next)
    void playNext() {
        if (currentIndex == musics.size() - 1) {
            currentIndex = 0;
        } else {
            currentIndex++;
        }
        musicController.playNext();
        updateView();
    }

    private void updateView() {
        music = musics.get(currentIndex);
        name.setText(music.getName());
        singer.setText(music.getArtist());
        if (musicController.isPlaying()) {
            musicPlay.setImageResource(R.drawable.pause_music);
        } else {
            musicPlay.setImageResource(R.drawable.play_music);
        }
        setProgress();
    }

    private void setProgress() {
        currentDuration.setText(musicController.getCurrentPosition());
        duration.setText(musicController.getDuration());
        progress.setMax(musicController.getDuration());
        progress.setProgress(musicController.getCurrentPosition());
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(101);
            }
        };
        App.timer.schedule(mTimerTask, 0, 1000);
    }

}
