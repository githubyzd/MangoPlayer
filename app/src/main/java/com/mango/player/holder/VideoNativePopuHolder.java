package com.mango.player.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.player.R;

/**
 * Created by yzd on 2017/9/27 0027.
 */

public class VideoNativePopuHolder extends RecyclerView.ViewHolder {
    public View mItemView = null;
    public TextView name = null;
    public ImageView delete = null;

    public VideoNativePopuHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        name = (TextView) mItemView.findViewById(R.id.tv_name);
        delete = (ImageView) mItemView.findViewById(R.id.tv_delete);
    }
}
