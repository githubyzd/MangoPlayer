package com.mango.player.util;

import io.vov.vitamio.MediaPlayer;

/**
 * Created by yzd on 2017/9/26 0026.
 */

public class ApplicationConstant {
    public final static boolean DEBUG = true;

    public final static boolean VIDEO_DEBUG = false;

    public final static long DEFAULT_CUSTOM_MEDIA_CONTROLLER_SHOW_TIME = 5000;
    //设置视频质量，低、中、高
    public final static int DEFAULT_VIDEO_QUALITY = MediaPlayer.VIDEOQUALITY_HIGH;
    //视频播放速度
    public final static float DEFAULT_PLAY_SPEED = 1.0F;
    //设置缓存大小，默认1024KB
    public final static long DEFAULT_BUFFER_SIZE = 1024;
    //设置是否显示字幕
    public final static boolean DEFAULT_SUB_SHOWN = false;
    //设置音轨
    public final static int DEFAULT_AUDIO_TRACK = 0;
    //音量
    public final static float DEFAULT_VOLUME = 0;
    //视频缩放
    public final static float DEFAULT_ASPECTRATIO = 0f;

    public static final String VIDEO_LIST_KEY = "video_list_key";
    public static final String VIDEO_POSITION_KEY = "video_position_key";
    public static final String VIDEO_DATA_KEY = "video_data_key";
    public static final String URL = "url";
    public static final String MUSIC_INDEX = "music_index";


    public final static int ACTIVITY_REQUEST_TK_CODE = 1024;
    public final static int ACTIVITY_REQUEST_RESET_PWD_CODE = ACTIVITY_REQUEST_TK_CODE + 1;
    public final static int ACTIVITY_REQUEST_IMAGE = ACTIVITY_REQUEST_RESET_PWD_CODE + 1;
    public static final int ACTIVITY_REQUEST_CODE_LOGIN = ACTIVITY_REQUEST_IMAGE + 1;
    public static final int ACTIVITY_REQUEST_CODE_SCAN = ACTIVITY_REQUEST_CODE_LOGIN + 1;
    public static final int ACTIVITY_REQUEST_CODE_VOICE = ACTIVITY_REQUEST_CODE_SCAN + 1;
    public static final int ACTIVITY_REQUEST_CODE_ALI_PAY = ACTIVITY_REQUEST_CODE_VOICE + 1;
    public static final int ACTIVITY_REQUEST_CODE_WX_PAY = ACTIVITY_REQUEST_CODE_ALI_PAY + 1;
    public static final int ACTIVITY_REQUEST_LOCATION = ACTIVITY_REQUEST_CODE_WX_PAY + 1;
    public static final int ACTIVITY_REQUEST_BIND_PHONE = ACTIVITY_REQUEST_LOCATION + 1;
    public static final int ACTIVITY_REQUEST_BIND_CARD = ACTIVITY_REQUEST_BIND_PHONE + 1;
    public static final int ACTIVITY_REQUEST_GET_PHOTO = ACTIVITY_REQUEST_BIND_CARD + 1;


    //音乐播放相关常量
    public static final int WHAT_REGIST_MSGER_MAINACTIVITY = 11;
    public static final int WHAT_REGIST_MSGER_LIST_ACTIVITY = 12;
    public static final int WHAT_UN_REGIST_MSGER_LIST_ACTIVITY = 13;
    public static final int WHAT_PLAY_PLAY_BTN = 20;
    public static final int WHAT_PLAY_NEXT_BTN = 21;
    public static final int WHAT_PLAY_PRE_BTN = 22;
    public static final int WHAT_PLAY_DURATION = 23;
    public static final int WHAT_CHANGE_PROGRESS = 24;
    public static final int WHAT_MUSIC_SELECTED = 25;
    public static final int WHAT_PLAYED = 26;
    public static final String TAG_SERVICE = "service";
    public static final String IS_PLAYING = "isPlaying";
    public static final String CURRENT_MUSIC = "current_music";
    public static final String CURRENT_MUSIC_LIST = "current_music_list";

    //视频类型常量
    public static final int VIDEO_NATIVE_TYPE = 1;
    public static final int VIDEO_ONLINE_TYPE = 2;
    public static final String VIDEO_TYPE = "video_type";
    public static final String VIDEO_ONLINE = "video_online";
    public static final String VIDEO = "video";


    public static String[] tabs = {"最新", "乱伦", "自拍偷拍","SM", "自慰", "日韩", "欧美", "高清", "群交", "中文字幕", "口交肛交", "有码", "无码", "同性"};

}
