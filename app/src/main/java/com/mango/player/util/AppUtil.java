package com.mango.player.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.mango.player.R;
import com.mango.player.activity.ScanActivity;
import com.mango.player.activity.SettingsActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static com.mango.player.util.ApplicationConstant.SKIN;


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

    public static void startSettingActivity(Context context, Class<SettingsActivity> clazz) {
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
            if (m < 10) {
                sb = sb.append("0").append(m).append(":");
            } else {
                sb = sb.append(m).append(":");
            }
            int syu = (int) (time % (1000 * 60 * 60 * 60));
            int s = syu / (1000 * 60 * 60);
            if (s < 10) {
                sb = sb.append("0").append(s);
            } else {
                sb.append(s);
            }
        }
        return sb.toString();
    }

    /**
     * 时长转换
     */
    public static String timeLenghtFormast(int time) {
        StringBuffer sb = new StringBuffer();
        time = time / 1000;
        int h = time / (60 * 60);
        int m = (time - h * 3600) / 60;
        int s = (time - h * 3600) % 60;
        if (h > 0) {
            sb.append(h).append(":");
        }
        sb.append(m).append(":");
        if (s < 10) {
            sb.append("0").append(s);
        }else {
            sb.append(s);
        }

        return sb.toString();
    }

    public static float changePlaySpeed(float playSpped, boolean isUp) {
        if (playSpped == 0.5f) {
            playSpped = isUp ? 0.6f : 0.5f;
        } else if (playSpped == 0.6f) {
            playSpped = isUp ? 0.7f : 0.5f;
        } else if (playSpped == 0.7f) {
            playSpped = isUp ? 0.8f : 0.6f;
        } else if (playSpped == 0.8f) {
            playSpped = isUp ? 0.9f : 0.7f;
        } else if (playSpped == 0.9f) {
            playSpped = isUp ? 1.0f : 0.8f;
        } else if (playSpped == 1.0f) {
            playSpped = isUp ? 1.1f : 0.9f;
        } else if (playSpped == 1.1f) {
            playSpped = isUp ? 1.2f : 1.0f;
        } else if (playSpped == 1.2f) {
            playSpped = isUp ? 1.3f : 1.1f;
        } else if (playSpped == 1.3f) {
            playSpped = isUp ? 1.4f : 1.2f;
        } else if (playSpped == 1.4f) {
            playSpped = isUp ? 1.5f : 1.3f;
        } else if (playSpped == 1.5f) {
            playSpped = isUp ? 1.6f : 1.4f;
        } else if (playSpped == 1.6f) {
            playSpped = isUp ? 1.7f : 1.5f;
        } else if (playSpped == 1.7f) {
            playSpped = isUp ? 1.8f : 1.6f;
        } else if (playSpped == 1.8f) {
            playSpped = isUp ? 1.9f : 1.7f;
        } else if (playSpped == 1.9f) {
            playSpped = isUp ? 2.0f : 1.8f;
        } else if (playSpped == 2.0f) {
            playSpped = isUp ? 2.0f : 1.9f;
        }
        return playSpped;
    }

    // Toast显示一条信息
    public static void showToastMsg(Context context, String msg) {
        AppUtil.showToastMsg(context, msg, 0);
    }

    // Toast显示一条信息
    public static void showToastMsg(Context context, int msg) {
        AppUtil.showToastMsg(context, context.getResources().getString(msg), 2);
    }

    // Toast显示一条信息
    public static void showToastMsg(Context context, String msg, int time) {
        if (msg != null && !"".equals(msg))
            Toast.makeText(context, msg, time).show();
    }

    // 判断字符串为空
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0)
            return true;
        else
            return false;
    }

    // 取得屏幕宽度
    public static int getScreenWidth(Activity activity) {
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        return width;
    }

    // 取得屏幕高度
    public static int getScreenHeight(Activity activity) {
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int height = outMetrics.heightPixels;
        return height;
    }

    public static String getAssets(Context context, String fileName) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {

        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        //canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 产生随机数
     */
    public static int getRandomNum(int min,int max){
        Random random = new Random();
        int num = random.nextInt(max)%(max-min+1) + min;
        return num;
    }

    //dip和px之间的转换
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    /**
     * convert sp to its equivalent px
     *
     * 将sp转换为px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /** 从assets 文件夹中读取图片 */
    public static Drawable loadImageFromAsserts(final Context ctx) {
        String asString = ACache.getInstance(ctx).getAsString(SKIN);
        if (asString == null || asString.isEmpty()){
            asString = "0";
        }
        int type = Integer.parseInt(asString);
        int temp = -1;
        switch (type){
            case 0:
                temp = R.raw.bg_000;
                break;
            case 1:
                temp = R.raw.bg_001;
                break;
            case 2:
                temp = R.raw.bg_002;
                break;
            case 3:
                temp = R.raw.bg_003;
                break;
            case 4:
                temp = R.raw.bg_004;
                break;
            case 5:
                temp = R.raw.bg_005;
                break;
            case 6:
                temp = R.raw.bg_006;
                break;
            case 7:
                temp = R.raw.bg_007;
                break;
            case 8:
                temp = R.raw.bg_008;
                break;
            case 9:
                temp = R.raw.bg_009;
                break;
            case 10:
                temp = R.raw.bg_010;
                break;
            case 11:
                temp = R.raw.bg_011;
                break;
            case 12:
                temp = R.raw.bg_012;
                break;
            case 13:
                temp = R.raw.bg_013;
                break;
            case 14:
                temp = R.raw.bg_014;
                break;
            case 15:
                temp = R.raw.bg_015;
                break;
            case 16:
                temp = R.raw.bg_016;
                break;
            case 17:
                temp = R.raw.bg_017;
                break;
            default:
                temp = R.raw.bg_016;
                break;
        }
        try {
            InputStream is = ctx.getResources().openRawResource(temp);
            return Drawable.createFromStream(is, null);
        } catch (OutOfMemoryError e) {
            if (e != null) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            if (e != null) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
