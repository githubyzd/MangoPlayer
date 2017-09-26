package com.mango.player.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yzd on 2017/9/25 0025.
 */

public abstract class BaseFragment extends Fragment {

    private View rootView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(),null);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        initData();
    }

    public abstract int getLayoutId();
    public abstract void initView();
    public void initData(){}
}
