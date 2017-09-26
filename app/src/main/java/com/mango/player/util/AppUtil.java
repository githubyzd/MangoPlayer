package com.mango.player.util;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.mango.player.activity.ScanActivity;


/**
 * Created by yzd on 2017/9/26 0026.
 */

public class AppUtil {
    public static void showSnackbar(View view, String msg) {
        Snackbar.make(view, msg, android.support.design.widget.Snackbar.LENGTH_LONG)
                .show();
    }

    public static void startScanActivity(Context context,Class<ScanActivity> clazz) {
        Intent intent = new Intent(context,clazz);
        context.startActivity(intent);
    }
}
