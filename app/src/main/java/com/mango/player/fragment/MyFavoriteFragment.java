package com.mango.player.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.activity.App;
import com.mango.player.adapter.MusicSongListAdapter;
import com.mango.player.base.BaseFragment;
import com.mango.player.bean.Music;
import com.mango.player.bean.MusicServiceBean;
import com.mango.player.bean.PlayMode;
import com.mango.player.util.FileManager;
import com.mango.player.util.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.mango.player.bean.PlayMode.MODE_RADOM;
import static com.mango.player.bean.PlayMode.PLAY_INDEX;
import static com.mango.player.bean.PlayMode.PLAY_NEXT;
import static com.mango.player.bean.PlayMode.SET_INDEX;

/**
 * Created by yzd on 2017/11/3 0003.
 */

public class MyFavoriteFragment extends BaseFragment implements MusicSongListAdapter.OnItemClickListener {

    @BindView(R.id.iv_random)
    ImageView ivRandom;
    @BindView(R.id.tv_random)
    TextView tvRandom;
    @BindView(R.id.edit)
    ImageView edit;
    @BindView(R.id.rank)
    ImageView rank;
    @BindView(R.id.list_recyclerview)
    RecyclerView listRecyclerview;
    Unbinder unbinder;
    private ArrayList<Music> musics = new ArrayList<>();
    private MusicSongListAdapter adapter;
    private int clickPosition;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_myfavorite;
    }

    @Override
    public void initView() {
        adapter = new MusicSongListAdapter(musics);
        listRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        listRecyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        if (App.musicList != null) {
            musics = App.favoriteList;
        } else {
            musics = FileManager.getInstance(getActivity()).getMusics(getActivity());
        }
        tvRandom.setText("全部随机(" + musics.size() + ")");
        adapter.setData(musics);
    }

    @OnClick({R.id.tv_random, R.id.iv_random})
    void randomPlay() {
        PlayMode playMode = MODE_RADOM;
        EventBus.getDefault().post(playMode);

        EventBus.getDefault().post(musics);

        MusicServiceBean bean = new MusicServiceBean();
        playMode = SET_INDEX;
        bean.setIndex(0);
        bean.setPlayMode(playMode);
        EventBus.getDefault().post(bean);

        playMode = PLAY_NEXT;
        bean.setPlayMode(playMode);
        EventBus.getDefault().post(bean);
    }

    @Override
    public void onItemClick(View view, int position) {
        clickPosition = position;
        switch (view.getId()) {
            case R.id.music_native_lib_item:
                playMusic();
                break;
            case R.id.detail:
                // TODO: 2017/10/19 0019 显示音乐详情
                LogUtil.logByD("点击音乐条目" + musics.get(position));
                break;
        }
    }

    private void playMusic() {
        EventBus.getDefault().post(musics);

        MusicServiceBean bean = new MusicServiceBean();
        bean.setIndex(clickPosition);
        bean.setPlayMode(PLAY_INDEX);
        EventBus.getDefault().post(bean);
    }

}
