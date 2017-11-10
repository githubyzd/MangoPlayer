package com.mango.player.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mango.player.R;

/**
 * Created by yzd on 2017/11/9 0009.
 */

public class SkinHolder extends RecyclerView.ViewHolder {
    public RelativeLayout mItemView = null;
    public ImageView iv_thumbnail = null;
    public ImageView check = null;
    public SkinHolder(View itemView) {
        super(itemView);
        mItemView = (RelativeLayout) itemView;
        iv_thumbnail = (ImageView) mItemView.findViewById(R.id.iv_thumbnail);
        check = (ImageView) mItemView.findViewById(R.id.check);
    }
}
