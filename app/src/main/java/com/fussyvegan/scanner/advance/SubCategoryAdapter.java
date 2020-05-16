package com.fussyvegan.scanner.advance;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.model.Category;
import com.fussyvegan.scanner.model.SubCategory;
import com.squareup.picasso.Picasso;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

public class SubCategoryAdapter extends ExpandableRecyclerViewAdapter<CategoryViewHolder, SubCategoryViewHolder> {
    private final List<? extends ExpandableGroup> grupo;
    IClickSubCategoryListener mIClickSubCategoryListener;

    public SubCategoryAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
        grupo = groups;
    }

    public void setOnClick(IClickSubCategoryListener iClickSubCategoryListener) {
        mIClickSubCategoryListener = iClickSubCategoryListener;

    }

    @Override
    public CategoryViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public SubCategoryViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subcategory_item, parent, false);
        return new SubCategoryViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(SubCategoryViewHolder holder, int flatPosition, ExpandableGroup group,
                                      int childIndex) {
        final Category category = ((SubCategory) group).getItems().get(childIndex);
        holder.onBind(category, mIClickSubCategoryListener);


    }

    @Override
    public void onBindGroupViewHolder(CategoryViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {
        holder.setTitle(group);
    }

    public void expandAllGroups() {

        for (int i = 0; i < grupo.size(); i++) {
            if (!isGroupExpanded(grupo.get(i))) {
                onGroupClick(expandableList.getFlattenedGroupIndex(i));
            }
        }
    }

}


class CategoryViewHolder extends GroupViewHolder {
    TextView tvName;

    CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tvCategory);
    }

    public void setTitle(ExpandableGroup group) {
        tvName.setText(group.getTitle());
    }

}


class SubCategoryViewHolder extends ChildViewHolder {
    TextView tvCategory;
    ImageView imgIcon;
    View mItemView;

    SubCategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
        tvCategory = itemView.findViewById(R.id.tvCategory);
        imgIcon = itemView.findViewById(R.id.imgIcon);
    }

    public void onBind(final Category category, final IClickSubCategoryListener iClickSubCategoryListener) {
        tvCategory.setText(category.getName());
        Picasso.get()
                .load(getDrawableIdFromFileName(category.getIdResource()))
                .placeholder(R.drawable.ic_blank)
                .into(imgIcon);

        tvCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickSubCategoryListener.onClickChange(category.getName());
            }
        });


    }

    public int getDrawableIdFromFileName(String nameOfDrawable) {
        return mItemView.getContext().getResources().getIdentifier(nameOfDrawable, "drawable", mItemView.getContext().getPackageName());
    }
}


interface IClickSubCategoryListener {
    void onClickChange(String position);
}
