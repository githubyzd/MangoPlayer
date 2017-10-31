package com.mango.player.bean;

/**
 * Created by yzd on 2017/10/31 0031.
 */

public class UpdateViewBean {
    private Music music;
    private int index;
    private int duration;
    private int currentDuration;
    private boolean isPlaying;

    @Override
    public String toString() {
        return "UpdateViewBean{" +
                "music=" + music +
                ", index=" + index +
                ", duration=" + duration +
                ", currentDuration=" + currentDuration +
                ", isPlaying=" + isPlaying +
                '}';
    }

    public Music getMusic() {
        return music;
    }

    public void setMusic(Music music) {
        this.music = music;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCurrentDuration() {
        return currentDuration;
    }

    public void setCurrentDuration(int currentDuration) {
        this.currentDuration = currentDuration;
    }
    public boolean getPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

}
