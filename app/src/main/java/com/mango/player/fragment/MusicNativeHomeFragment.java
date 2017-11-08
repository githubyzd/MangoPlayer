package com.mango.player.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.activity.App;
import com.mango.player.activity.MusicPlayActivity;
import com.mango.player.activity.SoundSettingActivity;
import com.mango.player.adapter.MusicListAdapter;
import com.mango.player.base.BaseFragment;
import com.mango.player.bean.MusicList;
import com.mango.player.bean.UpdateViewBean;
import com.mango.player.dao.DBManager;
import com.mango.player.util.ACache;
import com.mango.player.util.AppUtil;
import com.mango.player.util.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;
import static com.mango.player.R.id.list_recyclerview;
import static com.mango.player.R.id.tv_play_list;
import static com.mango.player.activity.App.listData;
import static com.mango.player.util.ApplicationConstant.MUSIC_INDEX;

/**
 * Created by yzd on 2017/10/18 0018.
 */

public class MusicNativeHomeFragment extends BaseFragment implements View.OnClickListener, MusicListAdapter.OnItemClickListener {
    @BindView(R.id.library)
    RelativeLayout library;
    @BindView(R.id.file)
    LinearLayout file;
    @BindView(R.id.favorite)
    RelativeLayout favorite;
    @BindView(R.id.current)
    LinearLayout current;
    @BindView(R.id.playing)
    LinearLayout playing;
    @BindView(R.id.equalizer)
    LinearLayout equalizer;
    @BindView(tv_play_list)
    TextView tvPlayList;
    @BindView(R.id.list_add)
    ImageView listAdd;
    @BindView(R.id.list_arrow)
    ImageView listArrow;
    @BindView(list_recyclerview)
    RecyclerView listRecyclerview;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.tv_library)
    TextView tvLibrary;
    @BindView(R.id.tv_favorite)
    TextView tvFavorite;
    @BindView(R.id.play_list)
    LinearLayout playList;
    Unbinder unbinder;
    private MusicNativeFragment controller;
    private BaseFragment fragment;
    private int currentIndex = -1;
    private AlertDialog alertDialog;
    private EditText etListName;
    private MusicListAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_music_native_home;
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

    @Override
    public void initView() {
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                if (scrollView != null)
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        if (listData == null) {
            listData = new ArrayList<>();
        }
        adapter = new MusicListAdapter(listData);
        manager.setOrientation(HORIZONTAL);
        listRecyclerview.setLayoutManager(manager);
        listRecyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        listData = DBManager.getInstance(getContext()).querylistList();
        if (listData == null || listData.isEmpty()) {
            listData = new ArrayList<>();
            MusicList list = new MusicList();
            list.setName("默认列表");
            DBManager.getInstance(getContext()).insertList(list);
            listData.add(list);
        }
        adapter.setData(listData);
        Observable<Observer> observable = Observable.create(new ObservableOnSubscribe<Observer>() {

            @Override
            public void subscribe(ObservableEmitter<Observer> emitter) throws Exception {
                while (true) {
                    if (App.favoriteList != null && App.musicList != null) {
                        emitter.onComplete();
                        break;
                    }
                }
            }
        });
        //创建一个下游 Observer
        Observer<Observer> observer = new Observer<Observer>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Observer value) {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                List<MusicList> musicLists = DBManager.getInstance(getContext()).querylistList();
                tvPlayList.setText("播放列表(" + musicLists.size() + ")");
                tvLibrary.setText(App.musicList.size() + "");
                tvFavorite.setText(App.favoriteList.size() + "");
            }
        };
        //建立连接
        observable.subscribe(observer);
    }

    @OnClick(R.id.library)
    void library() {
        EventBus.getDefault().post("媒体库");
        fragment = new MusciNativeLibraryFragment();
        if (controller != null) {
            controller.switchFragment(fragment);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 100)
    public void updateView(UpdateViewBean viewBean) {
        LogUtil.logByD("updateView: " + viewBean.toString());
        currentIndex = viewBean.getIndex();
    }

    @OnClick(R.id.playing)
    void toPlaying() {
        String index = ACache.getInstance(getContext()).getAsString(MUSIC_INDEX);
        if (currentIndex == -1) {
            if (index == null) {
                index = "0";
            }
            currentIndex = Integer.parseInt(index);
        }
        Intent intent = new Intent(getActivity(), MusicPlayActivity.class);
        intent.putExtra(MUSIC_INDEX, currentIndex);
        startActivity(intent);
    }

    @OnClick(R.id.favorite)
    void toFavorite() {
        EventBus.getDefault().post("我的最爱");
        fragment = new MyFavoriteFragment();
        if (controller != null) {
            controller.switchFragment(fragment);
        }
    }

    @OnClick(R.id.equalizer)
    void equalizer() {
        Intent intent = new Intent(getActivity(), SoundSettingActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.list_add)
    void addList() {
        if (alertDialog == null) {
            View view = View.inflate(getActivity(), R.layout.music_list_add, null);
            etListName = (EditText) view.findViewById(R.id.et_list);
            TextView cancel = (TextView) view.findViewById(R.id.cancel);
            TextView positave = (TextView) view.findViewById(R.id.positave);
            cancel.setOnClickListener(this);
            positave.setOnClickListener(this);
            alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setView(view);
        }
        alertDialog.show();
    }

    @OnClick(R.id.play_list)
    void toList() {
        EventBus.getDefault().post("播放列表");
        fragment = new MusciNativePlayListFragment();
        if (controller != null) {
            controller.switchFragment(fragment);
        }
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        initData();
//    }

    public void setController(MusicNativeFragment controller) {
        this.controller = controller;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                alertDialog.dismiss();
                List<MusicList> musicLists = DBManager.getInstance(getContext()).querylistList();
                LogUtil.logByD(musicLists.size() + "");
                break;
            case R.id.positave:
                if (etListName.getText().toString().isEmpty()) {
                    AppUtil.showSnackbar(etListName, "请输入名称");
                    return;
                }
                for (MusicList list : App.listData) {
                    if (etListName.equals(list.getName())){
                        AppUtil.showSnackbar(etListName, "列表已存在");
                        return;
                    }
                }
                MusicList list = new MusicList();
                list.setName(etListName.getText().toString());
                long insertID = DBManager.getInstance(getContext()).insertList(list);
                if (insertID >= 0) {
                    listData.add(list);
                    AppUtil.showSnackbar(listAdd, "创建成功");
                } else {
                    AppUtil.showSnackbar(listAdd, "创建成功");
                }
                alertDialog.dismiss();
                break;
        }
    }

    @Override
    public void onItemClick(View view, final int position) {
        BaseFragment fragment = new MusicPlayItemFragment();
        EventBus.getDefault().post(fragment);
        EventBus.getDefault().post(listData.get(position).getName());
        new Handler().postDelayed(new Runnable() {
            public void run() {
                EventBus.getDefault().postSticky(listData.get(position));
            }
        }, 0);
    }

    @Override
    public boolean onBackPressed() {
        return fragment.onBackPressed();
    }
}
