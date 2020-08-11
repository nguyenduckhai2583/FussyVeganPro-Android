package com.fussyvegan.scanner.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fussyvegan.scanner.APIInterface;
import com.fussyvegan.scanner.APILoginClient;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.model.accountFlow.RequestChangePassword;
import com.fussyvegan.scanner.model.accountFlow.UserAccount;
import com.fussyvegan.scanner.utils.SharedPrefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fussyvegan.scanner.utils.Constant.ACCESS_TOKEN;

public class ChangePasswordActivity extends AppCompatActivity {

    public static final String TAG = ChangePasswordActivity.class.getSimpleName();

    public int ChangePasswordCODE = 4;

    ImageView changePassImg_Back;
    EditText changePassEdt_OldPass, changePassEdt_NewPass, changePassEdt_ConfirmNewPass;
    Button changePassBtn_Change;

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        addContent();
        addEvent();
    }

    private void addEvent() {
        changePassImg_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        changePassBtn_Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldpass = changePassEdt_OldPass.getText().toString();
                String newpass = changePassEdt_NewPass.getText().toString();
                String confirmNewPass = changePassEdt_ConfirmNewPass.getText().toString();

                RequestChangePassword requestChangePassword = new RequestChangePassword(oldpass, newpass, confirmNewPass);
                String token = SharedPrefs.getInstance().get(ACCESS_TOKEN, String.class);

                if(oldpass.equals("") || newpass.equals("") || confirmNewPass.equals("")) {
                    Toast.makeText(ChangePasswordActivity.this, "Invalid password", Toast.LENGTH_SHORT).show();
                } else if (!newpass.equals(confirmNewPass)) {
                    Toast.makeText(ChangePasswordActivity.this, R.string.message_wrong_confirm_pass, Toast.LENGTH_SHORT).show();
                } else if(newpass.length() < 6) {
                    Toast.makeText(ChangePasswordActivity.this, R.string.message_wrong_length_papss, Toast.LENGTH_SHORT).show();
                } else {
                    apiInterface = APILoginClient.getClient().create(APIInterface.class);
                    Call<UserAccount> call = apiInterface.requestChangePassword(token, requestChangePassword);
                    call.enqueue(new Callback<UserAccount>() {
                        @Override
                        public void onResponse(Call<UserAccount> call, Response<UserAccount> response) {
                            if(response.code() == 200) {
                                Intent back = getIntent();
                                back.putExtra("key", response.body().getMessage());
                                setResult(ChangePasswordCODE, back);
                                finish();
                            } else {
                                Toast.makeText(ChangePasswordActivity.this, "Data Invalid", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserAccount> call, Throwable t) {
                            Log.d(TAG, t.getMessage());
                        }
                    });
                }

            }
        });
    }

    private void addContent() {
        changePassImg_Back = findViewById(R.id.changePassImg_Back);
        changePassEdt_OldPass = findViewById(R.id.changePassEdt_OldPass);
        changePassEdt_NewPass = findViewById(R.id.changePassEdt_NewPass);
        changePassEdt_ConfirmNewPass = findViewById(R.id.changePassEdt_ConfirmNewPass);
        changePassBtn_Change = findViewById(R.id.changePassBtn_Change);
    }
}
