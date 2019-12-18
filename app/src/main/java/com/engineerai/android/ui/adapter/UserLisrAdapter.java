package com.engineerai.android.ui.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.engineerai.android.R;
import com.engineerai.android.model.responsemodel.APIResponse;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserLisrAdapter extends RecyclerView.Adapter {

    private ArrayList<APIResponse.Data.Users> userlist;
    private Activity mAct;
    private GridPostAdapter gridPostAdapter;

    public UserLisrAdapter(Activity activity, ArrayList<APIResponse.Data.Users> list) {
        this.mAct = activity;
        this.userlist = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder itemViewHolder;
        View itemView = LayoutInflater.from(mAct)
                .inflate(R.layout.row_item_user, parent, false);

        itemViewHolder = new ItemViewHolder(itemView);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final APIResponse.Data.Users usersModel = userlist.get(position);
        ((ItemViewHolder) holder).tvUserName.setText(usersModel.getName());

        Glide.with(mAct).load(usersModel.getImage()).into(((ItemViewHolder) holder).ivUserPhoto);

        setPostDataAdapter(usersModel.getItems(), ((ItemViewHolder) holder).rvPost);

    }

    private void setPostDataAdapter(final List<String> items, RecyclerView rvPost) {

        if (items != null && items.size() > 0) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mAct, 2);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (items.size() % 2 == 0)
                        return 1;
                    else if (position == 0)
                        return 2;
                    else
                        return 1;
                }
            });
            gridPostAdapter=new GridPostAdapter(mAct,items);
            rvPost.setLayoutManager(gridLayoutManager);
            rvPost.setAdapter(gridPostAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public void setData(ArrayList<APIResponse.Data.Users> newList) {
        //Assign the list to old list
        this.userlist = newList;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView rvPost;
        private TextView tvUserName;
        private CircleImageView ivUserPhoto;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            rvPost = itemView.findViewById(R.id.rvPost);
            tvUserName = itemView.findViewById(R.id.tvUserName);

            ivUserPhoto = itemView.findViewById(R.id.ivUserPhoto);

        }
    }
}
