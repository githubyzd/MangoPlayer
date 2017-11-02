package com.mango.player.pager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.mango.player.R;
import com.mango.player.activity.VideoOnlineDetailActivity;
import com.mango.player.adapter.BannerAdapter;
import com.mango.player.adapter.VideoOnlineAdapter;
import com.mango.player.base.BasePager;
import com.mango.player.bean.VideoBean;
import com.mango.player.util.AppUtil;
import com.mango.player.util.ApplicationConstant;
import com.mango.player.view.GridSpacingItemDecoration;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by admin on 2017/10/21.
 */

public class VideoOnlinePager extends BasePager implements VideoOnlineAdapter.OnItemClickListener {

    private RollPagerView rollPagerView;
    private RecyclerView recyclerView;
    private List<VideoBean> videoList;
    private VideoOnlineAdapter adapter;
    private String mTab = "";
    private ScrollView scrollView;

    public VideoOnlinePager(Activity context, String tab) {
        super(context);
        mTab = tab;
    }

    @Override
    public void initView() {
        scrollView = (ScrollView) View.inflate(mContext, R.layout.video_online_pager, null);
        rollPagerView = (RollPagerView) scrollView.findViewById(R.id.rollView);
        recyclerView = (RecyclerView) scrollView.findViewById(R.id.recyclerview);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 20, true));
        recyclerView.setHasFixedSize(true);
        initBanner();
        initRecyclerView();
        fl_basepager_container.addView(scrollView);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        initData();
    }

    @Override
    public void initData() {
        super.initData();
        String assets = AppUtil.getAssets(mContext, mTab + ".txt");
        if(assets.isEmpty()){
            assets = AppUtil.getAssets(mContext,"最新.txt");
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<VideoBean>>() {
        }.getType();
        videoList = gson.fromJson(assets, type);
        adapter.setData(videoList);
    }

    private void initRecyclerView() {
        adapter = new VideoOnlineAdapter(videoList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    private void initBanner() {
        //设置播放时间间隔
        rollPagerView.setPlayDelay(3000);
        //设置透明度
        rollPagerView.setAnimationDurtion(500);
        //设置适配器
        rollPagerView.setAdapter(new BannerAdapter());

        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //rollPagerView.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        rollPagerView.setHintView(new ColorPointHintView(mContext, Color.YELLOW, Color.WHITE));
//        rollPagerView.setHintView(new TextHintView(mContext));
        //rollPagerView.setHintView(null);  
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void processClick(View view) {

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(mContext, VideoOnlineDetailActivity.class);
        intent.putExtra(ApplicationConstant.VIDEO_TYPE, ApplicationConstant.VIDEO_ONLINE_TYPE);
        intent.putExtra(ApplicationConstant.VIDEO, videoList.get(position));
        mContext.startActivity(intent);
    }
}
