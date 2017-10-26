package com.mango.player.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.adapter.MusicDetailListPopuAdapter;
import com.mango.player.adapter.MusicDetailLyricAdapter;
import com.mango.player.bean.Music;
import com.mango.player.util.AppUtil;
import com.mango.player.util.ApplicationConstant;
import com.mango.player.util.LogUtil;
import com.mango.player.util.MusicController;
import com.mango.player.util.PopupHelper;

import java.util.ArrayList;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mango.player.R.id.recyclerview;
import static com.mango.player.activity.App.mainActicity;
import static com.mango.player.util.MusicController.mContext;

public class MusicPlayActivity extends AppCompatActivity implements View.OnClickListener, MusicDetailListPopuAdapter.OnItemClickListener, MusicController.OnPreparedListener, SeekBar.OnSeekBarChangeListener, ViewPager.OnPageChangeListener {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.list)
    ImageView list;
    @BindView(R.id.singer)
    TextView singer;
    @BindView(R.id.lyric)
    ViewPager lyric;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.like)
    ImageView like;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.progress)
    SeekBar progress;
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
    private View contentView;
    private RecyclerView recyclerView;
    private boolean isInit = true;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 101) {
                progress.setProgress(musicController.getCurrentPosition());
                currentDuration.setText(AppUtil.timeLenghtFormast(musicController.getCurrentPosition()));
            }
        }
    };
    private PopupHelper popupHelper;
    private TextView index;
    private ImageView delete;
    private ImageView save;
    private ImageView close;
    private MusicDetailLyricAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
        ButterKnife.bind(this);
        musicController = MusicController.getInstance(mainActicity);
        initData();
        initListener();
        updateView();
        initViewpager();
    }

    private void initViewpager() {
        adapter = new MusicDetailLyricAdapter(musics, this, currentIndex);
        lyric.setAdapter(adapter);
        lyric.addOnPageChangeListener(this);
        lyric.setCurrentItem(currentIndex);
    }

    private void initListener() {
        progress.setOnSeekBarChangeListener(this);
        if (musicController.isPlaying()) {
            setProgress();
        } else {
            musicController.setOnPreparedListener(this);
        }
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

    private void playMusic(int index) {
        musicController.setOnPreparedListener(this);
        musicController.playMusic(index);
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
        lyric.setCurrentItem(currentIndex);
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
        lyric.setCurrentItem(currentIndex);
    }

    @OnClick(R.id.list)
    void shoList() {
        if (contentView == null) {
            contentView = View.inflate(mContext, R.layout.music_detail_play_list_popu, null);
            recyclerView = (RecyclerView) contentView.findViewById(recyclerview);
            index = (TextView) contentView.findViewById(R.id.index);
            delete = (ImageView) contentView.findViewById(R.id.delete);
            save = (ImageView) contentView.findViewById(R.id.save);
            close = (ImageView) contentView.findViewById(R.id.close);

            delete.setOnClickListener(this);
            save.setOnClickListener(this);
            close.setOnClickListener(this);
            initRecyclerview();
        }
        index.setText(" (" + currentIndex + "/" + musics.size() + ")");
        if (music.getThumbnail() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                contentView.setBackground(new BitmapDrawable(music.getThumbnail()));
            }
        } else {
            contentView.setBackgroundResource(R.drawable.background4);
        }
        popupHelper = new PopupHelper.Builder(mContext)
                .contentView(contentView)
                .width(ViewGroup.LayoutParams.MATCH_PARENT)
                .height(ViewGroup.LayoutParams.MATCH_PARENT)
                .anchorView(list)
                .parentView(list)
                .gravity(Gravity.TOP)
                .outSideTouchable(true)
                .animationStyle(R.style.anim_top)
                .build()
                .showAtLocation();
    }

    private void initRecyclerview() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        MusicDetailListPopuAdapter mAdapter = new MusicDetailListPopuAdapter(musics);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    private void updateView() {
        music = musics.get(currentIndex);
        name.setText(music.getName());
        singer.setText(music.getArtist());
        if (music.getThumbnail() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                container.setBackground(new BitmapDrawable(music.getThumbnail()));
            }
        } else {
            container.setBackgroundResource(R.drawable.background4);
        }
        if (index != null)
            index.setText(" (" + currentIndex + "/" + musics.size() + ")");
        if (musicController.isPlaying()) {
            musicPlay.setImageResource(R.drawable.pause_music);
        } else {
            musicPlay.setImageResource(R.drawable.play_music);
        }

    }

    private void setProgress() {
        currentDuration.setText(AppUtil.timeLenghtFormast(musicController.getCurrentPosition()));
        duration.setText(AppUtil.timeLenghtFormast(musicController.getDuration()));
        progress.setMax(musicController.getDuration());
        progress.setProgress(musicController.getCurrentPosition());
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(101);
            }
        };
        updateView();
        App.timer.schedule(mTimerTask, 0, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete:

                break;
            case R.id.save:

                break;
            case R.id.close:
                popupHelper.dismiss();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (popupHelper != null) {
            if (popupHelper.isShowing()) {
                popupHelper.dismiss();
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.item_music_list_popu:
                currentIndex = position;
                music = musics.get(currentIndex);
                playMusic(currentIndex);
                updateView();
                break;
            case R.id.more:
                break;
        }
    }

    @Override
    public void onPrepared() {
        setProgress();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        LogUtil.logByD(progress + "");
        if (fromUser)
            musicController.seekTo(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentIndex = position;
        if (isInit) {
            isInit = false;
        } else {
            playMusic(currentIndex);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
