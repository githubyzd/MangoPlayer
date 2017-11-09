package com.mango.player.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mango.player.R;
import com.mango.player.activity.App;
import com.mango.player.bean.Music;
import com.mango.player.bean.MusicList;
import com.mango.player.holder.MusicListHolder;
import com.mango.player.util.AppUtil;

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
        if (position == list.size()){
            holder.add.setVisibility(View.VISIBLE);
            holder.normalContainer.setVisibility(View.GONE);
            holder.add.setOnClickListener(this);
            holder.add.setTag(position);
            return;
        }else {
            holder.add.setVisibility(View.GONE);
            holder.normalContainer.setVisibility(View.VISIBLE);
        }
        MusicList musicList = list.get(position);
        holder.name.setText(musicList.getName());
        if (musicList.getMusics() == null) {
            holder.num.setText("0");
        } else {
            holder.num.setText(musicList.getMusics().size() + "");
        }
        holder.mItemView.setOnClickListener(this);
        holder.mItemView.setTag(position);
        List<String> musics = musicList.getMusics();
        if (musics == null || musics.isEmpty()) {

        } else {
            int index = AppUtil.getRandomNum(0, musics.size());
            for (Music music : App.musicList) {
                if (music.getPath().equals(musics.get(index))) {
                    Bitmap bitmap = music.getThumbnail();
                    if (bitmap == null) {
                        int padding = AppUtil.dp2px(holder.mItemView.getContext(),26);
                        holder.thumbnail.setPadding(padding,padding,padding,padding);
                        holder.thumbnail.setImageResource(R.drawable.music_list);
                    } else {
                        holder.thumbnail.setImageBitmap(bitmap);
                        holder.thumbnail.setPadding(0,0,0,0);
                    }
                    break;
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
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
