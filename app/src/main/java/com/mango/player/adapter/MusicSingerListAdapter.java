package com.mango.player.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mango.player.R;
import com.mango.player.bean.Music;
import com.mango.player.holder.MusicNativeListHolder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by yzd on 2017/10/18 0018.
 */

public class MusicSingerListAdapter extends RecyclerView.Adapter<MusicNativeListHolder> implements View.OnClickListener {
    private LinkedHashMap<String, List<Music>> mSinger;
    private MusicNativeListHolder mHolder;
    private OnItemClickListener mOnItemClickListener = null;
    private List<String> list = new ArrayList<>();
    public MusicSingerListAdapter(LinkedHashMap<String, List<Music>> map,List<String> list) {
        this.mSinger = map;
        this.list = list;
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
        if (position >= list.size()){
            return;
        }
        String singer = list.get(position);
        List<Music> musics = mSinger.get(singer);
        if (musics == null)
            return;
        for (Music music : musics) {
            if (music.getThumbnail() != null) {
                holder.iv_thumbnail.setImageBitmap(music.getThumbnail());
                break;
            } else {
                holder.iv_thumbnail.setImageResource(R.drawable.music);
            }
        }

        holder.name.setText(singer);
        holder.singer.setText(musics.size() + " 首");
        holder.mItemView.setTag(position);
        holder.detail.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mSinger.size();
    }

    public void setData(LinkedHashMap<String, List<Music>> musics,List<String> list) {
        mSinger = musics;
        this.list = list;
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
