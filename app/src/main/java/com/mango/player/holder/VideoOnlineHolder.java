package com.mango.player.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.player.R;

/**
 * Created by yzd on 2017/10/18 0018.
 */

public class VideoOnlineHolder extends RecyclerView.ViewHolder {
    public View mItemView = null;
    public ImageView thumbnail = null;
    public TextView name = null;

    public VideoOnlineHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        thumbnail = (ImageView) mItemView.findViewById(R.id.thumbnail);
        name = (TextView) mItemView.findViewById(R.id.name);
    }
}
