package com.mango.player.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mango.player.R;
import com.mango.player.bean.Music;
import com.mango.player.holder.MusicNativeListHolder;

import java.util.List;

/**
 * Created by yzd on 2017/10/18 0018.
 */

public class MusicSongListAdapter extends RecyclerView.Adapter<MusicNativeListHolder> implements View.OnClickListener {
    private List<Music> mMusic;
    private MusicNativeListHolder mHolder;
    private OnItemClickListener mOnItemClickListener = null;

    public MusicSongListAdapter(List<Music> list) {
        this.mMusic = list;
    }

    @Override
    public MusicNativeListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mHolder = new MusicNativeListHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_music_native_list, parent, false));
        mHolder.mItemView.setOnClickListener(this);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(MusicNativeListHolder holder, int position) {
        Music music = mMusic.get(position);
        if (music.getThumbnail() != null) {
            holder.iv_thumbnail.setImageBitmap(music.getThumbnail());
        }else {
            holder.iv_thumbnail.setImageResource(R.drawable.music);
        }
        holder.name.setText(music.getName());
        holder.singer.setText(music.getArtist());
        holder.mItemView.setTag(position);
        holder.detail.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mMusic.size();
    }

    public void setData(List<Music> musics) {
        mMusic = musics;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
