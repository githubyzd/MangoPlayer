package com.mango.player.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.player.R;

/**
 * Created by yzd on 2017/10/18 0018.
 */

public class MusicNativeListPopuHolder extends RecyclerView.ViewHolder {
    public View mItemView = null;
    public TextView name = null;
    public TextView singer = null;
    public ImageView delete = null;

    public MusicNativeListPopuHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        name = (TextView) mItemView.findViewById(R.id.name);
        singer = (TextView) mItemView.findViewById(R.id.singer);
        delete = (ImageView) mItemView.findViewById(R.id.delete);
    }
}
