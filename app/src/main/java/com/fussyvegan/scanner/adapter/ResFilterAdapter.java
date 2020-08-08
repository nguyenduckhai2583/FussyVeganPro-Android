package com.fussyvegan.scanner.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.model.CuisineType;

import java.util.ArrayList;

public class ResFilterAdapter extends RecyclerView.Adapter<ResFilterAdapter.myHolder> {

    private Context context;
    private ArrayList<CuisineType> list;

    public ResFilterAdapter(Context context, ArrayList<CuisineType> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cuisine_type, viewGroup, false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myHolder myHolder, int i) {
        final CuisineType type = list.get(i);
        myHolder.tvTypeCusine.setText(type.getType());
        myHolder.cbkCuisine.setChecked(type.isChecked());
        myHolder.cbkCuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!type.isChecked()) {
                    type.setChecked(true);
                } else {
                    type.setChecked(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myHolder extends RecyclerView.ViewHolder {
        TextView tvTypeCusine;
        CheckBox cbkCuisine;
        public myHolder(@NonNull View itemView) {
            super(itemView);
            tvTypeCusine = itemView.findViewById(R.id.tvTypeCusine);
            cbkCuisine = itemView.findViewById(R.id.cbkCuisine);
        }
    }
}
