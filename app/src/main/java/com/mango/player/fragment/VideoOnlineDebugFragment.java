package com.mango.player.fragment;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by yzd on 2017/9/25 0025.
 */

public class VideoOnlineDebugFragment extends BaseFragment {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private String[] tabs = {"最新", "乱伦", "自拍", "偷拍", "SM", "自慰", "日韩", "欧美", "高清", "群交", "中文字幕", "口交肛交", "有码", "无码", "同性",};

    @Override
    public int getLayoutId() {
        return R.layout.fragment_video_online_debug;
    }

    @Override
    public void initView() {
        for (String tab : tabs) {
            tablayout.addTab(tablayout.newTab().setText(tab));
        }
        PagerAdapter adapter = new MyPagerAdapter();
        viewpager.setAdapter(adapter);

        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.setOnTabSelectedListener(new MyOnTabSelectedListener());
    }

    @Override
    public void initData() {
        super.initData();
    }

    private class MyOnTabSelectedListener implements TabLayout.OnTabSelectedListener {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int position = tab.getPosition();

            viewpager.setCurrentItem(position);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return tabs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView textView = new TextView(getContext());
            textView.setText(getPageTitle(position));
            textView.setTextColor(Color.rgb(0,0,0));
            container.addView(textView);
            return textView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }
}
