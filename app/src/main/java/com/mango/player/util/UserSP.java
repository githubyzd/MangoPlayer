package com.mango.player.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author liangruiyu
 * @ClassName: UserSP
 * @Description: SharedPreferences的操作类，保存和获取更简单，本类单例
 * @create 2013-8-20 下午4:51:43
 */
public class UserSP {

    private static final String name = "SharedPreferences";//表明
    public static final String MUSIC_LIST = "music_list";
    public static final String VIDEO_LIST = "video_list";
    private static UserSP instance;
    public static SharedPreferences sp;
    public static Editor ed;

    private UserSP() {
        super();
    }

    /**
     * 获取实例
     *
     * @param context
     * @return
     */
    synchronized public static UserSP getInstance(Context context) {
        if (instance == null) {
            instance = new UserSP();
            sp = context.getSharedPreferences(name,
                    Context.MODE_PRIVATE);
            ed = sp.edit();
        }
        return instance;
    }

    public boolean putBoolean(String key, boolean value) {
        return ed.putBoolean(key, value).commit();
    }

    public boolean getBoolean(String key, boolean value) {
        return sp.getBoolean(key, value);
    }

    public boolean putFloat(String key, float value) {
        return ed.putFloat(key, value).commit();
    }

    public float getFloat(String key, float value) {
        return sp.getFloat(key, value);
    }

    public boolean putInt(String key, int value) {
        return ed.putInt(key, value).commit();
    }

    public int getInt(String key, int value) {
        return sp.getInt(key, value);
    }

    public boolean putLong(String key, Long value) {
        return ed.putLong(key, value).commit();
    }

    public long getLong(String key, Long value) {
        return sp.getLong(key, value);
    }

    public boolean putString(String key, String value) {
        return ed.putString(key, value).commit();
    }

    public String getString(String key, String value) {
        return sp.getString(key, value);
    }
}
