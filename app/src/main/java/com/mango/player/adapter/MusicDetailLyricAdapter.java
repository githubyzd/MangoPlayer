package com.mango.player.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mango.player.bean.Music;

import java.util.List;

/**
 * 自定义类实现PagerAdapter，填充显示数据
 */
public class MusicDetailLyricAdapter extends PagerAdapter {
    private List<Music> musicList;
    private Context context;
    private int index;

    public MusicDetailLyricAdapter(List<Music> musicList, Context context, int index) {
        this.musicList = musicList;
        this.context = context;
        this.index = index;
    }

    // 显示多少个页面
    @Override
    public int getCount() {
        return musicList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    // 初始化显示的条目对象
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // return super.instantiateItem(container, position);
        // 准备显示的数据，一个简单的TextView
        TextView tv = new TextView(context);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(20);
        tv.setText(musicList.get(position).getName());

        // 添加到ViewPager容器
        container.addView(tv);

        // 返回填充的View对象
        return tv;
    }

    // 销毁条目对象
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
