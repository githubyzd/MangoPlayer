package com.mango.player.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.mango.player.R;
import com.mango.player.activity.App;
import com.mango.player.bean.VideoBean;
import com.mango.player.holder.VideoOnlineHolder;

import java.util.List;

/**
 * Created by yzd on 2017/9/27 0027.
 */

public class VideoOnlineAdapter extends RecyclerView.Adapter<VideoOnlineHolder> implements View.OnClickListener {
    private OnItemClickListener mOnItemClickListener = null;
    private List<VideoBean> videoList;

    public VideoOnlineAdapter(List<VideoBean> videoList) {
        this.videoList = videoList;
    }

    @Override
    public VideoOnlineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        VideoOnlineHolder mHolder = new VideoOnlineHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video_online, parent, false));
        mHolder.mItemView.setOnClickListener(this);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(VideoOnlineHolder holder, int position) {
        VideoBean videoBean = videoList.get(position);
        Glide.with(App.mContext)
                .load(videoBean.getThumbnail())
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .error(R.mipmap.ic_launcher)
                .into(holder.thumbnail);
        holder.name.setText(videoBean.getName());
        holder.mItemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public void setData(List<VideoBean> data) {
        videoList = data;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

     public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
