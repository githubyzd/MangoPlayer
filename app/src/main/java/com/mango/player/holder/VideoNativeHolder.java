package com.mango.player.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.player.R;

/**
 * Created by yzd on 2017/9/27 0027.
 */

public class VideoNativeHolder extends RecyclerView.ViewHolder {
    private View mItemView = null;
    public ImageView iv_thumbnail = null;
    public TextView name = null;
    public TextView duration = null;
    public TextView size = null;
    public TextView resolution = null;

    public VideoNativeHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        iv_thumbnail = (ImageView) mItemView.findViewById(R.id.iv_thumbnail);
        name = (TextView) mItemView.findViewById(R.id.tv_name);
        duration = (TextView) mItemView.findViewById(R.id.tv_duration);
        size = (TextView) mItemView.findViewById(R.id.tv_size);
        resolution = (TextView) mItemView.findViewById(R.id.tv_resolution);
    }
}
