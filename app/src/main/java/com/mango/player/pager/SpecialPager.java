package com.mango.player.pager;

import android.app.Activity;
import android.view.View;

import com.mango.player.R;
import com.mango.player.base.BasePager;

/**
 * Created by yzd on 2017/10/18 0018.
 */

public class SpecialPager extends BasePager {
    private View view;
    public SpecialPager(Activity context) {
        super(context);
    }

    @Override
    public void initView() {
        view = View.inflate(mContext, R.layout.song_pager,null);
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
