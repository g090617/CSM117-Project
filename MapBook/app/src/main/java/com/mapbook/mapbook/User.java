package com.mapbook.mapbook;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by g090617 on 11/3/17.
 */

@IgnoreExtraProperties
public class User {
    public String email;
    public String userID;
//    public List<BookInfo> bookList;
//    public String Location;
    public List<BookInfo> bookArray;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String userID, String email) {
        this.email = email;
        this.userID = userID;
        BookInfo b1 = new BookInfo("War and Peace", "Tolsto", "99877",
                                    "Princeton", "English", "50","BUY");
        BookInfo b2 = new BookInfo("War and Peace2", "Tolsto", "99877",
                "Princeton", "English", "50","BUY");
        BookInfo [] temp = {b1, b2};
        this.bookArray = new ArrayList<>(Arrays.asList(temp));
    }

//    public void addBookToUser(String title, String author, String isbn, String publisher,
//                              String subject, String price, String status){
//        this.bookList.add(new BookInfo(title, author, isbn, publisher,
//                subject, price, status));
//    }

}
