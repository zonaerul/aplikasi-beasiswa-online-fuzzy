package com.agil.beasiswa_online;

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

public class BioDataOrangtua extends AppCompatActivity {
    private TextInputEditText input_nama, input_pekerjaan, input_pendapatan, input_saudara;
    private AppCompatButton btnsave;
    private SharedPreferences save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.biodataorangtua_activity);
        
        save = getSharedPreferences("users", MODE_PRIVATE);
        

        input_nama = findViewById(R.id.input_nama);
        input_pekerjaan = findViewById(R.id.input_pekerjaan);
        input_pendapatan = findViewById(R.id.input_pendapatan);
        input_saudara = findViewById(R.id.input_saudara);

        btnsave = findViewById(R.id.btn_save_data);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = input_nama.getText().toString().trim();
                String pekerjaan = input_pekerjaan.getText().toString().trim();
                String pendapatan = input_pendapatan.getText().toString().trim();
                String saudara = input_saudara.getText().toString().trim();

                if (TextUtils.isEmpty(nama)) {
                    input_nama.setError("Nama tidak boleh kosong");
                    return;
                }

                if (TextUtils.isEmpty(pekerjaan)) {
                    input_pekerjaan.setError("Pekerjaan tidak boleh kosong");
                    return;
                }

                if (TextUtils.isEmpty(pendapatan)) {
                    input_pendapatan.setError("Pendapatan tidak boleh kosong");
                    return;
                }

                if (TextUtils.isEmpty(saudara)) {
                    input_saudara.setError("Jumlah saudara tidak boleh kosong");
                    return;
                }

                setAddBioDataOrangTua(nama, pekerjaan, pendapatan, saudara);
            }
        });
    }

    private void setAddBioDataOrangTua(String nama, String pekerjaan, String pendapatan, String saudara) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new IpAddress().url) // Replace with your base URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseModel> call = apiService.setAddBioDataOrangTua(save.getString("email", ""), nama, pekerjaan, pendapatan, saudara);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    ResponseModel body = response.body();
                    if (body != null && body.getCode() == 200) {
                        Toast.makeText(BioDataOrangtua.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                        // Optionally handle success actions here
                    } else {
                        Toast.makeText(BioDataOrangtua.this, "Gagal menyimpan data: " + body.getStatus(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BioDataOrangtua.this, "Server tidak merespons", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(BioDataOrangtua.this, "Terjadi kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
