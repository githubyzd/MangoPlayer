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
    public static final String MUSIC_CURRENT_POSITION = "music_current_position";
    public static final String MUSIC_LIST_KEY = "music_list_key";
    public static final String MUSIC_DATA_KEY = "music_data_key";
    public static final String MUSIC_FAVORITE_KEY = "music_favorite_key";
    public static final String MUSIC_PLAYMODE_KEY = "music_playmode_key";
    public static final String MUSIC_LIST_ADD_DATA = "music_list_add_data";



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


    //视频类型常量
    public static final int VIDEO_NATIVE_TYPE = 1;
    public static final int VIDEO_ONLINE_TYPE = 2;
    public static final String VIDEO_TYPE = "video_type";
    public static final String VIDEO_ONLINE = "video_online";
    public static final String VIDEO = "video";

    public static String [] tabs = {"tab1","tab2"};

}
