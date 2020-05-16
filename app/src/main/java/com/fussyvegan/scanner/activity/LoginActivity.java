package com.fussyvegan.scanner.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fussyvegan.scanner.APIInterface;
import com.fussyvegan.scanner.APILoginClient;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.model.CurrentUser;
import com.fussyvegan.scanner.model.AccountFlow.UserAccount;
import com.fussyvegan.scanner.model.AccountFlow.requestLogin;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();

    public int loginIntentCODE = 0;

    public int registerIntentCODE = 1;

    public int forgotPassIntentCODE = 2;

    public EditText loginEdt_Email, loginEdt_Password;
    public Button loginBtn_Login;
    public TextView loginTxt_ForgotPass, loginTxt_Register;
    public ImageView loginImg_Back;

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addContent();
        addEvent();
    }

    private void addEvent() {
        loginBtn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEdt_Email.getText().toString();
                String password = loginEdt_Password.getText().toString();
                login(email, password);
            }
        });

        loginTxt_ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(LoginActivity.this, ForgotPasswordActivity.class), forgotPassIntentCODE);
            }
        });

        loginTxt_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), registerIntentCODE);
            }
        });

        loginImg_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void login(String email, String password) {
        if(email.equals("") || password.equals("")){
            Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
        } else {
            requestLogin requestLogin = new requestLogin(email, password);
            apiInterface = APILoginClient.getClient().create(APIInterface.class);
            Call<UserAccount> call = apiInterface.requestUserLogin(requestLogin);
            call.enqueue(new Callback<UserAccount>() {
                @Override
                public void onResponse(Call<UserAccount> call, Response<UserAccount> response) {

                    Log.d(TAG, String.valueOf(response.code()));

                    if(response.code() == 200) {
                        CurrentUser.instance = response.body();
                        Log.d(TAG, "login success");

                        Intent loginBack = getIntent();
                        loginBack.putExtra("key", "Login Success");
                        setResult(loginIntentCODE, loginBack);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Wrong Email or Password", Toast.LENGTH_SHORT).show();
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
        loginEdt_Email = findViewById(R.id.loginEdt_Email);
        loginEdt_Password = findViewById(R.id.loginEdt_Password);
        loginBtn_Login = findViewById(R.id.reviewBtn_Submid);
        loginTxt_ForgotPass = findViewById(R.id.loginTxt_ForgotPass);
        loginTxt_Register = findViewById(R.id.loginTxt_Register);
        loginImg_Back = findViewById(R.id.loginImg_Back);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == registerIntentCODE && resultCode == registerIntentCODE && data != null) {
            Intent backLogin = getIntent();
            backLogin.putExtra("key", data.getStringExtra("key"));
            setResult(loginIntentCODE, backLogin);
            finish();
        } else if (requestCode == forgotPassIntentCODE && resultCode == forgotPassIntentCODE && data != null) {
            Intent backLogin = getIntent();
            backLogin.putExtra("key", data.getStringExtra("key"));
            setResult(loginIntentCODE, backLogin);
            finish();
        }

    }
}
