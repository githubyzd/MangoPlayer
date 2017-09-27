package com.mango.player.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mango.player.R;
import com.mango.player.bean.Video;
import com.mango.player.holder.VideoNativeHolder;

import java.util.List;

/**
 * Created by yzd on 2017/9/27 0027.
 */

public class VideoNativeAdapter extends RecyclerView.Adapter<VideoNativeHolder> implements View.OnClickListener {
    private List<Video> mVideo = null;
    private OnItemClickListener mOnItemClickListener = null;

    public VideoNativeAdapter(List<Video> mVideo) {
        this.mVideo = mVideo;
    }

    @Override
    public VideoNativeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        VideoNativeHolder mHolder = new VideoNativeHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video_native, parent, false));
        mHolder.mItemView.setOnClickListener(this);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(VideoNativeHolder holder, int position) {
        Video video = mVideo.get(position);
        holder.iv_thumbnail.setImageBitmap(video.getThumbnail());
        holder.name.setText(video.getName());
        holder.resolution.setText(video.getResolution());
        holder.size.setText(video.getSize());
        holder.duration.setText(video.getDuration());
        holder.itemView.setTag(position);
        holder.iv_more.setTag(position);
        setViewOnclickLisenner(holder);
    }

    private void setViewOnclickLisenner(VideoNativeHolder holder) {
        holder.iv_more.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mVideo.size();
    }

    public void setData(List<Video> data) {
        this.mVideo = data;
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
