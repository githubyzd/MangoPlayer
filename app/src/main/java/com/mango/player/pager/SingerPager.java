package com.mango.player.pager;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.adapter.MusicSingerListAdapter;
import com.mango.player.base.BasePager;
import com.mango.player.bean.Music;
import com.mango.player.util.LogUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static com.mango.player.activity.App.musicList;

/**
 * Created by yzd on 2017/10/18 0018.
 */

public class SingerPager extends BasePager implements View.OnClickListener, MusicSingerListAdapter.OnItemClickListener {
    private ImageView ivRandom;
    private TextView tvRandom;
    private ImageView edit;
    private ImageView rank;
    private RecyclerView listRecyclerview;
    private View view;
    private MusicSingerListAdapter adapter;
    private LinkedHashMap<String, List<Music>> mSinger = new LinkedHashMap<>();
    private int clickPosition;
    public static List<String> singerList = new ArrayList<>();

    public SingerPager(Activity context) {
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
        edit.setVisibility(View.GONE);
        adapter = new MusicSingerListAdapter(mSinger, singerList);
        listRecyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(this);


        fl_basepager_container.removeAllViews();
        fl_basepager_container.addView(view);
        initData();
    }

    @Override
    protected void initListener() {
        ivRandom.setOnClickListener(this);
        tvRandom.setOnClickListener(this);
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
        singerList = new ArrayList<String>();
        List list = new ArrayList();
        for (Music music : musicList) {
            String artist = music.getArtist();
            if (mSinger.containsKey(artist)) {
                list = mSinger.get(artist);
                list.add(music);
                mSinger.put(artist, list);
            } else {
                singerList.add(artist);
                list = new ArrayList();
                list.add(music);
                mSinger.put(artist, list);
            }
        }
        tvRandom.setText("全部随机(" + musicList.size() + ")");
        adapter.setData(mSinger, singerList);
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
                break;
            case R.id.detail:
                // TODO: 2017/10/19 0019 显示音乐详情
                LogUtil.logByD("点击音乐条目");
                break;
        }
    }
}
