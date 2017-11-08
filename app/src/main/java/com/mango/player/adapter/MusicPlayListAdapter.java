package com.mango.player.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mango.player.R;
import com.mango.player.activity.App;
import com.mango.player.bean.MusicList;
import com.mango.player.holder.MusicNativeListHolder;
import com.mango.player.util.AppUtil;

import java.util.List;

/**
 * Created by yzd on 2017/10/18 0018.
 */

public class MusicPlayListAdapter extends RecyclerView.Adapter<MusicNativeListHolder> implements View.OnClickListener {
    private List<MusicList> mMusic;
    private MusicNativeListHolder mHolder;
    private OnItemClickListener mOnItemClickListener = null;

    public MusicPlayListAdapter(List<MusicList> list) {
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
        MusicList music = mMusic.get(position);
        holder.name.setText(music.getName());
        if (music.getMusics() == null) {
            holder.singer.setText("0 首");
        } else {
            holder.singer.setText(music.getMusics().size() + " 首");
        }
        int padding = AppUtil.dp2px(App.mContext, 14f);
        holder.iv_thumbnail.setPadding(padding, padding, padding, padding);
        holder.iv_thumbnail.setImageResource(R.drawable.music_list);
        holder.mItemView.setTag(position);
        holder.detail.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mMusic.size();
    }

    public void setData(List<MusicList> musics) {
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
