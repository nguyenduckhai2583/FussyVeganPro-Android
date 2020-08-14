package com.fussyvegan.scanner.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fussyvegan.scanner.APIInterface;
import com.fussyvegan.scanner.APIRegisterClient;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.model.CurrentUser;
import com.fussyvegan.scanner.model.accountFlow.UserAccount;
import com.fussyvegan.scanner.model.accountFlow.RequestRegister;
import com.fussyvegan.scanner.utils.Constant;
import com.fussyvegan.scanner.utils.SharedPrefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = RegisterActivity.class.getSimpleName();

    public int registerIntentCODE = 1;

    public ImageView registerImg_Back;
    public EditText registerEdt_Name, registerEdt_Email, registerEdt_Password, registerEdt_ConfirmPassword;
    public Button registerBtn_Register;

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        addContent();
        addEvent();
    }

    private void addEvent() {
        registerImg_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        registerBtn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = registerEdt_Name.getText().toString();
                String email = registerEdt_Email.getText().toString();
                String password = registerEdt_Password.getText().toString();
                String confirmPassword = registerEdt_ConfirmPassword.getText().toString();
                if (password.equals(confirmPassword)) {
                    Register(name, email, password);
                } else {
                    Toast.makeText(RegisterActivity.this, "Wrong Password Confirm", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Register(String name, String email, String password) {
        if(name.equals("") || email.equals("") || password.equals("")) {
            Toast.makeText(RegisterActivity.this, "Invalid information", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
        } else {
            RequestRegister requestRegister = new RequestRegister(name, email, password);
            apiInterface = APIRegisterClient.getClient().create(APIInterface.class);
            Call<UserAccount> call = apiInterface.requestRegister(requestRegister);
            call.enqueue(new Callback<UserAccount>() {
                @Override
                public void onResponse(Call<UserAccount> call, Response<UserAccount> response) {

                    Log.d(TAG, String.valueOf(response.code()));

                    if (response.code() == 200) {
                        CurrentUser.instance = response.body();
                        Log.d(TAG, "Registration successfull");
                        SharedPrefs.getInstance().put(Constant.ACCESS_TOKEN, response.body().getData().getToken());
                        SharedPrefs.getInstance().put(Constant.USER_NAME, response.body().getData().getUser().getName());
                        SharedPrefs.getInstance().put(Constant.EMAIL, response.body().getData().getUser().getEmail());
                        SharedPrefs.getInstance().put(Constant.IS_LOGIN, true);
                        Intent registerBack = getIntent();
                        registerBack.putExtra("key", "Registration successfull");
                        setResult(registerIntentCODE, registerBack);
                        finish();
                    } else {

                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserAccount> call, Throwable t) {
                    Log.d(TAG, t.getMessage());
                }
            });
        }
    }

    private void addContent() {
        registerImg_Back = findViewById(R.id.registerImg_Back);
        registerEdt_Name = findViewById(R.id.reviewEdt_Name);
        registerEdt_Email = findViewById(R.id.reviewEdt_Email);
        registerEdt_Password = findViewById(R.id.reviewEdt_Title);
        registerEdt_ConfirmPassword = findViewById(R.id.registerEdt_ConfirmPassword);
        registerBtn_Register = findViewById(R.id.reviewBtn_Submid);
    }
}
