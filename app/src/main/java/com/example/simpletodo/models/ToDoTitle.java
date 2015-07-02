package com.example.simpletodo.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ToDoTitle implements Parcelable{

    private int id;
    private String title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ToDoTitle(){}

    protected ToDoTitle(Parcel in) {
        id = in.readInt();
        title = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ToDoTitle> CREATOR = new Parcelable.Creator<ToDoTitle>() {
        @Override
        public ToDoTitle createFromParcel(Parcel in) {
            return new ToDoTitle(in);
        }

        @Override
        public ToDoTitle[] newArray(int size) {
            return new ToDoTitle[size];
        }
    };
}
