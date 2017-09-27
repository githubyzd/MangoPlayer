package com.mango.player.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * @创建人
 * @创建时间 2016/7/23  17:20
 */
public class Video implements Serializable {
    private int id = 0;//视频id
    private String path = null;//视频路径
    private String name = null;//视频名字
    private String resolution = null;// 分辨率
    private String size = "";//视频大小
    private String date = "";//视频日期
    private String duration = "";//视频长度
    private Bitmap thumbnail = null;//缩略图

    public Video(int id, String path, String name, String resolution, String size, String date, String duration, Bitmap bitmap) {
        this.id = id;
        this.path = path;
        this.name = name;
        this.resolution = resolution;
        this.size = size;
        this.date = date;
        this.duration = duration;
        this.thumbnail = bitmap;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", resolution='" + resolution + '\'' +
                ", size='" + size + '\'' +
                ", date='" + date + '\'' +
                ", duration='" + duration + '\'' +
                ", thumbnail=" + thumbnail +
                '}';
    }
}
