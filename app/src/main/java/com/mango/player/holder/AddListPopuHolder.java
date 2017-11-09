package com.mango.player.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mango.player.R;

/**
 * Created by yzd on 2017/11/9 0009.
 */

public class AddListPopuHolder extends RecyclerView.ViewHolder {
    public View mItemView = null;
    public TextView name = null;
    public AddListPopuHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        name = (TextView) mItemView.findViewById(R.id.name);
    }
}
