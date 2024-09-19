package com.agil.beasiswa_online.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agil.beasiswa_online.BeasiswaPage;
import com.agil.beasiswa_online.NilaiIpk;
import com.agil.beasiswa_online.PendaftarPage;
import com.agil.beasiswa_online.ProfilePage;
import com.agil.beasiswa_online.R;
import com.agil.beasiswa_online.data.DataArray;

import java.util.ArrayList;

public class AdapterItemGridView extends RecyclerView.Adapter<AdapterItemGridView.ViewHolder> {
    Context context;
    ArrayList<DataArray> array;

    public AdapterItemGridView(Context context, ArrayList<DataArray> array) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataArray data = array.get(position);
        holder.icon.setImageResource(data.getImage());
        holder.title.setText(data.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                switch (data.getId()){
                    case 1:
                        intent = new Intent(context, PendaftarPage.class);
                        context.startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(context, BeasiswaPage.class);
                        context.startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(context, NilaiIpk.class);
                        context.startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(context, ProfilePage.class);
                        context.startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.item_image);
            title = itemView.findViewById(R.id.item_title);
        }
    }
}
