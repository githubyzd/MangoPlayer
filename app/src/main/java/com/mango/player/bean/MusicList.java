package com.mango.player.bean;

import com.mango.player.dao.StringConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

import java.util.ArrayList;
import java.util.List;


@Entity
public class MusicList {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "list_name")
    @NotNull
    @Unique
    private String name;

    @Convert(columnType = String.class, converter = StringConverter.class)
    private List<String> musics;

    public List<String> getMusics() {
        return this.musics;
    }

    public void setMusics(ArrayList<String> musics) {
        this.musics = musics;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMusics(List<String> musics) {
        this.musics = musics;
    }

    @Generated(hash = 611921661)
    public MusicList(Long id, @NotNull String name, List<String> musics) {
        this.id = id;
        this.name = name;
        this.musics = musics;
    }

    @Generated(hash = 1135234633)
    public MusicList() {
    }
}