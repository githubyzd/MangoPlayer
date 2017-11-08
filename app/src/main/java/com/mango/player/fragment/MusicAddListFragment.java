package com.mango.player.fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.activity.App;
import com.mango.player.adapter.MusicAddListAdapter;
import com.mango.player.base.BaseFragment;
import com.mango.player.bean.Music;
import com.mango.player.bean.MusicList;
import com.mango.player.dao.DBManager;
import com.mango.player.util.ACache;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.mango.player.util.ApplicationConstant.MUSIC_LIST_ADD_DATA;

/**
 * Created by yzd on 2017/11/8 0008.
 */

public class MusicAddListFragment extends BaseFragment implements MusicAddListAdapter.OnCheckListener {
    @BindView(R.id.check_num)
    TextView checkNum;
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.all)
    TextView all;
    @BindView(R.id.check_all)
    CheckBox checkAll;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    private MusicAddListAdapter adapter;
    private String listName;
    private MusicList temp;

    @Override
    public int getLayoutId() {
        return R.layout.framgent_music_add_list;
    }

    @Override
    public void initView() {
        EventBus.getDefault().post("添加到");
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        adapter = new MusicAddListAdapter(App.musicList);
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(adapter);
        adapter.setOnCheckListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        listName = ACache.getInstance(getContext()).getAsString(MUSIC_LIST_ADD_DATA);
    }

    @OnClick(R.id.save)
    void save() {
        final List<Music> checked = adapter.getChecked();
        BaseFragment fragment = new MusicPlayItemFragment();
        EventBus.getDefault().post(fragment);
        final List<String> pathList = new ArrayList<>();
        for (Music music : checked) {
            pathList.add(music.getPath());
        }
        for (MusicList musicList : App.listData) {
            if (musicList.getName().equals(listName)) {
                temp = musicList;
                List<String> oldPath = musicList.getMusics();
                if (oldPath == null) {
                    oldPath = new ArrayList<String>();
                }
                if (!oldPath.isEmpty()){
                    for (String path:oldPath){
                        pathList.add(path);
                    }
                }
                musicList.setMusics(pathList);
                DBManager.getInstance(getContext()).updatelist(musicList);
                EventBus.getDefault().postSticky(musicList);
                break;
            }
        }

        new Handler().postDelayed(new Runnable() {
            public void run() {
                EventBus.getDefault().postSticky(temp);
            }
        }, 0);
    }

    @OnCheckedChanged(R.id.check_all)
    void cheekAll(boolean isCheck) {
        adapter.checkALll(isCheck);
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }

    @Override
    public void onCheckChanged(int size) {
        checkNum.setText("已选择" + size + "首");
    }
}
