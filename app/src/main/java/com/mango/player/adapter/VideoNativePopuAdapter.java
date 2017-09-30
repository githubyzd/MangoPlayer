package com.mango.player.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mango.player.R;
import com.mango.player.bean.Video;
import com.mango.player.holder.VideoNativePopuHolder;

import java.util.List;

/**
 * Created by yzd on 2017/9/27 0027.
 */

public class VideoNativePopuAdapter extends RecyclerView.Adapter<VideoNativePopuHolder> implements View.OnClickListener {
    private List<Video> mVideo = null;
    private OnItemClickListener mOnItemClickListener = null;

    public VideoNativePopuAdapter(List<Video> mVideo) {
        this.mVideo = mVideo;
    }

    @Override
    public VideoNativePopuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        VideoNativePopuHolder mHolder = new VideoNativePopuHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video_native_popu, parent, false));
        mHolder.mItemView.setOnClickListener(this);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(VideoNativePopuHolder holder, int position) {
        Video video = mVideo.get(position);
        holder.name.setText(video.getName());
        holder.itemView.setTag(position);
        holder.delete.setTag(position);
        setViewOnclickLisenner(holder);
    }


    private void setViewOnclickLisenner(VideoNativePopuHolder holder) {
        holder.delete.setOnClickListener(this);
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
