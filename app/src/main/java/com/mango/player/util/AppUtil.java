package com.mango.player.util;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.mango.player.activity.ScanActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by yzd on 2017/9/26 0026.
 */

public class AppUtil {
    public static void showSnackbar(View view, String msg) {
        Snackbar.make(view, msg, android.support.design.widget.Snackbar.LENGTH_LONG)
                .show();
    }

    public static void startScanActivity(Context context, Class<ScanActivity> clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    /**
     * 返回byte的数据大小对应的文本
     *
     * @param size
     * @return
     */
    public static String getDataSize(long size) {
        DecimalFormat formater = new DecimalFormat("####.00");
        if (size < 1024) {
            return size + "bytes";
        } else if (size < 1024 * 1024) {
            float kbsize = size / 1024f;
            return formater.format(kbsize) + "KB";
        } else if (size < 1024 * 1024 * 1024) {
            float mbsize = size / 1024f / 1024f;
            return formater.format(mbsize) + "MB";
        } else if (size < 1024 * 1024 * 1024 * 1024) {
            float gbsize = size / 1024f / 1024f / 1024f;
            return formater.format(gbsize) + "GB";
        } else {
            return "size: error";
        }
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param format  格式
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 时长转换
     */
    public static String timeLenghtFormast(long time) {
        StringBuffer sb = new StringBuffer();
        if (time < 1000) {
            sb = sb.append(time).append("ms");
        } else if (time >= 1000 && time < (1000 * 60)) {
            int s = (int) (time / 1000);
            sb = sb.append(s).append("s");
        } else if (time >= (1000 * 60) && time < (1000 * 60 * 60)) {
            int m = (int) (time / (1000 * 60));
            if (m < 10) {
                sb = sb.append("0").append(m);
            } else {
                sb = sb.append(m);
            }
            int s = (int) (time % (1000 & 60));
            if (s < 10) {
                sb = sb.append(":0").append(s);
            } else {
                sb = sb.append(s);
            }
        } else if (time >= (1000 * 60 * 60)) {
            int h = (int) (time / (1000 * 60 * 60));
            if (h < 10) {
                sb = sb.append("0").append(h).append(":");
            } else {
                sb.append(h).append(":");
            }
            int myu = (int) (time % (1000 * 60 * 60));
            int m = myu / (1000 * 60);
            if (m <10) {
                sb = sb.append("0").append(m).append(":");
            }else {
                sb = sb.append(m).append(":");
            }
            int syu = (int) (time % (1000 * 60 * 60 *60));
            int s = syu / (1000 * 60 * 60);
            if (s < 10) {
                sb = sb.append("0").append(s);
            }else {
                sb.append(s);
            }
        }
        return sb.toString();
    }
}
