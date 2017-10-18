package com.mango.player.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mango.player.R;
import com.mango.player.bean.Music;
import com.mango.player.holder.MusicNativeListHolder;
import com.mango.player.util.LogUtil;

import java.util.List;

/**
 * Created by yzd on 2017/10/18 0018.
 */

public class MusicSongListAdapter extends RecyclerView.Adapter<MusicNativeListHolder> implements View.OnClickListener {
    private List<Music> mMusic;
    private MusicNativeListHolder mHolder;

    public MusicSongListAdapter(List<Music> list) {
        this.mMusic = list;
    }

    @Override
    public MusicNativeListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mHolder = new MusicNativeListHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_music_native_list, parent, false));
        return mHolder;
    }

    @Override
    public void onBindViewHolder(MusicNativeListHolder holder, int position) {
        Music music = mMusic.get(position);
        if (music.getThumbnail() != null) {
            holder.iv_thumbnail.setImageBitmap(music.getThumbnail());
        }
        holder.name.setText(music.getName());
        holder.singer.setText(music.getArtist());
    }

    @Override
    public int getItemCount() {
        LogUtil.logByD("test");
        return mMusic.size();
    }

    public void setData(List<Music> musics) {
        mMusic = musics;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }
}
