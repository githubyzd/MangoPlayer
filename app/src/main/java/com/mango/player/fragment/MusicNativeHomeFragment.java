package com.mango.player.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.activity.App;
import com.mango.player.activity.MusicPlayActivity;
import com.mango.player.activity.SoundSettingActivity;
import com.mango.player.base.BaseFragment;
import com.mango.player.bean.UpdateViewBean;
import com.mango.player.util.ACache;
import com.mango.player.util.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.mango.player.util.ApplicationConstant.MUSIC_INDEX;

/**
 * Created by yzd on 2017/10/18 0018.
 */

public class MusicNativeHomeFragment extends BaseFragment {
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
    @BindView(R.id.tv_play_list)
    TextView tvPlayList;
    @BindView(R.id.list_add)
    ImageView listAdd;
    @BindView(R.id.list_arrow)
    ImageView listArrow;
    @BindView(R.id.list_recyclerview)
    RecyclerView listRecyclerview;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.tv_library)
    TextView tvLibrary;
    @BindView(R.id.tv_favorite)
    TextView tvFavorite;
    Unbinder unbinder;
    private MusicNativeFragment controller;
    private BaseFragment fragment;
    private int currentIndex = -1;

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
    }

    @Override
    public void initData() {
        super.initData();
        Observable<Observer> observable = Observable.create(new ObservableOnSubscribe<Observer>() {

            @Override
            public void subscribe(ObservableEmitter<Observer> emitter) throws Exception {
                while (true) {
                    if (App.favoriteList != null && App.musicList != null) {
                        emitter.onComplete();
                        LogUtil.logByD("test");
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
                tvLibrary.setText(App.musicList.size() + "");
                tvFavorite.setText(App.favoriteList.size() + "");
            }
        };
        //建立连接
        observable.subscribe(observer);
    }

    @OnClick(R.id.library)
    void library() {
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
        if (currentIndex == -1) {
            currentIndex = Integer.parseInt(ACache.getInstance(getContext()).getAsString(MUSIC_INDEX));
        }
        Intent intent = new Intent(getActivity(), MusicPlayActivity.class);
        intent.putExtra(MUSIC_INDEX, currentIndex);
        startActivity(intent);
    }

    @OnClick(R.id.favorite)
    void toFavorite() {
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
    void addList(){
    }

    public void setController(MusicNativeFragment controller) {
        this.controller = controller;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
