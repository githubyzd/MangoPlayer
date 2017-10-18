package com.mango.player.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.mango.player.R;


public abstract class BasePager {

	protected Context mContext;// 提供给子类使用的上下文对象
	
	public View rootView;// 保存自己身上的布局
	protected FrameLayout fl_basepager_container;

	public BasePager(Context context){
		this.mContext = context;
		rootView = getLayout();
	}

	public View getLayout() {
		View view = View.inflate(mContext, R.layout.basepager, null);
		fl_basepager_container = (FrameLayout) view.findViewById(R.id.fl_basepager_container);

		return view;
	}
	/**
	 * 需要子类实现
	 * @return
     */
	public abstract void initView();

	/**
	 *用来注册监听器和适配器，注册广播接收者
	 */
	protected abstract void initListener();

	/**
	 * 用来处理点击事件
	 */
	protected abstract void processClick(View view);

	/**
	 * 让子类更新布局，不必须实现
	 */
	public void initData(){

	}
}