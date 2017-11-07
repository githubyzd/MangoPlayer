package com.mango.player.fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mango.player.R;
import com.mango.player.activity.App;
import com.mango.player.adapter.MusicPlayListAdapter;
import com.mango.player.base.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * Created by yzd on 2017/11/7 0007.
 */

public class MusciNativePlayListFragment extends BaseFragment implements MusicPlayListAdapter.OnItemClickListener {
    @BindView(R.id.list_recyclerview)
    RecyclerView listRecyclerview;
    private BaseFragment fragment;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_music_play_list;
    }

    @Override
    public void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        MusicPlayListAdapter adapter = new MusicPlayListAdapter(App.listData);
        listRecyclerview.setLayoutManager(manager);
        listRecyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, final int position) {
        if (view.getId() == R.id.detail){

        }else {
            fragment = new MusicPlayItemFragment();
            EventBus.getDefault().post(fragment);
            new Handler().postDelayed(new Runnable(){
                public void run() {
                    EventBus.getDefault().postSticky(App.listData.get(position));
                }
            }, 0);
        }
    }
    @Override
    public boolean onBackPressed() {
        return true;
    }

}
