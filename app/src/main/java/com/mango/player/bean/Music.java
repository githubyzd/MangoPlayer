package com.mango.player.bean;


import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.mango.player.util.PinyinUtils;

public class Music implements Comparable<Music>,Parcelable {
    /**
     * 歌曲名
     */
    private String name;
    /**
     * 路径
     */
    private String path;
    /**
     * 所属专辑
     */
    private String album;
    /**
     * 艺术家(作者)
     */
    private String artist;
    /**
     * 文件大小
     */
    private long size;
    /**
     * 时长
     */
    private int duration;
    /**
     * 专辑封面
     */
    private Bitmap thumbnail;

    private String pinyin;

    public Music(String name, String path, String album, String artist, long size, int duration, Bitmap bitmap) {
        this.name = name;
        this.path = path;
        this.album = album;
        this.artist = artist;
        this.size = size;
        this.duration = duration;
        this.thumbnail = bitmap;

        pinyin = PinyinUtils.getPinyin(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int compareTo(Music music) {
        return this.pinyin.compareTo(music.getPinyin());
    }

    @Override
    public String toString() {
        return "Music{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                ", size=" + size +
                ", duration=" + duration +
                ", thumbnail=" + thumbnail +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.path);
        dest.writeString(this.album);
        dest.writeString(this.artist);
        dest.writeLong(this.size);
        dest.writeInt(this.duration);
        dest.writeParcelable(this.thumbnail, flags);
        dest.writeString(this.pinyin);
    }

    protected Music(Parcel in) {
        this.name = in.readString();
        this.path = in.readString();
        this.album = in.readString();
        this.artist = in.readString();
        this.size = in.readLong();
        this.duration = in.readInt();
        this.thumbnail = in.readParcelable(Bitmap.class.getClassLoader());
        this.pinyin = in.readString();
    }

    public static final Parcelable.Creator<Music> CREATOR = new Parcelable.Creator<Music>() {
        @Override
        public Music createFromParcel(Parcel source) {
            return new Music(source);
        }

        @Override
        public Music[] newArray(int size) {
            return new Music[size];
        }
    };
}
