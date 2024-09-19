package com.agil.beasiswa_online.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.agil.beasiswa_online.R;
import com.agil.beasiswa_online.data.DataString;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class AdapterItemHorizontal_v1 extends RecyclerView.Adapter<AdapterItemHorizontal_v1.Viewadapter> {
    Context context;
    ArrayList<DataString> array;

    public AdapterItemHorizontal_v1(Context context, ArrayList<DataString> array){
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public Viewadapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewadapter(LayoutInflater.from(context).inflate(R.layout.item_recycler_data_horizontal, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewadapter holder,final int position) {
        DataString data = array.get(position);
        holder.nama.setText("Nama: "+data.getNama());
        holder.ipk.setText("Ipk: "+data.getIpk());
        holder.jurusan.setText("Jurusan: "+data.getJurusan());
        holder.alamat.setText("Alamat: "+data.getAlamat());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view = LayoutInflater.from(context).inflate(R.layout.alert_dialog_custom_v1, null);
                AlertDialog alertDialog = new AlertDialog.Builder(context)
                        .setView(view)
                        .create();

                AppCompatButton btnSave = (AppCompatButton) view.findViewById(R.id.btn_save);
                AppCompatButton btnEdit = (AppCompatButton) view.findViewById(R.id.btn_edit);
                AppCompatButton btnDelete = (AppCompatButton) view.findViewById(R.id.btn_delete);

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        Toast.makeText(context, "Save data", Toast.LENGTH_SHORT).show();
                    }
                });
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View editView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_edit, null);
                        AlertDialog editDialog = new AlertDialog.Builder(context)
                                .setView(editView)
                                .create();
                        alertDialog.dismiss();

                        final TextInputEditText nama = editView.findViewById(R.id.input_nama);
                        nama.setText(data.getNama());
                        TextInputEditText ipk = editView.findViewById(R.id.input_ipk);
                        ipk.setText(data.getIpk());
                        TextInputEditText alamat = editView.findViewById(R.id.input_alamat);
                        alamat.setText(data.getAlamat());
                        TextInputEditText jurusan = editView.findViewById(R.id.input_jurusan);
                        jurusan.setText(data.getJurusan());
                        final TextInputEditText tanggal = editView.findViewById(R.id.input_tanggal_lahir);
                        tanggal.setText(data.getTanggal());
                        final TextInputEditText nik = editView.findViewById(R.id.input_nik);
                        nik.setText(data.getNik());

                        final TextInputEditText sekolah = editView.findViewById(R.id.input_sekolah);
                        sekolah.setText(data.getSekolah());

                        AppCompatButton btnSaveData = (AppCompatButton) editView.findViewById(R.id.btn_save_data);
                        btnSaveData.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                data.setNama(nama.getText().toString());
                                data.setIpk(ipk.getText().toString());
                                data.setJurusan(jurusan.getText().toString());
                                data.setAlamat(alamat.getText().toString());
                                array.remove(position);
                                array.add(0, data);

                                // Memberi tahu adapter bahwa data telah diubah
                                notifyItemMoved(position, 0);
                                notifyItemChanged(0);
                                editDialog.dismiss();
                                Toast.makeText(context, "Save data", Toast.LENGTH_SHORT).show();
                            }
                        });

                        editDialog.show();


                    }
                });
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        array.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, array.size());
                        alertDialog.dismiss();
                        Toast.makeText(context, "Delete data", Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    class Viewadapter extends RecyclerView.ViewHolder {
        TextView nama, ipk, jurusan, alamat;

        public Viewadapter(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
            ipk = itemView.findViewById(R.id.ipk);
            jurusan = itemView.findViewById(R.id.jurusan);
            alamat = itemView.findViewById(R.id.alamat);

        }
    }
}
