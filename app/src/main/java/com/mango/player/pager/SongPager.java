package com.mango.player.pager;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.activity.App;
import com.mango.player.adapter.MusicSongListAdapter;
import com.mango.player.base.BasePager;
import com.mango.player.bean.Music;
import com.mango.player.util.FileManager;
import com.mango.player.util.LogUtil;
import com.mango.player.util.MusicController;

import java.util.ArrayList;


/**
 * Created by yzd on 2017/10/18 0018.
 */

public class SongPager extends BasePager implements View.OnClickListener, MusicSongListAdapter.OnItemClickListener {

    private View view;
    private ImageView ivRandom;
    private TextView tvRandom;
    private ImageView edit;
    private ImageView rank;
    private RecyclerView listRecyclerview;
    private ArrayList<Music> musics = new ArrayList<>();
    private MusicSongListAdapter adapter;
    private int clickPosition;

    public SongPager(Activity context) {
        super(context);
    }

    @Override
    public void initView() {
        view = View.inflate(mContext, R.layout.song_pager, null);
        ivRandom = (ImageView) view.findViewById(R.id.iv_random);
        tvRandom = (TextView) view.findViewById(R.id.tv_random);
        edit = (ImageView) view.findViewById(R.id.edit);
        rank = (ImageView) view.findViewById(R.id.rank);
        listRecyclerview = (RecyclerView) view.findViewById(R.id.list_recyclerview);
        listRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));

        adapter = new MusicSongListAdapter(musics);
        listRecyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        fl_basepager_container.removeAllViews();
        fl_basepager_container.addView(view);

        initListener();
        initData();
    }

    @Override
    protected void initListener() {
        ivRandom.setOnClickListener(this);
        tvRandom.setOnClickListener(this);
        edit.setOnClickListener(this);
        rank.setOnClickListener(this);
    }

    @Override
    protected void processClick(View view) {
        switch (view.getId()) {
            case R.id.iv_random:
            case R.id.tv_random:

                break;
            case R.id.edit:
                break;
            case R.id.rank:
                break;
            default:
                break;
        }
    }

    @Override
    public void initData() {
        super.initData();
        if (App.musicList != null) {
            musics = App.musicList;
        }else {
            musics = FileManager.getInstance(mContext).getMusics(mContext);
        }
        tvRandom.setText("全部随机(" + musics.size() + ")");
        adapter.setData(musics);
        LogUtil.logByD("共有" + musics.size() + "首歌曲");
    }

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    @Override
    public void onItemClick(View view, int position) {
        clickPosition = position;
        switch (view.getId()) {
            case R.id.music_native_lib_item:
                playMusic();
                break;
            case R.id.detail:
                // TODO: 2017/10/19 0019 显示音乐详情
                LogUtil.logByD("点击音乐条目" + musics.get(position));
                break;
        }
    }

    private void playMusic() {
        MusicController instance = MusicController.getInstance(mContext);
        instance.initData(musics);
        instance.playMusic(clickPosition);
    }
}
