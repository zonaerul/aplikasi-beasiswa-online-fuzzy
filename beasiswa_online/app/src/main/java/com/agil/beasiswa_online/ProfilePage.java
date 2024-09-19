package com.agil.beasiswa_online;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.agil.beasiswa_online.api.ApiService;
import com.agil.beasiswa_online.api.MyData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfilePage extends AppCompatActivity {
    private ListView list;
    private ArrayList<data> array;
    private adaptercustom adapter;
    private String EMAIL_KEY = "email";
    private SharedPreferences save;
    private String email;
    private TextView nama;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        save = getSharedPreferences("users", MODE_PRIVATE);
        email = save.getString("email", "");

        list = (ListView)findViewById(R.id.list_setting);
        nama = (TextView)findViewById(R.id.text_nama);
        array = new ArrayList<data>();
        array.add(new data(1, R.drawable.degree, "Halaman Beasiswa"));
        array.add(new data(2, R.drawable.design, "Platform"));
        array.add(new data(3, R.drawable.resume, "Bio data"));
        array.add(new data(4, R.drawable.team, "Komunitas"));
        adapter = new adaptercustom(this, array);
        list.setAdapter(adapter);

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
                        nama.setText(body.getMydata().getUsername());
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
        Toast.makeText(ProfilePage.this, text, Toast.LENGTH_SHORT).show();
    }

    private class data {
        int id, icon;
        String title;
        public data(int id, int icon, String title){
            this.id = id;
            this.icon = icon;
            this.title = title;
        }

        public int getId() {
            return id;
        }

        public int getIcon() {
            return icon;
        }

        public String getTitle() {
            return title;
        }
    }

    private class adaptercustom extends BaseAdapter {

        Context context;
        ArrayList<data> array;

        public adaptercustom(Context context, ArrayList<data> array){
            this.context = context;
            this.array = array;
        }

        @Override
        public int getCount() {
            return array.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(context).inflate(R.layout.item_listview_v1, viewGroup, false);
            ImageView icon = view.findViewById(R.id.icon_list);
            TextView title = view.findViewById(R.id.title_list);
            icon.setImageResource(array.get(i).getIcon());
            title.setText(array.get(i).getTitle());
            return view;
        }
    }
}