package com.mango.player.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.mango.player.R;

public class BannerAdapter extends StaticPagerAdapter {
        private int[] imgs = {  
                R.drawable.background1,
                R.drawable.background2,
                R.drawable.background3,
                R.drawable.background4,
        };

    public BannerAdapter(int[] imgs) {
        this.imgs = imgs;
    }

    public BannerAdapter() {
    }


    @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);  
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));  
            return view;  
        }  
  
  
        @Override  
        public int getCount() {  
            return imgs.length;  
        }  
    }