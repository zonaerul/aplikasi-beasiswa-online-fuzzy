package com.agil.beasiswa_online;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agil.beasiswa_online.adapter.AdapterItemGridView;
import com.agil.beasiswa_online.data.DataArray;

import java.util.ArrayList;

public class HomeUserActivity extends AppCompatActivity {

    private LinearLayout biodata, biodata_ortu, beasiswa;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeuser_activity);

        biodata = (LinearLayout)findViewById(R.id.layout_biodata);
        biodata_ortu = (LinearLayout)findViewById(R.id.layout_biodata_orangtua);
        beasiswa = (LinearLayout)findViewById(R.id.informasi_beasiswa);

        biodata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TambahPendaftar.class);
                startActivity(intent);
            }
        });

        biodata_ortu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BioDataOrangtua.class);
                startActivity(intent);
            }
        });


        beasiswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BeasiswaPage.class);
                startActivity(intent);
            }
        });



    }
}
