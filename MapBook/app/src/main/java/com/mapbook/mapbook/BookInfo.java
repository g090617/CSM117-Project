package com.mapbook.mapbook;

/**
 * Created by g090617 on 11/4/17.
 */

public class BookInfo {
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
}
