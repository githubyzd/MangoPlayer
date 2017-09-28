package com.mango.player.listener;

import android.content.Context;
import android.view.OrientationEventListener;

/**
 * Created by yzd on 2017/9/28 0028.
 */

public class OrientationSensorListener extends OrientationEventListener {
    public OrientationSensorListener(Context context) {
        super(context);
    }

    @Override
    public void onOrientationChanged(int orientation) {

    }
}
