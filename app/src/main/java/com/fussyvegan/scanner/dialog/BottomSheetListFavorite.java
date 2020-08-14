package com.fussyvegan.scanner.dialog;

//import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fussyvegan.scanner.APIInterface;
import com.fussyvegan.scanner.APILoginClient;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.adapter.GroupListAdapter;
import com.fussyvegan.scanner.model.favorite.CreateListResponse;
import com.fussyvegan.scanner.model.favorite.FavoriteType;
import com.fussyvegan.scanner.model.favorite.GroupFavorite;
import com.fussyvegan.scanner.model.favorite.ListFavoriteResponse;
import com.fussyvegan.scanner.utils.SharedPrefs;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fussyvegan.scanner.utils.Constant.ACCESS_TOKEN;

public class BottomSheetListFavorite extends BottomSheetDialogFragment implements DialogCreateListFavorite.OnCreateListFavoriteListener, GroupListAdapter.OnAddFavoriteListener {

    private static final String FAVORITE = "favorite";

    RecyclerView recyclerListFavorite;
    GroupListAdapter adapterFavorite;
    ArrayList<GroupFavorite> listFavorite;
    DialogCreateListFavorite dialogCreateListFavorite;
    LinearLayout lnAddNewList;
    FavoriteType favoriteType;
    DialogLoadingFragment dialog;

    public BottomSheetListFavorite() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.bottom_list_favorite, container, false);
        dialog = new DialogLoadingFragment();
        dialog.setCancelable(false);
        dialog.show(getChildFragmentManager(), "loading");

        addContent(view);
        addEvent();
        getListGroupFavorite();

        return view;
    }

    private void addEvent() {
        lnAddNewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCreateListFavorite.show(getChildFragmentManager(), "");
            }
        });
    }

    private void addContent(View view) {
        dialogCreateListFavorite = new DialogCreateListFavorite();
        dialogCreateListFavorite.listener = this;
        lnAddNewList = view.findViewById(R.id.lnAddNewList);
        listFavorite = new ArrayList<>();
        recyclerListFavorite = view.findViewById(R.id.recyclerFavorite);
        adapterFavorite = new GroupListAdapter(listFavorite, getContext(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerListFavorite.setLayoutManager(layoutManager);
        recyclerListFavorite.setAdapter(adapterFavorite);
    }

    private void getListGroupFavorite() {
        listFavorite.clear();
        String token = SharedPrefs.getInstance().get(ACCESS_TOKEN, String.class);
        APIInterface apiInterface = APILoginClient.getClient().create(APIInterface.class);

        Call<ListFavoriteResponse> call = apiInterface.getListGroup(token);
        call.enqueue(new Callback<ListFavoriteResponse>() {
            @Override
            public void onResponse(Call<ListFavoriteResponse> call, Response<ListFavoriteResponse> response) {
                dialog.dismiss();
                if (response.code() == 200) {
                    listFavorite.addAll(response.body().getList());
                    adapterFavorite.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Load Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListFavoriteResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Load Error", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme);
        if (getArguments() != null) {
            favoriteType = getArguments().getParcelable(FAVORITE);
        }
    }

    @Override
    public void onCreateListFavorite() {
        getListGroupFavorite();
    }

    @Override
    public void addFavorite(int groupId) {
        favoriteType.setGroup_id(groupId);
        addFavoriteToGroup(favoriteType);
    }

    private void addFavoriteToGroup(FavoriteType favoriteType) {
        dialog.show(getChildFragmentManager(), "loading");
        String token = SharedPrefs.getInstance().get(ACCESS_TOKEN, String.class);
        APIInterface apiInterface = APILoginClient.getClient().create(APIInterface.class);

        Call<CreateListResponse> call = apiInterface.addFavorite(token, favoriteType);
        call.enqueue(new Callback<CreateListResponse>() {
            @Override
            public void onResponse(Call<CreateListResponse> call, Response<CreateListResponse> response) {
                if (response.code() == 200) {
                    dialog.dismiss();
                    dismiss();
                    Toast.makeText(getContext(), "Add Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateListResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}
