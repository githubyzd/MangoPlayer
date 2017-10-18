package com.mango.player.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.player.R;

/**
 * Created by yzd on 2017/10/18 0018.
 */

public class MusicNativeListHolder extends RecyclerView.ViewHolder {
    public View mItemView = null;
    public ImageView iv_thumbnail = null;
    public TextView name = null;
    public TextView singer = null;
    public ImageView detail = null;

    public MusicNativeListHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
        iv_thumbnail = (ImageView) mItemView.findViewById(R.id.iv_thumbnail);
        name = (TextView) mItemView.findViewById(R.id.music_name);
        singer = (TextView) mItemView.findViewById(R.id.music_singer);
        detail = (ImageView) mItemView.findViewById(R.id.detail);
    }
}
