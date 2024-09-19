package com.agil.beasiswa_online;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.agil.beasiswa_online.api.ApiService;
import com.agil.beasiswa_online.api.ResponseAddPendaftar;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TambahPendaftar extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView select_image;
    private Uri selectedImageUri;
    private TextInputEditText input_nama, input_ipk, input_alamat, input_jurusan, input_tanggal_lahir, input_sekolah, input_nik;
    private SharedPreferences save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data_pendaftar);

        save = getSharedPreferences("users", MODE_PRIVATE);

        input_nama = findViewById(R.id.input_nama);
        input_ipk = findViewById(R.id.input_ipk);
        input_alamat = findViewById(R.id.input_alamat);
        input_jurusan = findViewById(R.id.input_jurusan);
        input_tanggal_lahir = findViewById(R.id.input_tanggal_lahir);
        input_sekolah = findViewById(R.id.input_sekolah);
        input_nik = findViewById(R.id.input_nik);

        AppCompatButton btn_save_data = findViewById(R.id.btn_save_data);
        btn_save_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = input_nama.getText().toString();
                String ipk = input_ipk.getText().toString();
                String alamat = input_alamat.getText().toString();
                String jurusan = input_jurusan.getText().toString();
                String tanggal = input_tanggal_lahir.getText().toString();
                String sekolah = input_sekolah.getText().toString();
                String nik = input_nik.getText().toString();

                if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(ipk) || TextUtils.isEmpty(alamat) ||
                        TextUtils.isEmpty(jurusan) || TextUtils.isEmpty(tanggal) || TextUtils.isEmpty(sekolah) ||
                        TextUtils.isEmpty(nik)) {
                    Toast.makeText(TambahPendaftar.this, "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show();
                    return;
                }

                setAddBiodata(nama, ipk, alamat, jurusan, tanggal, sekolah, nik);
            }
        });

        select_image = findViewById(R.id.select_image);
        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
    }

    private void setAddBiodata(String nama, String ipk, String alamat, String jurusan, String tanggal, String sekolah, String nik) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new IpAddress().url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseAddPendaftar> responseModelCall = apiService.setAddBioData(save.getString("email", ""), nama, ipk, alamat, jurusan, tanggal, sekolah, nik);
        responseModelCall.enqueue(new Callback<ResponseAddPendaftar>() {
            @Override
            public void onResponse(Call<ResponseAddPendaftar> call, Response<ResponseAddPendaftar> response) {
                if (response.isSuccessful()) {
                    ResponseAddPendaftar body = response.body();
                    if (body != null) {
                        if (body.getCodeStatus() == 200) {
                            SharedPreferences.Editor editor = save.edit();
                            editor.putString("nama", nama);
                            editor.putString("ipk", ipk);
                            editor.putString("alamat", alamat);
                            editor.putString("jurusan", jurusan);
                            editor.putString("tanggal", tanggal);
                            editor.putString("sekolah", sekolah);
                            editor.putString("nik", nik);
                            editor.apply();

                            Toast.makeText(TambahPendaftar.this, "Berhasil disimpan", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TambahPendaftar.this, body.getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(TambahPendaftar.this, "Server tidak merespon!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAddPendaftar> call, Throwable throwable) {
                Toast.makeText(TambahPendaftar.this, "Gagal: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            select_image.setImageURI(selectedImageUri);
        }
    }
}
