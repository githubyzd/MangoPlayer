package com.mango.player.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mango.player.R;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.music_play)
    ImageView musicPlay;
    @BindView(R.id.music_next)
    ImageView musicNext;
    @BindView(R.id.sound_effect)
    ImageView soundEffect;
    @BindView(R.id.container)
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
        ButterKnife.bind(this);
    }
}
