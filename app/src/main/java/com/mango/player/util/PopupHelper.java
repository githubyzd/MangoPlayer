package com.mango.player.util;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by yzd on 2017/9/4 0004.
 */

public class PopupHelper {
    private static PopupWindow popupWindow;
    private View contentView;//popup的视图
    private int width;//popup的宽
    private int height;//popup的高
    private boolean focusable = false;//popup是否获取焦点
    private int animationStyle = -1;//是否有动画
    private View anchorView;//popup显示在位置基准
    private int xoff;//在x轴方向的偏移
    private int yoff;//在y轴方向的偏移
    private int gravity = -1;//popup相对父控件的位置
    private View parentView;//父控件
    private boolean touchable = true;//popup是否响应touch事件
    private boolean outSideTouchable = false;//popup外部区域是否可以点击
    private boolean withShadow = true;//是否有阴影遮罩
    private Drawable backgroundDrawable;//popup的背景图
    private OnDismissListener dismissListener;//popup消失监听器
    private static Activity mContext;


    private PopupHelper(Builder builder) {
        contentView = builder.contentView;
        width = builder.width;
        height = builder.height;
        focusable = builder.focusable;
        animationStyle = builder.animationStyle;
        anchorView = builder.anchorView;
        xoff = builder.xoff;
        yoff = builder.yoff;
        gravity = builder.gravity;
        parentView = builder.parentView;
        touchable = builder.touchable;
        outSideTouchable = builder.outSideTouchable;
        withShadow = builder.withShadow;
        backgroundDrawable = builder.backgroundDrawable;
        dismissListener = builder.dismissListener;
    }

    /**
     * 相对某个控件的位置（正左下方），无偏移
     */
    public PopupHelper showAsDropDown() {
        checkParams();

        if (anchorView == null) {
            ExceptionUtil.illegaArgument("anchorView can not be null");
        }

        setParams();

        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (gravity == -1) {
                gravity = Gravity.CENTER;
            }
            popupWindow.showAsDropDown(anchorView, xoff, yoff, gravity);
        } else {
            popupWindow.showAsDropDown(anchorView, xoff, yoff);
        }

        setDismissListener();

        return this;
    }

    /**
     * 指定位置弹窗
     *
     * @return
     */
    public PopupHelper showAtLocation() {
        checkParams();

        if (parentView == null) {
            ExceptionUtil.illegaArgument("parentView can not be null");
        }

        if (gravity == -1) {
            gravity = Gravity.CENTER;
        }

        setParams();
        if (popupWindow.isShowing()) {
            popupWindow.dismiss();
        }

        if (withShadow) {
            setShadow(0.5f);
        }

        setDismissListener();

        popupWindow.showAtLocation(parentView, gravity, xoff, yoff);

        return this;
    }

    private void setDismissListener() {
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setShadow(1.0f);
                if (dismissListener != null) {
                    dismissListener.onDismiss();
                }
            }
        });
    }

    public boolean isShowing() {
        if (popupWindow == null) {
            return false;
        }
        return popupWindow.isShowing();
    }

    private void setShadow(float shadow) {
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = shadow;
        mContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mContext.getWindow().setAttributes(lp);
    }

    public void dismiss() {
        popupWindow.dismiss();
    }

    /**
     * 设置必要的参数
     */
    private void setParams() {
        popupWindow.setContentView(contentView);
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);

        popupWindow.setAnimationStyle(animationStyle);
        popupWindow.setFocusable(focusable);
        popupWindow.setTouchable(touchable);
        popupWindow.setOutsideTouchable(outSideTouchable);
        popupWindow.setBackgroundDrawable(backgroundDrawable);
    }

    /**
     * 检验必要的参数
     */
    private void checkParams() {
        if (contentView == null) {
            ExceptionUtil.illegaArgument("contentView can not be null");
        }
        if (width == 0) {
            ExceptionUtil.illegaArgument("width can not be null");
        }
        if (height == 0) {
            ExceptionUtil.illegaArgument("height can not be null");
        }
    }


    public static class Builder {
        private View contentView;
        private int width;
        private int height;
        private boolean focusable = false;
        private int animationStyle = -1;
        private View anchorView;
        private int xoff;
        private int yoff;
        private int gravity = -1;
        private View parentView;
        private boolean touchable = true;
        private boolean outSideTouchable = false;
        private boolean withShadow = true;
        private Drawable backgroundDrawable;
        private OnDismissListener dismissListener;

        public Builder(Activity activity) {
            mContext = activity;
        }

        public Builder contentView(View contentView) {
            this.contentView = contentView;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder focusable(boolean focusable) {
            this.focusable = focusable;
            return this;
        }

        public Builder animationStyle(int animationStyle) {
            this.animationStyle = animationStyle;
            return this;
        }

        public Builder anchorView(View anchorView) {
            this.anchorView = anchorView;
            return this;
        }

        public Builder xoff(int xoff) {
            this.xoff = xoff;
            return this;
        }

        public Builder yoff(int yoff) {
            this.yoff = yoff;
            return this;
        }

        public Builder gravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder parentView(View parentView) {
            this.parentView = parentView;
            return this;
        }

        public Builder touchable(boolean touchable) {
            this.touchable = touchable;
            return this;
        }

        public Builder outSideTouchable(boolean outSideTouchable) {
            this.outSideTouchable = outSideTouchable;
            return this;
        }

        public Builder withShadow(boolean withShadow) {
            this.withShadow = withShadow;
            return this;
        }

        public Builder background(Drawable backgroundDrawable) {
            this.backgroundDrawable = backgroundDrawable;
            return this;
        }

        public Builder dismissListener(OnDismissListener listener) {
            this.dismissListener = listener;
            return this;
        }

        public PopupHelper build() {
            if (popupWindow == null) {
                popupWindow = new PopupWindow();
            }
            return new PopupHelper(this);
        }
    }

    public interface OnDismissListener {
        void onDismiss();
    }
}
