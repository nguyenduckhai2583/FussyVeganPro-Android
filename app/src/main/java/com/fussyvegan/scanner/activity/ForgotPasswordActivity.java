package com.fussyvegan.scanner.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fussyvegan.scanner.APIForgotPassClient;
import com.fussyvegan.scanner.APIInterface;
import com.fussyvegan.scanner.R;
import com.fussyvegan.scanner.model.accountFlow.ForgotPassResult;
import com.fussyvegan.scanner.model.accountFlow.Email;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    public static final String TAG = ForgotPasswordActivity.class.getSimpleName();

    public int forgotPassIntentCODE = 2;

    public ImageView forgotPassImg_Back;
    public EditText forgotPassEdt_Email;
    public Button forgotPassBtn_Reset;

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        addContent();
        addEvent();
    }

    private void addEvent() {
        forgotPassImg_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        forgotPassBtn_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = forgotPassEdt_Email.getText().toString();
                resetPassword(email);
            }
        });
    }

    private void resetPassword(String email) {
        if (email.equals("")){
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
        } else {
            Email requestForgotPass = new Email(email);
            apiInterface = APIForgotPassClient.getClient().create(APIInterface.class);
            Call<ForgotPassResult> call = apiInterface.requestForgotPassword(requestForgotPass);
            call.enqueue(new Callback<ForgotPassResult>() {
                @Override
                public void onResponse(Call<ForgotPassResult> call, Response<ForgotPassResult> response) {

                    Log.d(TAG, String.valueOf(response.code()));

                    if (response.code() == 200 ){
                        ForgotPassResult result = response.body();
                        Log.d(TAG, "Reset success");

                        Intent forgorPassBack = getIntent();
                        forgorPassBack.putExtra("key", result.getMessage());
                        setResult(forgotPassIntentCODE, forgorPassBack);

                        finish();
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Email is not exist", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ForgotPassResult> call, Throwable t) {
                    Log.d(TAG, t.getMessage());
                }
            });
        }
    }

    private void addContent() {
        forgotPassImg_Back = findViewById(R.id.forgotPassImg_Back);
        forgotPassEdt_Email = findViewById(R.id.forgotPassEdt_Email);
        forgotPassBtn_Reset = findViewById(R.id.forgotPassBtn_Reset);
    }
}
