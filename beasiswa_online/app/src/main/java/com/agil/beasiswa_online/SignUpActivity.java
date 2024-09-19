package com.agil.beasiswa_online;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.agil.beasiswa_online.api.ApiService;
import com.agil.beasiswa_online.api.ResponseModel;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {
    private TextInputEditText type, email, username, password;
    private AppCompatButton btnSignup;
    private SharedPreferences save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        save = getSharedPreferences("users", MODE_PRIVATE);

        type = findViewById(R.id.input_type);
        email = findViewById(R.id.input_email);
        username = findViewById(R.id.input_username);
        password = findViewById(R.id.input_pass);

        btnSignup = findViewById(R.id.btn_signup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String typeText = type.getText().toString().trim();
                String emailText = email.getText().toString().trim();
                String usernameText = username.getText().toString().trim();
                String passwordText = password.getText().toString().trim();

                setSignUp(typeText, emailText, usernameText, passwordText);
            }
        });
    }

    private void setSignUp(String _type, String _email, String _username, String _password) {
        // Validate input fields
        if (TextUtils.isEmpty(_type) || TextUtils.isEmpty(_email) || TextUtils.isEmpty(_username) || TextUtils.isEmpty(_password)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new IpAddress().url) // Ensure IpAddress.url resolves correctly
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseModel> responseModelCall = apiService.setSIgnUp(_type, _username, _email, _password);
        responseModelCall.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    ResponseModel body = response.body();
                    if (body != null) {
                        if (body.getCode() == 200) {
                            // Save email to SharedPreferences
                            SharedPreferences.Editor edit = save.edit();
                            edit.putString("email", _email);
                            edit.apply();

                            // Navigate to home activity
                            if(_type.equals("admin")){
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Intent intent = new Intent(getApplicationContext(), HomeUserActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            Toast.makeText(SignUpActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignUpActivity.this, body.getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Server not responding", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable throwable) {
                // Log error message
                Toast.makeText(SignUpActivity.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
