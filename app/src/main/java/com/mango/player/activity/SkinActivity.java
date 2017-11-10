package com.mango.player.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.mango.player.R;
import com.mango.player.adapter.SkinAdapter;
import com.mango.player.util.ACache;
import com.mango.player.util.AppUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mango.player.util.ApplicationConstant.SKIN;

public class SkinActivity extends AppCompatActivity implements SkinAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.container)
    RelativeLayout container;
    private SkinAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
        ButterKnife.bind(this);
        initToolBar();
        initView();
    }

    private void initView() {
        setBg();
        GridLayoutManager manager = new GridLayoutManager(this,3);
        adapter = new SkinAdapter();
        recyclerview.setLayoutManager(manager);
        recyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    private void setBg() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            container.setBackground(AppUtil.loadImageFromAsserts(this));
        }
    }

    private void initToolBar() {
        toolbar.setBackgroundColor(Color.GRAY);
        toolbar.setTitle("皮肤");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        ACache.getInstance(this).put(SKIN,position+"");
        setBg();
        adapter.notifyData();
        EventBus.getDefault().post("skin");
    }
}
