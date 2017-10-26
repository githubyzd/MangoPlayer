package com.mango.player.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mango.player.R;
import com.mango.player.base.BaseFragment;
import com.mango.player.fragment.MusicNativeFragment;
import com.mango.player.fragment.MusicOnlineFragment;
import com.mango.player.fragment.VideoNativeFragment;
import com.mango.player.fragment.VideoOnlineDebugFragment;
import com.mango.player.fragment.VideoOnlineFragment;
import com.mango.player.util.AppUtil;
import com.mango.player.util.ApplicationConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.mango.player.activity.App.mainActicity;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.main_container)
    FrameLayout mainContainer;
    private Button bt_login;

    private final int DEFAULT_FRATGMENT  = 3;

    private final int FRAGMENT_VIDEO_ONLINE = 100;
    private final int FRAGMENT_VIDEO_NATIVE = FRAGMENT_VIDEO_ONLINE + 1;
    private final int FRAGMENT_MUSIC_ONLINE = FRAGMENT_VIDEO_NATIVE + 1;
    private final int FRAGMENT_MUSIC_NATIVE = FRAGMENT_MUSIC_ONLINE + 1;
    private final int FRAGMENT_WELFARE = FRAGMENT_MUSIC_NATIVE + 1;
    private final int FRAGMENT_VIDEO_ONLINE_DEBUG = FRAGMENT_WELFARE + 1;
    private int fragmentType = 0;
    private Unbinder unbinder;
    private BaseFragment fragment;
    private MusicNativeFragment musicNativeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        setStatus();
        initView();
        initListener();
        initToobar();
        initDrawerLayout();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        //使用此种方式代替在布局中使用headerlayout引起的bug
        View view = navView.inflateHeaderView(R.layout.nav_header_main);
        bt_login = (Button) view.findViewById(R.id.bt_login);
        mainActicity = this;
    }

    private void initListener() {
        bt_login.setOnClickListener(this);
        fab.setOnClickListener(this);
        //菜单的设置
        navView.setNavigationItemSelectedListener(this);
    }

    //设置菜单打开关闭的监听
    private void initDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    //设置Toolbar
    private void initToobar() {
        onNavigationItemSelected(navView.getMenu().getItem(DEFAULT_FRATGMENT));
        setSupportActionBar(toolbar);
    }

    //设置状态栏透明
    private void setStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    //添加Toolbar右上方的菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.base_toolbar_menu, menu);
        return true;
    }

    //Toolbar右上方菜单item的点击回调
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_about:
            case R.id.menu_main_home_page:
            case R.id.menu_main_top_github:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //左侧菜单item点击时回调
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        toolbar.setTitle(item.getTitle());
        item.setCheckable(true);//设置选项可选
        navView.setCheckedItem(item.getItemId());
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.video_online:
                fragmentType = ApplicationConstant.VIDEO_DEBUG ? FRAGMENT_VIDEO_ONLINE_DEBUG : FRAGMENT_VIDEO_ONLINE;
                break;
            case R.id.video_native:
                fragmentType = FRAGMENT_VIDEO_NATIVE;
                break;
            case R.id.music_online:
                fragmentType = FRAGMENT_MUSIC_ONLINE;
                break;
            case R.id.music_native:
                fragmentType = FRAGMENT_MUSIC_NATIVE;
                break;
            case R.id.common_scan:
                AppUtil.startScanActivity(this, ScanActivity.class);
                return true;
            case R.id.common_welfare:
                fragmentType = FRAGMENT_VIDEO_ONLINE;
                break;
            case R.id.common_setting:
                return true;
        }
        switchFragment();
        return true;
    }

    private void switchFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fragment = null;
        switch (fragmentType) {
            case FRAGMENT_VIDEO_ONLINE:
                fragment = new VideoOnlineFragment();
                break;
            case FRAGMENT_VIDEO_ONLINE_DEBUG:
                fragment = new VideoOnlineDebugFragment();
                break;
            case FRAGMENT_VIDEO_NATIVE:
                fragment = new VideoNativeFragment();
                break;
            case FRAGMENT_MUSIC_ONLINE:
                fragment = new MusicOnlineFragment();
                break;
            case FRAGMENT_MUSIC_NATIVE:
                if (musicNativeFragment == null) {
                    musicNativeFragment = new MusicNativeFragment();
                }
                fragment = musicNativeFragment;
                break;
        }
        transaction.replace(R.id.main_container, fragment);
        transaction.commit();
    }

    //返回键的处理
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        if (fragmentType == FRAGMENT_MUSIC_NATIVE){
            MusicNativeFragment nativeFragment = (MusicNativeFragment) fragment;
            if (!nativeFragment.onBackPressed()){
                super.onBackPressed();
            }
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.fab:
                Snackbar.make(v, "Snack", Snackbar.LENGTH_LONG)
                        .setAction("clickSnack", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "Snack被点击了", Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();
                break;
        }
    }
}
