package com.mango.player.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mango.player.R;
import com.mango.player.bean.Video;
import com.mango.player.holder.VideoNativeHolder;

import java.util.List;

/**
 * Created by yzd on 2017/9/27 0027.
 */

public class VideoNativeAdapter extends RecyclerView.Adapter<VideoNativeHolder> {
    private List<Video> mVideo = null;

    public VideoNativeAdapter(List<Video> mVideo) {
        this.mVideo = mVideo;
    }

    @Override
    public VideoNativeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        VideoNativeHolder mHolder = new VideoNativeHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video_native, parent, false));
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
    }

    @Override
    public int getItemCount() {
        return mVideo.size();
    }

    public void setData(List<Video> data) {
        this.mVideo = data;
        notifyDataSetChanged();
    }
}
