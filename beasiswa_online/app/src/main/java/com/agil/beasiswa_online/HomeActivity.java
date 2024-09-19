package com.agil.beasiswa_online;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agil.beasiswa_online.adapter.AdapterItemGridView;
import com.agil.beasiswa_online.api.ApiService;
import com.agil.beasiswa_online.api.MyData;
import com.agil.beasiswa_online.data.DataArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {
    private ArrayList<DataArray> array;
    private AdapterItemGridView adapter;
    private TextView nama;
    private SharedPreferences save;

    private static final String PREFS_NAME = "users";
    private static final String EMAIL_KEY = "email";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);

        save = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        nama = (TextView) findViewById(R.id.text_name);

        RecyclerView gridView = (RecyclerView) findViewById(R.id.recycler_item_menu);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        gridView.setLayoutManager(gridLayoutManager);
        array = new ArrayList<DataArray>();
        array.add(new DataArray(1, R.drawable.team, "Pendaftar"));
        array.add(new DataArray(2, R.drawable.degree, "Beasiswa"));
        array.add(new DataArray(3, R.drawable.team, "Nilai Ipk"));
        array.add(new DataArray(4, R.drawable.team, "Profile"));

        adapter = new AdapterItemGridView(this, array);
        gridView.setAdapter(adapter);
        load_data();


    }

    private void load_data() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new IpAddress().url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<MyData> responseModelCall = apiService.mydata(save.getString(EMAIL_KEY, ""));
        responseModelCall.enqueue(new Callback<MyData>() {
            @Override
            public void onResponse(Call<MyData> call, Response<MyData> response) {
                if(response.isSuccessful()){
                    MyData body = response.body();
                    if(body != null && body.getCodeStatus() == 200){
                        Toast.makeText(HomeActivity.this, "berhasil", Toast.LENGTH_SHORT).show();
                        nama.setText("Hi, "+body.getMydata().getUsername());
                    }
                }else{
                    showToast("Server not responding");
                }
            }

            @Override
            public void onFailure(Call<MyData> call, Throwable throwable) {
                showToast("Failed to load data");
            }
        });
    }

    private void showToast(String text){
        Toast.makeText(HomeActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
