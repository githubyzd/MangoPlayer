package com.mango.player.fragment;

import android.content.Intent;
import android.widget.LinearLayout;

import com.mango.player.R;
import com.mango.player.activity.WebActivity;
import com.mango.player.base.BaseFragment;
import com.mango.player.util.AppUtil;
import com.mango.player.util.ApplicationConstant;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by yzd on 2017/9/25 0025.
 */

public class VideoOnlineFragment extends BaseFragment {

    @BindView(R.id.tencent)
    LinearLayout tencent;
    @BindView(R.id.yuku)
    LinearLayout yuku;
    @BindView(R.id.aiqiyi)
    LinearLayout aiqiyi;
    @BindView(R.id.leshi)
    LinearLayout leshi;
    @BindView(R.id.sohu)
    LinearLayout sohu;
    @BindView(R.id.download)
    LinearLayout download;
    @BindView(R.id.wait)
    LinearLayout wait;
    Unbinder unbinder;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_video_online;
    }

    @Override
    public void initView() {
    }

    @OnClick(R.id.tencent)
    void tencent() {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra(ApplicationConstant.URL, "http://m.v.qq.com/");
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.yuku)
    void yuku() {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra(ApplicationConstant.URL, "http://www.youku.com/");
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.aiqiyi)
    void aiqiyi() {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra(ApplicationConstant.URL, "http://m.iqiyi.com/");
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.leshi)
    void leshi(){
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra(ApplicationConstant.URL, "http://m.le.com/");
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.sohu)
    void sohu(){
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra(ApplicationConstant.URL, "http://m.tv.sohu.com/film");
        getActivity().startActivity(intent);
    }

    @OnClick(R.id.download)
    void download(){
        AppUtil.showSnackbar(download,"暂未开放");
    }

    @OnClick(R.id.wait)
    void mWait(){
        AppUtil.showSnackbar(wait,"敬请期待");
    }
}
