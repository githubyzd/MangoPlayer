package com.mango.player.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mango.player.R;
import com.mango.player.util.AppUtil;
import com.mango.player.util.LogUtil;

public class DutyView extends ViewGroup implements View.OnTouchListener {
    int width;
    int height;
    /**
     * duty最大值
     */
    public static final int MAX_DUTY = 100;

    public int getMaxDuty() {
        return MAX_DUTY;
    }

    /**
     * duty当前值
     */
    private int duty;

    public int getDuty() {
        return duty;
    }

    public void setDuty(int value) {
        if (value >= 0 && value <= MAX_DUTY) {
            duty = value;
            calculateThumbPosition();
            postInvalidate();
//            MyBaseActivity.sendMessage(duty+"");
            LogUtil.logByD("up: " + duty);
        }
    }

    public void addDuty() {
        if (duty < MAX_DUTY) {
            duty++;
            calculateThumbPosition();
            postInvalidate();
            LogUtil.logByD("add: " + duty);
//            MyBaseActivity.sendMessage("add"+1);
        }
    }

    public void cutDuty() {
        if (duty > 0) {
            duty--;
            calculateThumbPosition();
            postInvalidate();
            LogUtil.logByD("down: " + duty);
//            MyBaseActivity.sendMessage("cut"+1);
        }
    }

    /**
     * 文本大小
     */
    private float textSize;

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    /**
     * 文本颜色
     */
    public static final int DEFAULT_TEXT_COLOR = 0xff326ee9;
    private int textColor = DEFAULT_TEXT_COLOR;

    public void setTextColor(int color) {
        this.textColor = color;
        postInvalidate();
    }

    public int getTextColor() {
        return this.textColor;
    }

    /**
     * 圆弧颜色
     */
    public static final int DEFAULT_PROGRESS_BG_COLOR = 0xffffffff;
    private int progressBgColor = DEFAULT_PROGRESS_BG_COLOR;

    public int getProgressBgColor() {
        return progressBgColor;
    }

    public void setProgressBgColo(int arcColor) {
        this.progressBgColor = arcColor;
        postInvalidate();
    }

    public static final int DEFAULT_PROGRESS_COLOR = 0xffff4e00;

    private int ProgressColor = DEFAULT_PROGRESS_COLOR;

    public int getProgressColor() {
        return ProgressColor;
    }

    public void setProgressColor(int value) {
        this.ProgressColor = value;
        postInvalidate();
    }

    /**
     * 圆弧尺寸
     */
    private float arcWidth;
    /**
     * 抗锯齿
     */
    private PaintFlagsDrawFilter pfd;

    public float getArcWidth() {
        return arcWidth;
    }

    public void setArcWidth(float arcWidth) {
        this.arcWidth = arcWidth;
    }

    public void setRotatDrawableResource(int id) {
        BitmapDrawable drawable = (BitmapDrawable) getContext().getResources().getDrawable(id);
        setRotatDrawable(drawable);
    }

    public void setRotatDrawable(BitmapDrawable drawable) {
        thumbUp = drawable.getBitmap();
        upSrc.set(0, 0, thumbUp.getWidth(), thumbUp.getHeight());

        postInvalidate();
    }

    private Context mContext;

