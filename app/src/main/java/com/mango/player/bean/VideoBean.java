package com.mango.player.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2017/10/21.
 */

public class VideoBean implements Parcelable {

    private String name;
    private String time;
    private String thumbnail;
    private String videoUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.time);
        dest.writeString(this.thumbnail);
        dest.writeString(this.videoUrl);
    }

    public VideoBean() {
    }

    private VideoBean(Parcel in) {
        this.name = in.readString();
        this.time = in.readString();
        this.thumbnail = in.readString();
        this.videoUrl = in.readString();
    }

    public static final Parcelable.Creator<VideoBean> CREATOR = new Parcelable.Creator<VideoBean>() {
        public VideoBean createFromParcel(Parcel source) {
            return new VideoBean(source);
        }

        public VideoBean[] newArray(int size) {
            return new VideoBean[size];
        }
    };
}
