package com.mango.player.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.mango.player.R;
import com.mango.player.base.BaseFragment;
import com.mango.player.base.BasePager;
import com.mango.player.pager.GenrePager;
import com.mango.player.pager.SingerPager;
import com.mango.player.pager.SongPager;
import com.mango.player.pager.SpecialPager;

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
    private String[] tabs = {"歌曲", "歌手", "专辑", "流派"};
    private List<BasePager> pagers;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_music_native_library;
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
        pagers = new ArrayList<>();
        pagers.add(new SongPager(getActivity()));
        pagers.add(new SingerPager(getActivity()));
        pagers.add(new SpecialPager(getActivity()));
        pagers.add(new GenrePager(getActivity()));
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

    private class MyPagerAdapter extends PagerAdapter{

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

}
