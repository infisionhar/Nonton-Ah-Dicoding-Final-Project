package com.hariz.noah.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class FavModel implements Parcelable {
    private int id;
    private int id_item;
    private String title;
    private String description;
    private String date;
    private String jenis;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.id_item);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.date);
        dest.writeString(this.jenis);
    }
    public FavModel(){}
    private  FavModel(Parcel in){
        this.id = in.readInt();
        this.id_item = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.date = in.readString();
        this.jenis = in.readString();
    }
    public static final Parcelable.Creator<FavModel> CREATOR = new Parcelable.Creator<FavModel>() {
        @Override
        public FavModel createFromParcel(Parcel source) {
            return new FavModel(source);
        }

        @Override
        public FavModel[] newArray(int size) {
            return new FavModel[size];
        }
    };
}
