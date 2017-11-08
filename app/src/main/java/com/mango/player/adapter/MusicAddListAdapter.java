package com.mango.player.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.mango.player.R;
import com.mango.player.bean.Music;
import com.mango.player.holder.MusicAddListHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzd on 2017/10/18 0018.
 */

public class MusicAddListAdapter extends RecyclerView.Adapter<MusicAddListHolder> implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private List<Music> mMusic;
    private MusicAddListHolder mHolder;
    private List<Music> checkList = new ArrayList<>();
    private OnCheckListener mListener;

    public MusicAddListAdapter(List<Music> list) {
        this.mMusic = list;
    }

    @Override
    public MusicAddListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mHolder = new MusicAddListHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_music_add_list, parent, false));
        mHolder.mItemView.setOnClickListener(this);
        return mHolder;
    }

    @Override
    public void onBindViewHolder(MusicAddListHolder holder, int position) {
        Music music = mMusic.get(position);
        if (music.getThumbnail() != null) {
            holder.iv_thumbnail.setImageBitmap(music.getThumbnail());
        } else {
            holder.iv_thumbnail.setImageResource(R.drawable.music);
        }
        holder.name.setText(music.getName());
        holder.singer.setText(music.getArtist());
        holder.mItemView.setTag(position);
        holder.mItemView.setOnClickListener(this);
        holder.check.setChecked(checkList.contains(music));
        holder.check.setTag(position);
        holder.check.setOnCheckedChangeListener(this);
    }

    @Override
    public int getItemCount() {
        return mMusic.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        CheckBox checkBox = (CheckBox) v.findViewById(R.id.check);
        checkBox.setChecked(!checkBox.isChecked());
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int position = (int) buttonView.getTag();
        Music music = mMusic.get(position);
        if (isChecked) {
            checkList.add(music);
        } else {
            checkList.remove(music);
        }
        if (mListener != null){
            mListener.onCheckChanged(checkList.size());
        }
    }

    public List<Music> getChecked() {
        return checkList;
    }

    public void checkALll(boolean isCheck) {
        checkList.clear();
        if (isCheck) {
            checkList.addAll(mMusic);
        }
        notifyDataSetChanged();
    }
    public void setOnCheckListener(OnCheckListener listener){
        mListener = listener;
    }
    public interface OnCheckListener{
        void onCheckChanged(int size);
    }
}
