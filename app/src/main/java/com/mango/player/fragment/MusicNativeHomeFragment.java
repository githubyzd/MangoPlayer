package com.mango.player.fragment;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yzd on 2017/10/18 0018.
 */

public class MusicNativeHomeFragment extends BaseFragment {
    @BindView(R.id.library)
    LinearLayout library;
    @BindView(R.id.file)
    LinearLayout file;
    @BindView(R.id.favorite)
    LinearLayout favorite;
    @BindView(R.id.current)
    LinearLayout current;
    @BindView(R.id.playing)
    LinearLayout playing;
    @BindView(R.id.equalizer)
    LinearLayout equalizer;
    @BindView(R.id.tv_play_list)
    TextView tvPlayList;
    @BindView(R.id.list_add)
    ImageView listAdd;
    @BindView(R.id.list_arrow)
    ImageView listArrow;
    @BindView(R.id.list_recyclerview)
    RecyclerView listRecyclerview;
    private MusicNativeFragment controller;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_music_native_home;
    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.library)
    void library(){
         MusciNativeLibraryFragment fragment = new MusciNativeLibraryFragment();
        if(controller != null){
            controller.switchFragment(fragment);
        }
    }

    public void setController(MusicNativeFragment controller){
        this.controller = controller;
    }
}
