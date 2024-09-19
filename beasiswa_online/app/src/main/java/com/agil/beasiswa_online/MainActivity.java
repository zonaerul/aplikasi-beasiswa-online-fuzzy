package com.agil.beasiswa_online;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.agil.beasiswa_online.api.ApiService;
import com.agil.beasiswa_online.api.ResponseLogin;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton btnLogin;
    private TextInputEditText input_email;
    private TextInputEditText input_pass;
    private TextInputEditText input_type;
    private SharedPreferences save;
    private String email_shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        save = getSharedPreferences("users", MODE_PRIVATE);
        email_shared = save.getString("email", "");

        TextView text_daftar = findViewById(R.id.text_daftar);
        text_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        input_type = findViewById(R.id.input_type);
        input_email = findViewById(R.id.input_email);
        input_email.setText(email_shared); // Set saved email if available
        input_pass = findViewById(R.id.input_pass);

        btnLogin = findViewById(R.id.login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String type = input_type.getText().toString().trim();
                String email = input_email.getText().toString().trim();
                String password = input_pass.getText().toString().trim();
                login(type, email, password);
            }
        });
    }

    private void login(String type, String email, String password) {
        // Clear previous error messages
        input_type.setError(null);
        input_email.setError(null);
        input_pass.setError(null);

        // Validate input fields
        if (TextUtils.isEmpty(type)) {
            input_type.setError("Type kosong");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            input_email.setError("Email kosong");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            input_pass.setError("Password kosong");
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new IpAddress().url) // Ensure IpAddress.url resolves correctly
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<ResponseLogin> loginCall = apiService.setLogin(type, email, password);
        loginCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()) {
                    ResponseLogin body = response.body();
                    if (body != null && body.getCodeStatus() == 200) {

                        if(type.equals("admin")){
                            Toast.makeText(MainActivity.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(MainActivity.this, "Welcome Admin", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, HomeUserActivity.class);
                            startActivity(intent);
                            finish();
                        }

                    } else{
                        Toast.makeText(MainActivity.this, body != null ? body.getStatus() : "Login gagal", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Server tidak merespon", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Terjadi kesalahan: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
