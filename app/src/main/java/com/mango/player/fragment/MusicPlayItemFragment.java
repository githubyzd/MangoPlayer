package com.mango.player.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.activity.App;
import com.mango.player.adapter.MusicSongListAdapter;
import com.mango.player.base.BaseFragment;
import com.mango.player.bean.Music;
import com.mango.player.bean.MusicList;
import com.mango.player.bean.MusicServiceBean;
import com.mango.player.bean.PlayMode;
import com.mango.player.util.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.mango.player.bean.PlayMode.MODE_RADOM;
import static com.mango.player.bean.PlayMode.PLAY_INDEX;
import static com.mango.player.bean.PlayMode.PLAY_NEXT;
import static com.mango.player.bean.PlayMode.SET_INDEX;

/**
 * Created by yzd on 2017/11/7 0007.
 */

public class MusicPlayItemFragment extends BaseFragment implements MusicSongListAdapter.OnItemClickListener {

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
    @BindView(R.id.add_music)
    TextView addMusic;
    @BindView(R.id.no_data)
    LinearLayout noData;
    private MusicList music;
    private List<Music> musics = new ArrayList<>();
    private MusicSongListAdapter adapter;
    private int clickPosition;
    private List<String> pathList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_play_list_item;
    }

    @Override
    public void initView() {
        adapter = new MusicSongListAdapter(musics);
        listRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        listRecyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 100)
    public void getData(MusicList musicList) {
        this.music = musicList;
        pathList = music.getMusics();
        if (pathList == null) {
            pathList = new ArrayList<>();
        }
        tvRandom.setText("全部随机(" + pathList.size() + ")");
        for (String path : pathList) {
            for (Music music : App.musicList) {
                if (path.equals(music.getPath())) {
                    musics.add(music);
                    break;
                }
            }
        }
        if (musics.isEmpty()) {
            noData.setVisibility(View.VISIBLE);
            listRecyclerview.setVisibility(View.GONE);
        } else {
            noData.setVisibility(View.GONE);
            listRecyclerview.setVisibility(View.VISIBLE);
            adapter.setData(musics);
        }
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

    @OnClick(R.id.add_music)
    void addMusic(){

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}
