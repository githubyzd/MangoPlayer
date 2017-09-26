package com.mango.player.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.util.AppUtil;
import com.mango.player.util.ApplicationConstant;
import com.mango.player.util.ImageUtil;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScanActivity extends AppCompatActivity {

    @BindView(R.id.fl_my_container)
    FrameLayout flMyContainer;
    @BindView(R.id.btn_album)
    TextView btnAlbum;
    private CaptureFragment captureFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);
        btnAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPic();
            }
        });
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            onSuccess(result);
        }

        @Override
        public void onAnalyzeFailed() {
            AppUtil.showSnackbar(findViewById(R.id.btn_album), "扫描失败");
        }
    };

    @OnClick(R.id.iv_back)
    void onBack() {
        finish();
    }

    private void onSuccess(String result) {
        AppUtil.showSnackbar(findViewById(R.id.btn_album), result);

    }

    private void selectPic() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, ApplicationConstant.ACTIVITY_REQUEST_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ApplicationConstant.ACTIVITY_REQUEST_IMAGE) {
            if (data != null) {
                try {
                    Uri uri = data.getData();
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(ScanActivity.this, uri), analyzeCallback);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
