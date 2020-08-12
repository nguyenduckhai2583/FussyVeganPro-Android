package com.fussyvegan.scanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.model.favorite.GroupFavorite;

import java.util.ArrayList;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.myHolder> {

    private ArrayList<GroupFavorite> list;
    private Context context;
    OnRemoveGroupListener listener;

    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public MyListAdapter(ArrayList<GroupFavorite> list, Context context, OnRemoveGroupListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_favorite, parent, false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, final int position) {
        holder.tvNameList.setText(list.get(position).getName());
        holder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Switch screen here
                Toast.makeText(context, list.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.removeGroup(list.get(position).getId());
            }
        });

        viewBinderHelper.setOpenOnlyOne(true);
        viewBinderHelper.bind(holder.swipeLayout, list.get(position).getName());
        viewBinderHelper.closeLayout(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myHolder extends RecyclerView.ViewHolder {
        TextView tvNameList, tvDelete;
        RelativeLayout rlItem;
        SwipeRevealLayout swipeLayout;
        public myHolder(@NonNull View itemView) {
            super(itemView);
            tvNameList = itemView.findViewById(R.id.tvNameList);
            rlItem = itemView.findViewById(R.id.rlItemList);
            tvDelete = itemView.findViewById(R.id.tvDelete);
            swipeLayout = itemView.findViewById(R.id.swipeLayout);
        }
    }

    public interface OnRemoveGroupListener {
        void removeGroup(int groupId);
    }
}
