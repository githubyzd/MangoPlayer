package com.mango.player.util;

/**
 * Created by yzd on 2017/8/21 0021.
 */

public class ExceptionUtil {

    /**
     * 非法参数异常
     * @param msg
     * @param params
     */
    public static void illegaArgument(String msg, Object... params) {
        throw new IllegalArgumentException(String.format(msg, params));
    }

}
