package com.mapbook.mapbook;

/**
 * Created by g090617 on 11/4/17.
 */

public class BookInfo {
    public String title;
    public String author;
    public String isbn;
    public String publisher;
    public String subject;
    public String price;
    public String status;

    public BookInfo(){

    }

    public BookInfo(String title, String author, String isbn, String publisher,
                    String subject, String price, String status){
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.subject = subject;
        this.price = price;
        this.status = status;
    }
}
