package com.mango.player.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mango.player.R;
import com.mango.player.adapter.VideoNativeAdapter;
import com.mango.player.base.BaseFragment;
import com.mango.player.bean.Video;
import com.mango.player.util.FileManager;
import com.mango.player.util.LogUtil;
import com.mango.player.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yzd on 2017/9/25 0025.
 */

public class VideoNativeFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private LinearLayoutManager mLayoutManager;
    private VideoNativeAdapter mAdapter;
    private List<Video> videos = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_video_native;
    }

    @Override
    public void initView() {
        mLayoutManager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
        mAdapter = new VideoNativeAdapter(videos);
        recyclerview.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        super.initData();
        FileManager instance = FileManager.getInstance(this.getActivity());
        videos = instance.getVideos();
        mAdapter.setData(videos);
        for (Video video : videos) {
            LogUtil.logByD(video.toString());
        }
    }
}
