package com.fussyvegan.scanner.adapter;

import android.content.Context;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fussyvegan.scanner.model.ChainFastFood;
import com.fussyvegan.scanner.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChainFoodAdapter extends RecyclerView.Adapter {
    private List<ChainFastFood> mChains = new ArrayList<>();
    private Context context;
    private int selectposition = 0;
    IChainListener iChainListener;

    public ChainFoodAdapter(Context context, IChainListener iChainListener) {
        this.context = context;
        this.iChainListener = iChainListener;
        Log.e("recent", " recent");
    }

    public void updateData(List<ChainFastFood> chains){
        mChains.clear();
        mChains.addAll(chains);
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public ChainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_chain_categories_fastfood, viewGroup, false);
        return new ChainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        final ChainViewHolder holder = (ChainViewHolder) viewHolder;
        holder.tvNameChain.setText(mChains.get(position).getName());
        Picasso.get()
                .load(getDrawableIdFromFileName(mChains.get(position).getUrl()))
                .placeholder(R.drawable.ic_blank)
                .into(holder.imgChain);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iChainListener.onClickChange(position);
            }
        });

    }
    public  int getDrawableIdFromFileName( String nameOfDrawable) {
        return context.getResources().getIdentifier(nameOfDrawable, "drawable", context.getPackageName());
    }

    @Override
    public int getItemCount() {
        return mChains.size();
    }

    public class ChainViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameChain;
        ImageView imgChain;

        ChainViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameChain = itemView.findViewById(R.id.tvNameChain);
            imgChain = itemView.findViewById(R.id.imgChain);
        }

    }


    public static class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private int mItemOffset;

        public ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
    }

    public interface IChainListener {
        void onClickChange(int position);
    }
}
