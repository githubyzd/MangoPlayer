package com.mango.player.bean;

/**
 * Created by yzd on 2017/10/30 0030.
 */

public class MusicServiceBean {
    private PlayMode playMode;
    private int index;
    private int msec;

    @Override
    public String toString() {
        return "MusicServiceBean{" +
                "playMode=" + playMode +
                ", index=" + index +
                ", msec=" + msec +
                '}';
    }

    public PlayMode getPlayMode() {
        return playMode;
    }

    public void setPlayMode(PlayMode playMode) {
        this.playMode = playMode;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getMsec() {
        return msec;
    }

    public void setMsec(int msec) {
        this.msec = msec;
    }
}
