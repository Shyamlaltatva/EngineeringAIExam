package com.engineerai.android.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.engineerai.android.R;

import java.util.List;


public class GridPostAdapter extends RecyclerView.Adapter {

    private Activity mCtx;
    private List<String> list;

    public   GridPostAdapter(Activity mctx, List<String> list)
    {
        this.mCtx = mctx;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder itemViewHolder;
        View itemView = LayoutInflater.from(mCtx)
                .inflate(R.layout.row_item_post, parent, false);

        itemViewHolder = new ItemViewHolder(itemView);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final String imageUrl= list.get(position);
        try {
            Glide.with(mCtx).load(imageUrl).placeholder(R.drawable.ic_placeholder_24dp).into(((ItemViewHolder)holder).ivPostImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPostImage;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPostImage = itemView.findViewById(R.id.postImage);

        }
    }
}
