package com.mango.player.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.mango.player.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutUsActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String html = "<font color='red'> <big> <i>芒果播放器是一款轻量级的音视频播放软件，界面清新脱俗，操作流畅。 本应用纯属个人爱好，不做任何商业用途。</i> </big> <font>";
        html += "<font color='@" + android.R.color.white + "'> <tt> <b> <big> <u> 样式三 </u> </big> </b> </tt> </font> ";
        html += "</b>";
        html += "<big> <a href='https://github.com/githubyzd'>Github地址:https://github.com/githubyzd </a> </big>";

        CharSequence charSequence = Html.fromHtml(html);
        tv.setText(charSequence);
        //该语句在设置后必加，不然没有任何效果
        tv.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @OnClick(R.id.back)
    void back(){
        finish();
    }
}
