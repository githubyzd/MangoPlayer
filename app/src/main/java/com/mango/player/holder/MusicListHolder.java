package com.mango.player.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mango.player.R;

/**
 * Created by yzd on 2017/11/7 0007.
 */

public class MusicListHolder extends RecyclerView.ViewHolder {
    public View mItemView = null;
    public TextView name = null;
    public TextView num = null;
    public ImageView thumbnail = null;
    public RelativeLayout normalContainer = null;
    public RelativeLayout add = null;
    public MusicListHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        name = (TextView) mItemView.findViewById(R.id.list_name);
        num = (TextView) mItemView.findViewById(R.id.num);
        thumbnail = (ImageView) mItemView.findViewById(R.id.iv_thumbnail);
        normalContainer = (RelativeLayout) mItemView.findViewById(R.id.normal);
        add = (RelativeLayout) mItemView.findViewById(R.id.add);
    }
}
