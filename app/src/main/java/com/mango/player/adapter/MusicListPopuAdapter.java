package com.mango.player.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mango.player.R;
import com.mango.player.bean.Music;
import com.mango.player.holder.MusicNativeListPopuHolder;

import java.util.List;

/**
 * Created by yzd on 2017/9/27 0027.
 */

public class MusicListPopuAdapter extends RecyclerView.Adapter<MusicNativeListPopuHolder> implements View.OnClickListener {
    private List<Music> mMusic = null;
    private OnItemClickListener mOnItemClickListener = null;
    private View lastClickView;
    private int lastIndex = -1;

    public MusicListPopuAdapter(List<Music> musicList) {
        this.mMusic = musicList;
    }

    @Override
    public MusicNativeListPopuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MusicNativeListPopuHolder mHolder = new MusicNativeListPopuHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_music_list_popu, parent, false));
        mHolder.mItemView.setOnClickListener(this);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(MusicNativeListPopuHolder holder, int position) {
        Music music = mMusic.get(position);
        holder.name.setText(music.getName());
        holder.itemView.setTag(position);
        holder.mItemView.setSelected(lastIndex == position);
        holder.singer.setText(" - " + music.getArtist());
        holder.delete.setTag(position);
        setViewOnclickLisenner(holder);
    }

    private void setViewOnclickLisenner(MusicNativeListPopuHolder holder) {
        holder.delete.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mMusic.size();
    }

    public void setData(List<Music> data) {
        this.mMusic = data;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
        if (v.getId() == R.id.item_music_list_popu) {
            lastIndex = (int) v.getTag();
            if (lastClickView != null) {
                lastClickView.setSelected(false);
            }
            v.setSelected(true);
            lastClickView = v;
        }

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setIndex(int index) {
        lastIndex = index;
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
