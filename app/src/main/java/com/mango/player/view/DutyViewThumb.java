package com.mango.player.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.mango.player.R;
import com.mango.player.util.AppUtil;

public class DutyViewThumb extends View {

    int width;
    int height;

    private boolean selectState = false;

    public void setSelected(boolean state)
    {
        if(selectState != state)
        {
            selectState = state;
            postInvalidate();
        }
    }

    public boolean isSelected()
    {
        return selectState;
    }

    public DutyViewThumb(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        try {
            thumbUp = BitmapFactory.decodeResource(getResources(), R.drawable.point_up);
            upSrc.set(0, 0, thumbUp.getWidth(), thumbUp.getHeight());           

            thumbDn = BitmapFactory.decodeResource(getResources(), R.drawable.point_down);
            dnSrc.set(0,0,thumbDn.getWidth(),thumbDn.getHeight());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        /*
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    if(selectState == false)
                    {
                        selectState = true;
                        invalidate();
                        Log.i("userLog", "onSelected");
                    }
                    return true;


                default:
                    break;
                }
                return false;
            }
        });     */

    }


    private Bitmap thumbUp;
    private Bitmap thumbDn;
    private Rect upSrc = new Rect();
    private Rect dnSrc = new Rect();

    private Rect dstRect = new Rect();

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub

        try {
            if(isSelected())
            {
                canvas.drawBitmap(thumbDn, dnSrc, dstRect, null);
            }
            else {
                canvas.drawBitmap(thumbUp, upSrc, dstRect, null);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub

        if(MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY)
        {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        else {
            width = AppUtil.dp2px(getContext(), 40);
        }
        height = width;

        setMeasuredDimension(width, height);

        dstRect.set(width/3, height/3, width*2/3, height*2/3);
    }
}