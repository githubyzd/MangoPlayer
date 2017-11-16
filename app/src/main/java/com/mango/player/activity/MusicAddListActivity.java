package com.mango.player.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.adapter.MusicAddListAdapter;
import com.mango.player.base.BaseFragment;
import com.mango.player.bean.Music;
import com.mango.player.bean.MusicList;
import com.mango.player.fragment.MyFavoriteFragment;
import com.mango.player.util.ACache;
import com.mango.player.util.AppUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.mango.player.activity.App.musicList;
import static com.mango.player.util.ApplicationConstant.MUSIC_FAVORITE_KEY;

public class MusicAddListActivity extends AppCompatActivity implements MusicAddListAdapter.OnCheckListener {

    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.check_num)
    TextView checkNum;
    @BindView(R.id.all)
    TextView all;
    @BindView(R.id.check_all)
    CheckBox checkAll;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.container)
    RelativeLayout container;
    private MusicAddListAdapter adapter;
    private String listName;
    private MusicList temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_add_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setBg();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter = new MusicAddListAdapter(musicList);
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(adapter);
        adapter.setOnCheckListener(this);
    }

    public void setBg() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            container.setBackground(AppUtil.loadImageFromAsserts(this));
        }
    }
    @OnClick(R.id.save)
    void save() {
        final List<Music> checked = adapter.getChecked();
        final List<String> pathList = new ArrayList<>();
        for (Music music : checked) {
            pathList.add(music.getPath());
        }
        ArrayList<String> favoritePath = (ArrayList<String>) ACache.getInstance(this).getAsObject(MUSIC_FAVORITE_KEY);
        if (favoritePath == null){
            favoritePath = new ArrayList<>();
        }
        for (Music music : checked) {
            App.favoriteList.add(music);
            favoritePath.add(music.getPath());
        }
        ACache.getInstance(this).put(MUSIC_FAVORITE_KEY, favoritePath);
        finish();
        BaseFragment fragment = new MyFavoriteFragment();
        EventBus.getDefault().post(fragment);
    }

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @OnCheckedChanged(R.id.check_all)
    void cheekAll(boolean isCheck) {
        adapter.checkALll(isCheck);
    }

    @Override
    public void onCheckChanged(int size) {
        checkNum.setText("已选择" + size + "首");
    }
}