    public DutyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DutyView);
        pfd = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        duty = array.getInteger(R.styleable.DutyView_initDuty, 0);
        textColor = array.getColor(R.styleable.DutyView_textColor, DEFAULT_TEXT_COLOR);
        textSize = array.getDimension(R.styleable.DutyView_textSize, AppUtil.sp2px(context, 18));
        progressBgColor = array.getColor(R.styleable.DutyView_progressBgColor, DEFAULT_PROGRESS_BG_COLOR);
        ProgressColor = array.getColor(R.styleable.DutyView_progressColor, DEFAULT_PROGRESS_COLOR);
        arcWidth = array.getDimension(R.styleable.DutyView_arcWidth, AppUtil.dp2px(context, 5));
        array.recycle();
        arcPaint = new Paint();
        arcPaint.setColor(progressBgColor);
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(arcWidth);
        textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
        setOnTouchListener(this);
        this.setBackgroundColor(0x00ffffff);
        try {
            bgPaint = new Paint();
            bgPaint.setColor(Color.GRAY);
            bgPaint.setStrokeWidth(6);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    Paint arcPaint = null;
    RectF arcRectF = new RectF();
    Paint bgPaint = null;
    Canvas bgcanvas;
    Paint textPaint = null;

    private void drawText(Canvas canvas) {
        String text = String.valueOf((int) duty);

        float length = textPaint.measureText(text);
        float x = width / 2 - length / 2;
        float y = height / 2 + length / 4;
        canvas.drawText(text, x, y, textPaint);
    }

    private Bitmap thumbUp;
    private Bitmap thumbDn;
    private Rect upSrc = new Rect();
    private Rect dnSrc = new Rect();
    private Rect dstRect = new Rect();
    //DutyViewProgress progressView;
    DutyViewThumb thumbView;
    int thumbWidth;
    int thumbHeight;

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        //抗锯齿
        canvas.setDrawFilter(pfd);
        canvas.drawBitmap(thumbUp, upSrc, dstRect, null);
        drawProgressBar(canvas);
        drawText(canvas);
        drawgraduation(canvas);

        super.onDraw(canvas);
    }

    private int lineHeight = 20;

    private void drawProgressBar(Canvas canvas) {
        // TODO Auto-generated method stub
//      arcPaint.setColor(progressBgColor);
//      canvas.drawArc(arcRectF, 135, 270, false, arcPaint);
//      arcPaint.setColor(ProgressColor);
//      canvas.drawArc(arcRectF, 135, 270*duty/MAX_DUTYe, false, arcPaint);
    }

    private int countGraduation = 20;//刻度数

    private void drawgraduation(Canvas canvas) {
        canvas.save();
        float degrees = (float) (270.0 / countGraduation);
        float Graduation = ((float) duty * 20 / MAX_DUTY);
        int pointX = width / 2;
        int pointY = height / 2;
        canvas.save();
        canvas.translate(0, pointY);
        canvas.rotate(-45, pointX, 0);
        for (int i = 0; i < countGraduation + 1; i++) {
            if (i <= Graduation || duty == MAX_DUTY) {
                bgPaint.setColor(progressBgColor);
            } else {
                bgPaint.setColor(Color.GRAY);
            }
            if (i == 0 || i == countGraduation) {
                canvas.drawLine(0, 0, lineHeight, 0, bgPaint);
            } else {
                canvas.drawCircle(10, 10, 10, bgPaint);
            }
            canvas.rotate(degrees, pointX, 0);
        }
        canvas.restore();
//      for(int i= 0;i<)
//      canvas.drawLine();
    }

    LinearLayout.LayoutParams lp;
    int thumb;//thumb的宽高

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        height = MeasureSpec.getSize(heightMeasureSpec);
        width = height;
        setMeasuredDimension(width, height);
        if (thumbView == null) {
            thumbView = (DutyViewThumb) getChildAt(0);
            thumb = height / 5;
            lp = new LinearLayout.LayoutParams(thumb, thumb);
            thumbView.setLayoutParams(lp);//自适应大小
            thumbWidth = thumb / 2;
            thumbHeight = thumb / 2;
        }
        arcRectF.set(thumbWidth, thumbWidth, width - thumbWidth, height - thumbWidth);
        dstRect.set(40, 40, width - 40, height - 40);
        invaliArea.set(width, height, width * 3 / 2, height * 3 / 2);

        setRotatDrawableResource(R.drawable.cilcle);
    }

    RectF thumbRect = new RectF();


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // TODO Auto-generated method stub
        try {
            if (thumbView == null) {
                thumbView = (DutyViewThumb) getChildAt(0);
                lp = new LinearLayout.LayoutParams(thumb, thumb);
                thumbView.setLayoutParams(lp);
                thumbWidth = thumb / 2;
                thumbHeight = thumb / 2;
            }
            calculateThumbPosition();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /**
     * 输入坐标，求出对应象限
     *
     * @param x
     * @param y
     * @return
     */
    private static int getQuadrant(double x, double y) {
        if (x >= 0) {
            return y >= 0 ? 1 : 4;
        } else {
            return y >= 0 ? 2 : 3;
        }
    }

    /**
     * 获取指定坐标对应的角度
     *
     * @param xTouch
     * @param yTouch
     * @return
     */
    double angle;
    double changeAngle;

    private double getAngle(double xTouch, double yTouch) {
        double x = xTouch - (width / 2d);
        double y = height - yTouch - (height / 2d);
        double a;
        switch (getQuadrant(x, y)) {
            case 1:
                return Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
            case 2:
            case 3:
                return 180 - (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);

            case 4:
                return 360 + Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;

            default:
                // ignore, does not happen
                return 0;
        }
//      switch (getQuadrant(x, y)) {
//      case 1:
//          a = 180 - (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
//          break;
//          case 2:
//      case 4:
//          a =  360 + Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI;
//          break;
//      default:
//          // ignore, does not happen
//          a =  0;
//          break;
//      }
//
//      changeAngle = a-angle;
//      angle = a;
//
//      return angle;
    }

    private final float angleRate = MAX_DUTY / 270f;

    /**
     * 角度对应的百分比
     *
     * @param angle
     * @return
     */
    private int angle2pesent(double angle) {
        //无效角度
        if (angle < 300 && angle > 240)
            return -1;

        if (angle > 225)
            angle -= 360;

        return (int) (Math.abs(angle - 225) * angleRate);
    }

    /**
     * 根据当前的duty值计算thumb的位置
     */
    private void calculateThumbPosition() {
        double angle = duty / angleRate;
        angle += 135;
        if (angle > 360)
            angle -= 360;
        float xPoint = (float) (width / 2 + (width / 2 - thumbWidth * 2) * Math.cos(Math.toRadians(angle)));//修改这里可修改thumb的位置
        float yPoint = (float) (height / 2 + (height / 2 - thumbHeight * 2) * Math.sin(Math.toRadians(angle)));
        thumbRect.left = xPoint - thumbWidth;
        thumbRect.top = yPoint - thumbHeight;
        thumbRect.right = xPoint + thumbWidth;
        thumbRect.bottom = yPoint + thumbHeight;
        thumbView.layout((int) thumbRect.left,
                (int) thumbRect.top,
                (int) thumbRect.right,
                (int) thumbRect.bottom);
    }

    //触摸的无效区域
    RectF invaliArea = new RectF();
    float last_x;
    float last_y;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        float x = event.getX();
        float y = event.getY();
        //触摸无效区域禁止横向滑动
        if (invaliArea.contains(x, y)) {
            if (thumbView.isSelected()) {
                thumbView.setSelected(false);
                if (listener != null)
                    listener.onDutyChanged(duty);
            }
            LogUtil.logByD("userLog", "无效区域");
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtil.logByD("userLog", "action down");
                if (thumbView.isSelected()) {
                /*thumbView.setSelected(false);
                if(listener != null)
                {
                    listener.onDutyChanged(duty);
                }   */
                } else {
                    //是否点击滑动块
                    if (thumbRect.contains((int) x, (int) y)) {
                        thumbView.setSelected(true);
                        last_x = x;
                        last_y = y;
                        LogUtil.logByD("userLog", "点击滑块");
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (thumbView.isSelected()) {
                    LogUtil.logByD("userLog", "滑动");
                    //触摸不连续，防止多点触控
                    if (Math.abs(x - last_x) > 150 || Math.abs(y - last_y) > 150) {
                        LogUtil.logByD("userLog", "滑动不连续");
                        thumbView.setSelected(false);
                        if (listener != null) {
                            listener.onDutyChanged(duty);
                        }
                        return true;
                    }
                    //计算角度和百分比
                    double angle = getAngle(x, y);
                    int _duty = angle2pesent(angle);

                    if (_duty != -1) {
                        setDuty(_duty);
                    } else {
                        if (duty < 10) {
                            setDuty(0);
                        } else if (duty > 90) {
                            setDuty(100);
                        }
                        thumbView.setSelected(false);
                        if (listener != null) {
                            listener.onDutyChanged(duty);
                        }
                    }
                    last_x = x;
                    last_y = y;
                    calculateThumbPosition();
                }
                break;
            case MotionEvent.ACTION_UP:
                LogUtil.logByD("userLog", "action up");
                if (thumbView.isSelected()) {
                    thumbView.setSelected(false);
                    if (listener != null) {
                        listener.onDutyChanged(duty);
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }

    public interface onDutyChangedListener {
        void onDutyChanged(int currentDuty);
    }

    onDutyChangedListener listener = null;

    public void setOnDutyChangedListener(onDutyChangedListener listener) {
        this.listener = listener;
    }


}