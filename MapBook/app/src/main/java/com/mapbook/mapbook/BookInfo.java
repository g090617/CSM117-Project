package com.mapbook.mapbook;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by g090617 on 11/4/17.
 */

@SuppressLint("ParcelCreator")
public class BookInfo implements Parcelable {
    public String bookID;
    public String title;
    public String author;
    public String isbn;
    public String publisher;
    public String subject;
    public String price;
    public String status;
    public String zipCode;
    public String longtitude;
    public String latitude;

    public BookInfo(){

    }
    public BookInfo(String bookID, String title, String author, String isbn, String publisher,
                    String subject, String price, String status, String zipCode, String longtitude,
                    String latitude){
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.subject = subject;
        this.price = price;
        this.status = status;
        this.zipCode = zipCode;
        this.longtitude = longtitude;
        this.latitude = latitude;
    }

    public BookInfo(String bookID, String status){
        this.bookID = bookID;
        this.status = status;
    }

    public BookInfo(Parcel in) {
        String[] data = new String[9];
        in.readStringArray(data);
        this.title = data[0];
        this.author = data[1];
        this.isbn = data[2];
        this.publisher = data[3];
        this.subject = data[4];
        this.price = data[5];
        this.zipCode = data[6];
        this.longtitude = data[7];
        this.latitude = data[8];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{ this.title, this.author, this.isbn,
                this.publisher, this.subject, this.price, this.zipCode, this.longtitude, this.latitude});
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
      public BookInfo createFromParcel(Parcel in){
          return new BookInfo(in);
      }
      public BookInfo[] newArray(int size){
          return new BookInfo[size];
      }
    };
}
