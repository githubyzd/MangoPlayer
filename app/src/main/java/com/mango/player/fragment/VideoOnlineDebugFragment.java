package com.mango.player.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.mango.player.R;
import com.mango.player.base.BaseFragment;
import com.mango.player.pager.VideoOnlinePager;
import com.mango.player.util.ApplicationConstant;

import butterknife.BindView;

/**
 * Created by yzd on 2017/9/25 0025.
 */

public class VideoOnlineDebugFragment extends BaseFragment {

    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_video_online_debug;
    }

    private String [] tabs = {};
    @Override
    public void initView() {
        for (String tab : ApplicationConstant.tabs) {
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
            return ApplicationConstant.tabs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            String tab = ApplicationConstant.tabs[position];
            VideoOnlinePager  videoOnlinePager = new VideoOnlinePager(getActivity(),tab);
            View view = videoOnlinePager.rootView;
            videoOnlinePager.initView();
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ApplicationConstant.tabs[position];
        }
    }
}
