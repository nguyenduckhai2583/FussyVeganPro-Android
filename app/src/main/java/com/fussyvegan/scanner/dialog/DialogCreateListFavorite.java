package com.fussyvegan.scanner.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.fussyvegan.scanner.APIInterface;
import com.fussyvegan.scanner.APILoginClient;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.model.favorite.CreateGroupRequest;
import com.fussyvegan.scanner.model.favorite.CreateListResponse;
import com.fussyvegan.scanner.utils.SharedPrefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fussyvegan.scanner.utils.Constant.ACCESS_TOKEN;

public class DialogCreateListFavorite extends AppCompatDialogFragment {

    OnCreateListFavoriteListener listener;
    EditText edtListName;
    Button btnCancel, btnAdd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_create_list, container, false);

        addContent(view);
        addEvent();

        return view;
    }

    public void CreateFavoriteGroup(String name) {
        String token = SharedPrefs.getInstance().get(ACCESS_TOKEN, String.class);
        APIInterface apiInterface = APILoginClient.getClient().create(APIInterface.class);

        Call<CreateListResponse> call = apiInterface.addGroupFavorite(token, new CreateGroupRequest(name));
        call.enqueue(new Callback<CreateListResponse>() {
            @Override
            public void onResponse(Call<CreateListResponse> call, Response<CreateListResponse> response) {
                if (response.code() == 200) {
                    listener.onCreateListFavorite();
                }
                dismiss();
            }

            @Override
            public void onFailure(Call<CreateListResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Add Error", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });
    }

    private void addEvent() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtListName.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(view.getContext(), "Please enter name", Toast.LENGTH_SHORT ).show();
                } else {
                    edtListName.setText("");
                    CreateFavoriteGroup(name);
                }
            }
        });
    }

    private void addContent(View view) {
        edtListName = view.findViewById(R.id.edtListName);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnCancel = view.findViewById(R.id.btnCancel);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public interface OnCreateListFavoriteListener {
        void onCreateListFavorite();
    }
}
