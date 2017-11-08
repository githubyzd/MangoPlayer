package com.mango.player.pager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mango.player.R;
import com.mango.player.activity.App;
import com.mango.player.adapter.MusicSongListAdapter;
import com.mango.player.base.BasePager;
import com.mango.player.bean.Music;
import com.mango.player.bean.MusicServiceBean;
import com.mango.player.bean.PlayMode;
import com.mango.player.util.AppUtil;
import com.mango.player.util.FileManager;
import com.mango.player.util.LogUtil;
import com.mango.player.util.PopupHelper;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

import static com.mango.player.R.id.iv_random;
import static com.mango.player.R.id.tv_random;
import static com.mango.player.bean.PlayMode.MODE_RADOM;
import static com.mango.player.bean.PlayMode.PLAY_INDEX;
import static com.mango.player.bean.PlayMode.PLAY_NEXT;
import static com.mango.player.bean.PlayMode.SET_INDEX;


/**
 * Created by yzd on 2017/10/18 0018.
 */

public class SongPager extends BasePager implements View.OnClickListener, MusicSongListAdapter.OnItemClickListener {

    private View view;
    private ImageView ivRandom;
    private TextView tvRandom;
    private ImageView edit;
    private ImageView rank;
    private RecyclerView listRecyclerview;
    private ArrayList<Music> musics = new ArrayList<>();
    private MusicSongListAdapter adapter;
    private int clickPosition;
    private View contentView;
    private PopupHelper popupHelper;
    private TextView musicName;
    private ImageView editPopu;
    private LinearLayout addList;
    private LinearLayout addTo;
    private LinearLayout bell;
    private LinearLayout photo;
    private LinearLayout share;
    private LinearLayout delete;
    private int popuIndex = -1;

    public SongPager(Activity context) {
        super(context);
    }

