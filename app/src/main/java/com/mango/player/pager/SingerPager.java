package com.mango.player.pager;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.base.BasePager;

/**
 * Created by yzd on 2017/10/18 0018.
 */

public class SingerPager extends BasePager {
    private ImageView ivRandom;
    private TextView tvRandom;
    private ImageView edit;
    private ImageView rank;
    private RecyclerView listRecyclerview;
    private View view;

    public SingerPager(Activity context) {
        super(context);
    }

    @Override
    public void initView() {
        view = View.inflate(mContext, R.layout.song_pager, null);
        ivRandom = (ImageView) view.findViewById(R.id.iv_random);
        tvRandom = (TextView) view.findViewById(R.id.tv_random);
        edit = (ImageView) view.findViewById(R.id.edit);
        rank = (ImageView) view.findViewById(R.id.rank);
        listRecyclerview = (RecyclerView) view.findViewById(R.id.list_recyclerview);

        fl_basepager_container.removeAllViews();
        fl_basepager_container.addView(view);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void processClick(View view) {

    }
}
