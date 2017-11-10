package com.mango.player.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.activity.App;
import com.mango.player.base.BaseFragment;
import com.mango.player.bean.Music;
import com.mango.player.util.ACache;
import com.mango.player.util.AppUtil;
import com.mango.player.util.ExceptionUtil;
import com.mango.player.util.LogUtil;
import com.mango.player.util.MusicController;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.mango.player.util.ApplicationConstant.MUSIC_FAVORITE_KEY;
import static com.mango.player.util.ApplicationConstant.MUSIC_INDEX;

/**
 * Created by yzd on 2017/9/25 0025.
 */

public class MusicNativeFragment extends BaseFragment {

    @BindView(R.id.ll_container)
    FrameLayout llContainer;
    @BindView(R.id.music_img)
    ImageView musicImg;
    @BindView(R.id.music_name)
    TextView musicName;
    @BindView(R.id.music_author)
    TextView musicAuthor;
    @BindView(R.id.music_play)
    ImageView musicPlay;
    @BindView(R.id.music_next)
    ImageView musicNext;
    @BindView(R.id.music_list)
    ImageView musicList;
    @BindView(R.id.music_play_controller)
    LinearLayout musicPlayController;
    @BindView(R.id.ll_home)
    RelativeLayout llHome;

    private BaseFragment baseFragment;
    private int index;
    private ArrayList<String> favoritePath;
    private ArrayList<Music> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_music_native;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {
        EventBus.getDefault().post("本地音乐");
        setBg("skin");
        initController();
        MusicNativeHomeFragment fragment = new MusicNativeHomeFragment();
        fragment.setController(this);
        switchFragment(fragment);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = false, priority = 100)
    public void setBg(String type) {
        if (!type.equals("skin")){
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            llHome.setBackground(AppUtil.loadImageFromAsserts(getContext()));
        }
    }

    private void initController() {
        final MusicController controller = MusicController.getInstance(getActivity());
        controller.initView(musicPlayController);
        //创建一个上游 Observable：
        Observable<Observer> observable = Observable.create(new ObservableOnSubscribe<Observer>() {

            @Override
            public void subscribe(ObservableEmitter<Observer> emitter) throws Exception {
                favoritePath = (ArrayList<String>) ACache.getInstance(getActivity()).getAsObject(MUSIC_FAVORITE_KEY);
                String indexStr = ACache.getInstance(getContext()).getAsString(MUSIC_INDEX);
                while (true) {
                    if (App.musicList != null) {
                        controller.initData(App.musicList);
                        if (indexStr == null) {
                            indexStr = "0";
                        }
                        LogUtil.logByD("index" + indexStr);
                        index = Integer.parseInt(indexStr);
                        controller.setCurrentIndex(index);
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
                controller.initController();
                list.clear();
                if (favoritePath == null)
                    favoritePath = new ArrayList<>();
                for (String path : favoritePath) {
                    for (Music music : App.musicList) {
                        if (path.equals(music.getPath())) {
                            list.add(music);
                            break;
                        }
                    }
                }
                App.favoriteList = list;
            }
        };
        //建立连接
        observable.subscribe(observer);

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true, priority = 100)
    public void switchFragment(BaseFragment fragment) {
        if (fragment == null) {
            ExceptionUtil.illegaArgument("fragment is null");
        }
        baseFragment = fragment;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.ll_container, fragment);
        transaction.commit();
    }

    @Override
    public boolean onBackPressed() {
        if (baseFragment instanceof MusicNativeHomeFragment) {
            return false;
        } else {
            if (baseFragment.onBackPressed()) {
                initView();
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
