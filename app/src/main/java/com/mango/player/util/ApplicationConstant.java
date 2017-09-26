package com.mango.player.util;

/**
 * Created by yzd on 2017/9/26 0026.
 */

public class ApplicationConstant {
    public final static boolean DEBUG = true;


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
}
