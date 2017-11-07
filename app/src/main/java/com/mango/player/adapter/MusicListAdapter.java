package com.mango.player.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mango.player.R;
import com.mango.player.bean.MusicList;
import com.mango.player.holder.MusicListHolder;

import java.util.List;

/**
 * Created by yzd on 2017/11/7 0007.
 */

public class MusicListAdapter extends RecyclerView.Adapter<MusicListHolder> implements View.OnClickListener {
    private List<MusicList> list;
    private OnItemClickListener mOnItemClickListener = null;

    public MusicListAdapter(List<MusicList> list) {
        this.list = list;
    }

    @Override
    public MusicListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MusicListHolder holder = new MusicListHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_music_list, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MusicListHolder holder, int position) {
        MusicList musicList = list.get(position);
        holder.name.setText(musicList.getName());
        holder.num.setText(musicList.getMusics().size() + "");
        holder.mItemView.setOnClickListener(this);
        holder.mItemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setData(List<MusicList> data) {
        this.list = data;
        notifyDataSetChanged();
    }
}
