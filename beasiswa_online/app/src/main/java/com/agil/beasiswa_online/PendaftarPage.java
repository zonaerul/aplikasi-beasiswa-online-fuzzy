package com.agil.beasiswa_online;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.agil.beasiswa_online.adapter.AdapterItemHorizontal_v1;
import com.agil.beasiswa_online.api.ApiService;
import com.agil.beasiswa_online.api.ResponseModel;
import com.agil.beasiswa_online.api.ResponsePendaftar;
import com.agil.beasiswa_online.data.DataString;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PendaftarPage extends AppCompatActivity {
    private RecyclerView recycler;
    private ArrayList<DataString> array;
    private AdapterItemHorizontal_v1 adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pendaftar_activity);

        recycler = findViewById(R.id.item_recycler_data_horizontal);
        array = new ArrayList<>();
        swipeRefreshLayout = findViewById(R.id.swipe);

        FloatingActionButton floatbtn = (FloatingActionButton) findViewById(R.id.fab);
        floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TambahPendaftar.class);
                startActivity(intent);
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        adapter = new AdapterItemHorizontal_v1(this, array);
        recycler.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Panggil method untuk memperbarui data
                refreshData();
            }
        });

        apiListPendaftar();
    }
    private void refreshData(){
        array.clear();
        apiListPendaftar();
        // Beri tahu adapter bahwa data telah berubah
        adapter.notifyDataSetChanged();
        // Selesai refresh
        swipeRefreshLayout.setRefreshing(false);
    }

    private void apiListPendaftar(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new IpAddress().url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponsePendaftar> responseModelCall = apiService.getPendaftar();
        responseModelCall.enqueue(new Callback<ResponsePendaftar>() {
            @Override
            public void onResponse(Call<ResponsePendaftar> call, Response<ResponsePendaftar> response) {
                if(response.isSuccessful()){
                    ResponsePendaftar body = response.body();
                    if(body != null){
                        if(body.getCodeStatus() == 200){
                            Toast.makeText(PendaftarPage.this, "Terhubung", Toast.LENGTH_SHORT).show();
                            for(ResponsePendaftar.ListPendaftar pendaftar : body.getListPendaftar()){
                                array.add(new DataString(pendaftar.getIdPendaftar(), pendaftar.getNamaLengkap(), pendaftar.getIpk(), pendaftar.getAlamat(), pendaftar.getJurusan(), pendaftar.getProfile(), pendaftar.getTanggal_lahir(), pendaftar.getSekolah(), pendaftar.getNik()));
                            }
                        }
                    }
                }else{
                    showToast("server not response");
                }
            }

            @Override
            public void onFailure(Call<ResponsePendaftar> call, Throwable throwable) {
                showToast(throwable.getMessage());
            }
        });
    }

    private void showToast(String text){
        System.out.println("Toast: "+text);
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