    @Override
    public void initView() {
        view = View.inflate(mContext, R.layout.song_pager, null);
        ivRandom = (ImageView) view.findViewById(iv_random);
        tvRandom = (TextView) view.findViewById(tv_random);
        edit = (ImageView) view.findViewById(R.id.edit);
        rank = (ImageView) view.findViewById(R.id.rank);
        listRecyclerview = (RecyclerView) view.findViewById(R.id.list_recyclerview);
        listRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));

        adapter = new MusicSongListAdapter(musics);
        listRecyclerview.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        fl_basepager_container.removeAllViews();
        fl_basepager_container.addView(view);

        initListener();
        initData();
    }

    @Override
    protected void initListener() {
        ivRandom.setOnClickListener(this);
        tvRandom.setOnClickListener(this);
        edit.setOnClickListener(this);
        rank.setOnClickListener(this);
    }

    void randomPlay() {
        PlayMode playMode = MODE_RADOM;
        EventBus.getDefault().post(playMode);

        EventBus.getDefault().post(App.musicList);

        MusicServiceBean bean = new MusicServiceBean();
        playMode = SET_INDEX;
        bean.setIndex(0);
        bean.setPlayMode(playMode);
        EventBus.getDefault().post(bean);

        playMode = PLAY_NEXT;
        bean.setPlayMode(playMode);
        EventBus.getDefault().post(bean);
    }

    @Override
    protected void processClick(View view) {
        switch (view.getId()) {
            case iv_random:
            case tv_random:
                randomPlay();
                break;
            case R.id.edit:
                break;
            case R.id.rank:
                break;
            case R.id.edit_popu:
                break;
            case R.id.add_list:
                break;
            case R.id.add_to:
                break;
            case R.id.bell:
                setBell();
                break;
            case R.id.photo:
                break;
            case R.id.share:
                share();
                break;
            case R.id.delete:
                delete();
                break;
            default:
                break;
        }
    }

    private void delete() {
        popupHelper.dismiss();
        final Music music = musics.get(popuIndex);
        new AlertDialog.Builder(mContext).setTitle("删除")
                .setMessage("您确认要删除 " + music.getName() + " 吗？")
                .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteFile(music.getPath());
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    private void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) { // 判断文件是否存在
            boolean delete = file.delete();
            if (delete) {
                AppUtil.showSnackbar(view, "删除成功");
            } else {
                AppUtil.showSnackbar(view, "删除失败");
            }
        } else {
            AppUtil.showSnackbar(view, "文件不存在！");
        }
    }

    private void setBell() {
        popupHelper.dismiss();
        File sdfile = new File(musics.get(popuIndex).getPath());
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DATA, sdfile.getAbsolutePath());
        values.put(MediaStore.MediaColumns.TITLE, sdfile.getName());
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");
        values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
        values.put(MediaStore.Audio.Media.IS_ALARM, false);
        values.put(MediaStore.Audio.Media.IS_MUSIC, false);

        Uri uri = MediaStore.Audio.Media.getContentUriForPath(sdfile.getAbsolutePath());
        Uri newUri = mContext.getContentResolver().insert(uri, values);
        RingtoneManager.setActualDefaultRingtoneUri(mContext, RingtoneManager.TYPE_RINGTONE, newUri);
        AppUtil.showSnackbar(view, "设置来电铃声成功");
    }

    private void share() {
        popupHelper.dismiss();
        //由文件得到uri
        Uri musicPath = Uri.fromFile(new File(musics.get(popuIndex).getPath()));
        LogUtil.logByD("share", "uri:" + musicPath);  //输出：file:///storage/emulated/0/test.jpg

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, musicPath);
        shareIntent.setType("audio/*");
        mContext.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    @Override
    public void initData() {
        super.initData();
        if (App.musicList != null) {
            musics = App.musicList;
        } else {
            musics = FileManager.getInstance(mContext).getMusics(mContext);
        }
        tvRandom.setText("全部随机(" + musics.size() + ")");
        adapter.setData(musics);
        LogUtil.logByD("共有" + musics.size() + "首歌曲");
    }

    @Override
    public void onClick(View v) {
        processClick(v);
    }

    @Override
    public void onItemClick(View view, int position) {
        clickPosition = position;
        switch (view.getId()) {
            case R.id.music_native_lib_item:
                playMusic();
                break;
            case R.id.detail:
                LogUtil.logByD("点击音乐条目" + musics.get(position));
                showDetail(position);
                break;
        }
    }

    private void showDetail(int position) {
        if (popupHelper != null && popupHelper.isShowing()) {
            return;
        }
        popuIndex = position;
        Music music = musics.get(position);
        if (contentView == null) {
            contentView = View.inflate(mContext, R.layout.music_detail_popu, null);
            musicName = (TextView) contentView.findViewById(R.id.name);
            editPopu = (ImageView) contentView.findViewById(R.id.edit_popu);
            addList = (LinearLayout) contentView.findViewById(R.id.add_list);
            addTo = (LinearLayout) contentView.findViewById(R.id.add_to);
            bell = (LinearLayout) contentView.findViewById(R.id.bell);
            photo = (LinearLayout) contentView.findViewById(R.id.photo);
            share = (LinearLayout) contentView.findViewById(R.id.share);
            delete = (LinearLayout) contentView.findViewById(R.id.delete);

            editPopu.setOnClickListener(this);
            addList.setOnClickListener(this);
            addTo.setOnClickListener(this);
            bell.setOnClickListener(this);
            photo.setOnClickListener(this);
            share.setOnClickListener(this);
            delete.setOnClickListener(this);
        }
        musicName.setText(music.getName());
        popupHelper = new PopupHelper.Builder(mContext)
                .contentView(contentView)
                .width(ViewGroup.LayoutParams.MATCH_PARENT)
                .height(AppUtil.getScreenHeight(mContext) / 2 - AppUtil.dp2px(mContext, 80))
                .anchorView(view)
                .parentView(view)
                .gravity(Gravity.BOTTOM)
                .outSideTouchable(true)
                .animationStyle(R.style.anim_bottom)
                .build()
                .showAtLocation();
    }

    private void playMusic() {
        MusicServiceBean bean = new MusicServiceBean();
        bean.setIndex(clickPosition);
        bean.setPlayMode(PLAY_INDEX);
        EventBus.getDefault().post(bean);
    }
}
