package com.mango.player.fragment;

import android.support.v7.widget.RecyclerView;

import com.mango.player.R;
import com.mango.player.base.BaseFragment;
import com.mango.player.bean.Video;
import com.mango.player.util.FileManager;
import com.mango.player.util.LogUtil;

import java.io.File;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yzd on 2017/9/25 0025.
 */

public class VideoNativeFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_video_native;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        super.initData();
        FileManager instance = FileManager.getInstance(this.getActivity());
        List<Video> videos = instance.getVideos();

        for (Video video : videos) {
            LogUtil.logByD(video.toString());
        }
    }
}
