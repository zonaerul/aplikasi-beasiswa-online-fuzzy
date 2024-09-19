package com.agil.beasiswa_online;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import com.agil.beasiswa_online.data.RulleFuzzy;
import com.google.android.material.textfield.TextInputEditText;

public class NilaiIpk extends AppCompatActivity {
    private TextInputEditText inputNilai, inputPendapatan, inputAkademik;
    private RulleFuzzy rulleFuzzy;
    private TextView textOutputHasil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listnilai_activity); // Pastikan layout XML sesuai

        // Inisialisasi objek RulleFuzzy
        rulleFuzzy = new RulleFuzzy();

        // Menghubungkan elemen input dari XML ke variabel Java
        inputNilai = findViewById(R.id.input_nilai);
        inputPendapatan = findViewById(R.id.input_pendapatan);
        inputAkademik = findViewById(R.id.input_prestasiakademik); // Sesuaikan dengan ID yang benar
        textOutputHasil = findViewById(R.id.text_output_hasil);

        // Pastikan elemen ditemukan di XML
        if (inputNilai == null || inputPendapatan == null || inputAkademik == null || textOutputHasil == null) {
            throw new IllegalStateException("Beberapa elemen tidak ditemukan di XML layout.");
        }

        // Menghubungkan tombol hitung dari XML ke listener onClick
        AppCompatButton btnHitung = findViewById(R.id.button_hitung);
        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mendapatkan nilai dari input
                String nilaiStr = inputNilai.getText().toString();
                String pendapatanStr = inputPendapatan.getText().toString();
                String akademikStr = inputAkademik.getText().toString();

                // Menangani kemungkinan input kosong
                if (nilaiStr.isEmpty() || pendapatanStr.isEmpty() || akademikStr.isEmpty()) {
                    textOutputHasil.setText("Semua input harus diisi.");
                    return;
                }

                try {
                    // Mengkonversi input menjadi double
                    double nilai = Double.parseDouble(nilaiStr);
                    double pendapatan = Double.parseDouble(pendapatanStr);
                    double akademik = Double.parseDouble(akademikStr);

                    // Validasi nilai input (jika diperlukan)
                    if (nilai < 0 || pendapatan < 0 || akademik < 0) {
                        textOutputHasil.setText("Nilai input tidak boleh negatif.");
                        return;
                    }

                    // Menghitung hasil fuzzy
                    double hasil = rulleFuzzy.hitungHasil(nilai, pendapatan, akademik);


                    // Menentukan status layak atau tidak layak
                    String status = hasil >= 70 ? "Layak" : "Tidak Layak";

//                    StringBuilder stringBuilder = new StringBuilder();
//                    stringBuilder.append("Hasil: ").append(ha  sil).append("\n");
//                    stringBuilder.append("Status").append(status).append("\n");

                    // Menampilkan hasil pada output
//                    textOutputHasil.setText(stringBuilder.toString());
                   textOutputHasil.setText(String.format("Hasil: %.2f\nStatus: %s", hasil, status));
                } catch (NumberFormatException e) {
                    textOutputHasil.setText("Input tidak valid. Harap masukkan angka yang benar.");
                }
            }
        });
    }

    private void toast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
