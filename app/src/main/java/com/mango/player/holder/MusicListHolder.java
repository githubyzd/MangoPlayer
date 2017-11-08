package com.mango.player.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mango.player.R;

/**
 * Created by yzd on 2017/11/7 0007.
 */

public class MusicListHolder extends RecyclerView.ViewHolder {
    public View mItemView = null;
    public TextView name = null;
    public TextView num = null;
    public MusicListHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        name = (TextView) mItemView.findViewById(R.id.list_name);
        num = (TextView) mItemView.findViewById(R.id.num);
    }
}