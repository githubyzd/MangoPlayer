package com.mango.player.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.activity.VideoPlayActivity;
import com.mango.player.adapter.VideoNativeAdapter;
import com.mango.player.base.BaseFragment;
import com.mango.player.bean.Video;
import com.mango.player.util.AppUtil;
import com.mango.player.util.ApplicationConstant;
import com.mango.player.util.FileManager;
import com.mango.player.util.LogUtil;
import com.mango.player.util.PopupHelper;
import com.mango.player.view.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by yzd on 2017/9/25 0025.
 */

public class VideoNativeFragment extends BaseFragment implements VideoNativeAdapter.OnItemClickListener, View.OnClickListener {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.container)
    RelativeLayout container;
    private LinearLayoutManager mLayoutManager;
    private VideoNativeAdapter mAdapter;
    private ArrayList<Video> videos = new ArrayList<>();
    private PopupHelper popupHelper;
    private int clickPosition;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_video_native;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initView() {
        setBg("skin");
        mLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
        mAdapter = new VideoNativeAdapter(videos);
        recyclerview.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);
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

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = false, priority = 100)
    public void setBg(String type) {
        if (!type.equals("skin")){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            container.setBackground(AppUtil.loadImageFromAsserts(getContext()));
        }
    }
    @Override
    public void onItemClick(View view, int position) {
        clickPosition = position;
        switch (view.getId()) {
            case R.id.video_native_item:
                play(position);
                break;
            case R.id.iv_more:
                showMore(view, position);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_native_play:
                play(clickPosition);
                if (popupHelper != null) {
                    popupHelper.dismiss();
                }
                break;
            case R.id.video_native_detail:
                AppUtil.showSnackbar(v, "详情");
                break;
            case R.id.video_native_edit:
                AppUtil.showSnackbar(v, "编辑");
                break;
            case R.id.video_native_openpath:
                AppUtil.showSnackbar(v, "打开路径");
                break;
        }
    }

    private void showMore(View view, int position) {
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.video_native_itam_more_popu, null);
        ((TextView) contentView.findViewById(R.id.tv_title)).setText(getResources().getString(R.string.video) + "："
                + videos.get(position).getName());
        contentView.findViewById(R.id.video_native_play).setOnClickListener(this);
        contentView.findViewById(R.id.video_native_detail).setOnClickListener(this);
        contentView.findViewById(R.id.video_native_edit).setOnClickListener(this);
        contentView.findViewById(R.id.video_native_openpath).setOnClickListener(this);

        popupHelper = new PopupHelper.Builder(getActivity())
                .contentView(contentView)
                .height(ViewGroup.LayoutParams.WRAP_CONTENT)
                .width(ViewGroup.LayoutParams.MATCH_PARENT)
                .anchorView(view)
                .gravity(Gravity.BOTTOM)
                .parentView(view)
                .outSideTouchable(true)
                .animationStyle(R.style.anim_bottom)
                .build()
                .showAtLocation();
    }

    private void play(int position) {
        Intent intent = new Intent(getContext(), VideoPlayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(ApplicationConstant.VIDEO_LIST_KEY, videos);
        bundle.putInt(ApplicationConstant.VIDEO_POSITION_KEY, position);
        intent.putExtra(ApplicationConstant.VIDEO_TYPE, ApplicationConstant.VIDEO_NATIVE_TYPE);
        intent.putExtra(ApplicationConstant.VIDEO_DATA_KEY, bundle);
        getContext().startActivity(intent);
    }

}
