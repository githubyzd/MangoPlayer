package com.mango.player.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.base.BaseFragment;
import com.mango.player.util.ExceptionUtil;
import com.mango.player.util.MusicController;

import butterknife.BindView;

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
        MusicController controller = MusicController.getInstance();
        controller.initView(musicPlayController);
    }

    public void switchFragment(Fragment fragment) {
        if (fragment == null) {
            ExceptionUtil.illegaArgument("fragment is null");
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.ll_container, fragment);
        transaction.commit();
    }

}
