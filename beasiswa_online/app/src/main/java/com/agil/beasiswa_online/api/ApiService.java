package com.agil.beasiswa_online.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @FormUrlEncoded
    @POST("login")
    Call<ResponseLogin> setLogin(
            @Field("type") String type,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("signup")
    Call<ResponseModel> setSIgnUp(
            @Field("type") String type,
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("bio_pendaftar")
    Call<ResponseAddPendaftar> setAddBioData(
            @Query("email") String email,
            @Query("nama") String nama,
            @Query("ipk") String ipk,
            @Query("alamat") String alamat,
            @Query("jurusan") String jurusan,
            @Query("tanggal") String tanggal,
            @Query("sekolah") String sekolah,
            @Query("nik") String nik
    );

    @GET("bio_orangtua")
    Call<ResponseModel> setAddBioDataOrangTua(
            @Query("email") String email,
            @Query("nama") String nama,
            @Query("pekerjaan") String pekerjaan,
            @Query("pendapatan") String pendapatan,
            @Query("saudara") String saudara);

    @GET("mydata")
    Call<MyData> mydata(
            @Query("email") String email
    );

    @GET("list_pendaftar")
    Call<ResponsePendaftar> getPendaftar();
}
