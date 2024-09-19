package com.agil.beasiswa_online.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponsePendaftar {
    @SerializedName("code")
    int code;

    @SerializedName("status")
    String status;

    public String getStatus() {
        return status;
    }

    public int getCodeStatus() {
        return code;
    }

    @SerializedName("data")
    public List<ListPendaftar> listPendaftar;

    public List<ListPendaftar> getListPendaftar() {
        return listPendaftar;
    }

    public class ListPendaftar {
        @SerializedName("id_biodatapendaftar")
        private int idPendaftar;

        @SerializedName("id_login")
        private String idLogin;

        @SerializedName("nama_lengkap")
        private String namaLengkap;

        @SerializedName("ipk")
        private String ipk;

        @SerializedName("alamat")
        private String alamat;

        @SerializedName("jurusan")
        private String jurusan;

        @SerializedName("profile")
        private String profile;

        @SerializedName("tanggal_lahir")
        String tanggal_lahir;

        @SerializedName("nik")
        String nik;

        @SerializedName("sekolah")
        String sekolah;

        public String getSekolah() {
            return sekolah;
        }

        public String getNik() {
            return nik;
        }

        public String getTanggal_lahir() {
            return tanggal_lahir;
        }

        public int getIdPendaftar() {
            return idPendaftar;
        }

        public String getIdLogin() {
            return idLogin;
        }

        public String getNamaLengkap() {
            return namaLengkap;
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

        public String getProfile() {
            return profile;
        }
    }
}
