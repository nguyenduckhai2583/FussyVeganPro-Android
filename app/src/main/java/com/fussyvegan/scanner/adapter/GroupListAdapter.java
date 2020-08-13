package com.fussyvegan.scanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.model.favorite.GroupFavorite;

import java.util.ArrayList;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.myHolder> {

    private ArrayList<GroupFavorite> list;
    private Context context;
    OnAddFavoriteListener listener;

    public GroupListAdapter(ArrayList<GroupFavorite> list, Context context, OnAddFavoriteListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myHolder holder, final int position) {
        holder.tvNameList.setText(list.get(position).getName());
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.addFavorite(list.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myHolder extends RecyclerView.ViewHolder {
        TextView tvNameList;
        RelativeLayout rlItem;

        public myHolder(@NonNull View itemView) {
            super(itemView);
            tvNameList = itemView.findViewById(R.id.tvNameList);
            rlItem = itemView.findViewById(R.id.rlItem);
        }
    }

    public interface OnAddFavoriteListener {
        void addFavorite(int groupId);
    }
}
