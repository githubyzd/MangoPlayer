package com.mango.player.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.activity.App;
import com.mango.player.base.BaseFragment;
import com.mango.player.util.ACache;
import com.mango.player.util.ApplicationConstant;
import com.mango.player.util.ExceptionUtil;
import com.mango.player.util.LogUtil;
import com.mango.player.util.MusicController;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

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
    LinearLayout llHome;

    private BaseFragment baseFragment;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_music_native;
    }

    @Override
    public void initView() {
        initController();
        MusicNativeHomeFragment fragment = new MusicNativeHomeFragment();
        fragment.setController(this);
        switchFragment(fragment);
    }

    private void initController() {
        final MusicController controller = MusicController.getInstance(getActivity());
        controller.initView(musicPlayController);
        //创建一个上游 Observable：
        Observable<Observer> observable = Observable.create(new ObservableOnSubscribe<Observer>() {
            @Override
            public void subscribe(ObservableEmitter<Observer> emitter) throws Exception {
                while (true){
                    if (App.musicList != null) {
                        controller.initData(App.musicList);
                        String index = ACache.getInstance(getContext()).getAsString(ApplicationConstant.MUSIC_INDEX);
                        controller.setCurrentIndex(Integer.parseInt(index));
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
                LogUtil.logByD("接受数据");
                controller.updateView();
            }
        };
        //建立连接
        observable.subscribe(observer);

    }

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

    public boolean onBackPressed(){
        if (baseFragment instanceof MusicNativeHomeFragment){
            return false;
        }else{
            initView();
            return true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
