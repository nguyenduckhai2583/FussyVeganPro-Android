package com.fussyvegan.scanner;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.fussyvegan.scanner.activity.MainActivity;
import com.fussyvegan.scanner.adapter.MyListAdapter;
import com.fussyvegan.scanner.model.favorite.CreateGroupRequest;
import com.fussyvegan.scanner.model.favorite.CreateListResponse;
import com.fussyvegan.scanner.model.favorite.GroupFavorite;
import com.fussyvegan.scanner.model.favorite.ListFavoriteResponse;
import com.fussyvegan.scanner.utils.SharedPrefs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fussyvegan.scanner.utils.Constant.ACCESS_TOKEN;

public class FavoriteListFragment extends Fragment implements MyListAdapter.OnRemoveGroupListener {

    RecyclerView recyclerListFavorite;
    MyListAdapter adapter;
    ArrayList<GroupFavorite> list;
    MainActivity activity;
    FloatingActionButton btnAdd;
    ProgressDialog dialog;

    private Dialog dialogCreateList;

    public FavoriteListFragment() {
        // Required empty public constructor
    }

    public static FavoriteListFragment newInstance() {

        return new FavoriteListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity)this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_favorite_list, container, false);

        addContent(view);
        addEvent();
        getListGroupFavorite();

        return view;
    }

    private void addContent(View view) {
        btnAdd = view.findViewById(R.id.btnAdd);
        list = new ArrayList<>();
        recyclerListFavorite = view.findViewById(R.id.recyclerListFavorite);
        adapter = new MyListAdapter(list, getContext(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerListFavorite.setLayoutManager(layoutManager);
        recyclerListFavorite.setAdapter(adapter);
    }

    private void addEvent() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogCreateList();
            }
        });
    }

    private void showDialogCreateList() {
        dialogCreateList = new Dialog(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_create_list, null);
        dialogCreateList.setContentView(view);

        final EditText edtListName = dialogCreateList.findViewById(R.id.edtListName);

        dialogCreateList.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogCreateList.dismiss();
            }
        });

        dialogCreateList.findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtListName.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(view.getContext(), "Please enter name", Toast.LENGTH_SHORT ).show();
                } else {
                    dialogCreateList.dismiss();
                    addGroup(name);
                }
            }
        });

        dialogCreateList.show();
    }

    private void getListGroupFavorite() {
        dialog = ProgressDialog.show(getContext(), "Loading...", "Please wait...", true);
        list.clear();
        String token = SharedPrefs.getInstance().get(ACCESS_TOKEN, String.class);
        APIInterface apiInterface = APILoginClient.getClient().create(APIInterface.class);

        Call<ListFavoriteResponse> call = apiInterface.getListGroup(token);
        call.enqueue(new Callback<ListFavoriteResponse>() {
            @Override
            public void onResponse(Call<ListFavoriteResponse> call, Response<ListFavoriteResponse> response) {
                list.addAll(response.body().getList());
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<ListFavoriteResponse> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

    private void addGroup(String name) {
        dialog = ProgressDialog.show(getContext(), "Loading...", "Please wait...", true);
        String token = SharedPrefs.getInstance().get(ACCESS_TOKEN, String.class);
        APIInterface apiInterface = APILoginClient.getClient().create(APIInterface.class);

        Call<CreateListResponse> call = apiInterface.addGroupFavorite(token, new CreateGroupRequest(name));
        call.enqueue(new Callback<CreateListResponse>() {
            @Override
            public void onResponse(Call<CreateListResponse> call, Response<CreateListResponse> response) {
                dialog.dismiss();
                if (response.code() == 200) {
                    getListGroupFavorite();
                } else {
                    Toast.makeText(getContext(), "Add Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateListResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Add Error", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    @Override
    public void removeGroup(int groupId) {
        dialog = ProgressDialog.show(getContext(), "Loading...", "Please wait...", true);
        String token = SharedPrefs.getInstance().get(ACCESS_TOKEN, String.class);
        APIInterface apiInterface = APILoginClient.getClient().create(APIInterface.class);

        Call<CreateListResponse> call = apiInterface.removeGroup(token, groupId);
        call.enqueue(new Callback<CreateListResponse>() {
            @Override
            public void onResponse(Call<CreateListResponse> call, Response<CreateListResponse> response) {
                dialog.dismiss();
                if (response.code() == 200) {
                    getListGroupFavorite();
                } else {
                    Toast.makeText(getContext(), "Remove Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CreateListResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Add Error", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }
}