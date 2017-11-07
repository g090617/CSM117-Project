package com.mapbook.mapbook;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by g090617 on 11/6/17.
 */

public class RequestAccess {
    private DatabaseReference mDatabase;
    public RequestAccess(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void writeNewUser(String userId, String email) {
        User user = new User(userId, email);

        mDatabase.child("users").child(userId).setValue(user);

    }

    public void addBookToUser(String userId, String email, String title, String author, String isbn,
                              String publisher,
                              String subject, String price, String status){
        User user = new User(userId, email);
        DatabaseReference newRef = mDatabase.child("users").child(userId).child("bookIDMap").push();//create a new book id

        BookInfo book = new BookInfo(newRef.getKey().toString(), status);
//        user.addBookToUser("018444441", "Basvvvy");

        newRef.setValue(book);

        addBookToBookDatabase(newRef.getKey().toString(), title, author, isbn, publisher, subject,
                price, status);
    }

    public void addBookToBookDatabase(String bookID, String title, String author, String isbn,
                                      String publisher,
                                      String subject, String price, String status){
        BookInfo book = new BookInfo(bookID, title, author, isbn, publisher,
                subject, price, status);
        mDatabase.child("BookDB").child(bookID).setValue(book);
    }

}
