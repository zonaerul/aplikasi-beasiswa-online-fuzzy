package com.agil.beasiswa_online.data;

public class DataArray {
    String title;
    int image, id;
    public DataArray(int id, int image, String title){
        this.id = id;
        this.image = image;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }
}
