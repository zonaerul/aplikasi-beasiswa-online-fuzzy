package com.agil.beasiswa_online.data;

public class DataString {
    int id;
    String nama, ipk, alamat, nik, jurusan, urlImage, tanggal, sekolah;
    public DataString(int id, String nama, String ipk, String alamat, String jurusan, String urlImage, String tanggal, String sekolah, String nik){
        this.id = id;
        this.nama = nama;
        this.ipk = ipk;
        this.alamat = alamat;
        this.jurusan = jurusan;
        this.urlImage = urlImage;
        this.tanggal = tanggal;
        this.sekolah = sekolah;
        this.nik = nik;
    }

    public String getNik() {
        return nik;
    }

    public String getSekolah() {
        return sekolah;
    }

    public String getTanggal() {
        return tanggal;
    }

    public int getId() {
        return id;
    }

    public void setIpk(String ipk) {
        this.ipk = ipk;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setJurusan(String jurusan) {
        this.jurusan = jurusan;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getNama() {
        return nama;
    }

    public String getIpk() {
        return ipk;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getJurusan() {
        return jurusan;
    }

    public String getUrlImage() {
        return urlImage;
    }
}
