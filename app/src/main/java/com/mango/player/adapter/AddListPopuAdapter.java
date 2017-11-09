package com.mango.player.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mango.player.R;
import com.mango.player.holder.AddListPopuHolder;

import java.util.List;

/**
 * Created by yzd on 2017/11/9 0009.
 */

public class AddListPopuAdapter extends RecyclerView.Adapter<AddListPopuHolder> implements View.OnClickListener {
    private List<String> mList;
    private OnItemClickListener mOnItemClickListener = null;
    public AddListPopuAdapter(List<String> listData) {
        mList = listData;
    }

    @Override
    public AddListPopuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AddListPopuHolder holder = new AddListPopuHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_add_list_popu, parent, false));
        holder.mItemView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(AddListPopuHolder holder, int position) {
        holder.name.setText(mList.get(position));
        holder.mItemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v,(int) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
