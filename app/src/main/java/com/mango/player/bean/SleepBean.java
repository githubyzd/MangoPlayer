package com.mango.player.bean;

/**
 * Created by yzd on 2017/11/14 0014.
 */

public class SleepBean {
    @Override
    public String toString() {
        return "SleepBean{" +
                "isStop=" + isStop +
                ", isUpdateView=" + isUpdateView +
                ", sleepTime=" + sleepTime +
                ", millisUntilFinished=" + millisUntilFinished +
                '}';
    }

    private boolean isStop = false;

    public boolean isStop() {
        return isStop;
    }

    public void setStop(boolean stop) {
        isStop = stop;
    }

    public boolean isUpdateView() {
        return isUpdateView;
    }

    public void setUpdateView(boolean updateView) {
        isUpdateView = updateView;
    }

    public long getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    public long getMillisUntilFinished() {
        return millisUntilFinished;
    }

    public void setMillisUntilFinished(long millisUntilFinished) {
        this.millisUntilFinished = millisUntilFinished;
    }

    private boolean isUpdateView;
    private long sleepTime;
    private long millisUntilFinished;
}
