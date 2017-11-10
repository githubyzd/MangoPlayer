package com.mango.player.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mango.player.R;
import com.mango.player.activity.App;
import com.mango.player.holder.SkinHolder;
import com.mango.player.util.ACache;
import com.mango.player.util.ApplicationConstant;
import com.mango.player.util.BitmapDecodeUtil;

import java.io.InputStream;

import static com.mango.player.util.ApplicationConstant.SKIN;

/**
 * Created by yzd on 2017/11/9 0009.
 */

public class SkinAdapter extends RecyclerView.Adapter<SkinHolder> implements View.OnClickListener {
    private OnItemClickListener mOnItemClickListener = null;
    private int index;

    public SkinAdapter() {
        String asString = ACache.getInstance(App.mContext).getAsString(SKIN);
        if (asString == null || asString.isEmpty()){
            asString = "0";
        }
        index = Integer.parseInt(asString);
    }

    @Override
    public SkinHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SkinHolder holder = new SkinHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_skin, parent, false));
        holder.mItemView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(SkinHolder holder, int position) {
        setImage(holder, position);
        holder.mItemView.setTag(position);
        if (position == index){
            holder.check.setVisibility(View.VISIBLE);
        }else {
            holder.check.setVisibility(View.GONE);
        }
    }

    private void setImage(SkinHolder holder, int position) {
        int temp = ApplicationConstant.skin[position];
        InputStream is = App.mContext.getResources().openRawResource(temp);
        Bitmap bitmap = BitmapDecodeUtil.decodeBitmap(App.mContext,is);
        holder.iv_thumbnail.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return ApplicationConstant.skin.length;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void notifyData(){
        String asString = ACache.getInstance(App.mContext).getAsString(SKIN);
        if (asString == null || asString.isEmpty()){
            asString = "0";
        }
        index = Integer.parseInt(asString);
        notifyDataSetChanged();
    }
}
