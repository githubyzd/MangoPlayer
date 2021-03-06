package com.mango.player.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.mango.player.R;
import com.mango.player.base.BaseFragment;
import com.mango.player.base.BasePager;
import com.mango.player.pager.SingerPager;
import com.mango.player.pager.SongPager;
import com.mango.player.pager.SpecialPager;
import com.mango.player.util.AppUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yzd on 2017/10/18 0018.
 */

public class MusciNativeLibraryFragment extends BaseFragment {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.container)
    RelativeLayout container;
    private String[] tabs = {"歌曲", "歌手", "专辑"};
    private List<BasePager> pagers;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_music_native_library;
    }


    @Override
    public void initView() {
       setBg("skin");
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
        pagers = new ArrayList<>();
        pagers.add(new SongPager(getActivity()));
        pagers.add(new SingerPager(getActivity()));
        pagers.add(new SpecialPager(getActivity()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = false, priority = 100)
    public void setBg(String type) {
        if (!type.equals("skin")){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            container.setBackground(AppUtil.loadImageFromAsserts(getContext()));
        }
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
            BasePager basePager = pagers.get(position);
            View view = basePager.rootView;
            basePager.initView();
            container.addView(view);
            return view;
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

    @Override
    public boolean onBackPressed() {
        return true;
    }
}
