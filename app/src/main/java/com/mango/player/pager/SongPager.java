package com.mango.player.pager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.adapter.MusicSongListAdapter;
import com.mango.player.base.BasePager;
import com.mango.player.bean.Music;
import com.mango.player.util.FileManager;
import com.mango.player.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzd on 2017/10/18 0018.
 */

public class SongPager extends BasePager implements View.OnClickListener {

    private View view;
    private ImageView ivRandom;
    private TextView tvRandom;
    private ImageView edit;
    private ImageView rank;
    private RecyclerView listRecyclerview;
    private List<Music> musics = new ArrayList<>();
    private MusicSongListAdapter adapter;

    public SongPager(Context context) {
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
        musics = new ArrayList<>();
        FileManager manager = FileManager.getInstance(mContext);
        musics = manager.getMusics(mContext);
        adapter.setData(musics);
        LogUtil.logByD("共有" + musics.size() + "首歌曲");
        for (Music music : musics) {
            LogUtil.logByD(music.toString());
        }
    }

    @Override
    public void onClick(View v) {
        processClick(v);
    }
}
