package com.agil.beasiswa_online.data;

public class RulleFuzzy {

    // Konsekuen untuk setiap aturan
    private static final double[] Z = {85, 55, 85, 55, 85, 55, 85, 55, 85}; // 9 aturan

    // Fungsi keanggotaan untuk 'Nilai Tinggi'
    public static double nilaiTinggi(double x) {
        if (x <= 70) return 0;
        if (x >= 100) return 1;
        return (x - 70) / 30.0;
    }

    // Fungsi keanggotaan untuk 'Nilai Sedang'
    public static double nilaiSedang(double x) {
        if (x <= 50 || x >= 70) return 0;
        return (x - 50) / 20.0;
    }

    // Fungsi keanggotaan untuk 'Nilai Rendah'
    public static double nilaiRendah(double x) {
        if (x <= 10) return 1;
        if (x >= 40) return 0;
        return (40 - x) / 30.0;
    }

    // Fungsi keanggotaan untuk 'Pendapatan Tinggi'
    public static double pendapatanTinggi(double x) {
        if (x <= 2000) return 0;
        if (x >= 3000) return 1;
        return (x - 2000) / 1000.0;
    }

    // Fungsi keanggotaan untuk 'Pendapatan Sedang'
    public static double pendapatanSedang(double x) {
        if (x <= 1500 || x >= 2000) return 0;
        return (x - 1500) / 500.0;
    }

    // Fungsi keanggotaan untuk 'Pendapatan Rendah'
    public static double pendapatanRendah(double x) {
        if (x <= 500) return 1;
        if (x >= 1000) return 0;
        return (1000 - x) / 500.0;
    }

    // Fungsi keanggotaan untuk 'Prestasi Akademik Khusus'
    public static double prestasiAkademikKhusus(double x) {
        if (x <= 3) return 1;
        if (x >= 4) return 0;
        return (4 - x) / 1.0;
    }

    // Fungsi keanggotaan untuk 'Prestasi Akademik Biasa'
    public static double prestasiAkademikBiasa(double x) {
        if (x <= 1) return 1;
        if (x >= 2) return 0;
        return (x - 1) / 1.0;
    }

    // Fungsi min untuk mendapatkan derajat keanggotaan terkecil
    private static double min(double... values) {
        double min = values[0];
        for (double value : values) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    // Menghitung numerator dari rata-rata berbobot
    private static double setNumerator(double... apreds) {
        double numerator = 0;
        for (int i = 0; i < apreds.length; i++) {
            numerator += apreds[i] * Z[i];
        }
        return numerator;
    }

    // Menghitung denominator dari rata-rata berbobot
    private static double setDenominator(double... apreds) {
        double denominator = 0;
        for (double apred : apreds) {
            denominator += apred;
        }
        return denominator;
    }

    // Menghitung hasil akhir dari fuzzy Tsukamoto
    private static double getResult(double numerator, double denominator) {
        return denominator == 0 ? 0 : numerator / denominator;
    }

    // Metode untuk menghitung output fuzzy
    public double hitungHasil(double nilai, double pendapatan, double prestasiAkademik) {
        double myuNilaiRendah = nilaiRendah(nilai);
        double myuNilaiSedang = nilaiSedang(nilai);
        double myuNilaiTinggi = nilaiTinggi(nilai);
        double myuPendapatanRendah = pendapatanRendah(pendapatan);
        double myuPendapatanSedang = pendapatanSedang(pendapatan);
        double myuPendapatanTinggi = pendapatanTinggi(pendapatan);
        double myuPrestasiAkademikKhusus = prestasiAkademikKhusus(prestasiAkademik);
        double myuPrestasiAkademikBiasa = prestasiAkademikBiasa(prestasiAkademik);

        double apred1 = min(myuNilaiTinggi, myuPendapatanRendah, myuPrestasiAkademikKhusus);
        double apred2 = min(myuNilaiTinggi, myuPendapatanSedang, myuPrestasiAkademikBiasa);
        double apred3 = min(myuNilaiTinggi, myuPendapatanTinggi, myuPrestasiAkademikKhusus);
        double apred4 = min(myuNilaiSedang, myuPendapatanRendah, myuPrestasiAkademikBiasa);
        double apred5 = min(myuNilaiSedang, myuPendapatanSedang, myuPrestasiAkademikKhusus);
        double apred6 = min(myuNilaiSedang, myuPendapatanTinggi, myuPrestasiAkademikBiasa);
        double apred7 = min(myuNilaiRendah, myuPendapatanRendah, myuPrestasiAkademikKhusus);
        double apred8 = min(myuNilaiRendah, myuPendapatanSedang, myuPrestasiAkademikBiasa);
        double apred9 = min(myuNilaiRendah, myuPendapatanTinggi, myuPrestasiAkademikKhusus);

        double numerator = setNumerator(apred1, apred2, apred3, apred4, apred5, apred6, apred7, apred8, apred9);
        double denominator = setDenominator(apred1, apred2, apred3, apred4, apred5, apred6, apred7, apred8, apred9);

        return getResult(numerator, denominator);

// Determine if the result is eligible
//        return result >= 70; //jika hasil kurang dari 70 ke adaan jadi false

    }
}
