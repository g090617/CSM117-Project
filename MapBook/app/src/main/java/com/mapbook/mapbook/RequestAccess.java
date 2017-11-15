package com.mapbook.mapbook;

import android.app.usage.UsageEvents;
import android.support.v7.app.AppCompatActivity;
import android.util.EventLog;
import android.util.Log;
import android.widget.EditText;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static android.content.ContentValues.TAG;

/**
 * Created by g090617 on 11/6/17.
 */

public class RequestAccess{
    private DatabaseReference mDatabase;
    public RequestAccess(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
    final ArrayList<BookInfo> bookInfoArrayList = new ArrayList<>(10);

    public void writeNewUser(String userId, String email) {
        User user = new User(userId, email);

        mDatabase.child("users").child(userId).setValue(user);

    }

    public void addBookToUser(String userId, String email, String title, String author, String isbn,
                              String publisher,
                              String subject, String price, String status,String zipCode,
                              String longtitude, String latitude){
        User user = new User(userId, email);
        final DatabaseReference newRef = mDatabase.child("users").child(userId).child("bookIDMap").push();//create a new book id

        BookInfo book = new BookInfo(newRef.getKey().toString(), status);
//        user.addBookToUser("018444441", "Basvvvy");

        newRef.setValue(book);

        addBookToBookDatabase(newRef.getKey().toString(), title, author, isbn, publisher, subject,
                price, status, zipCode, longtitude, latitude);

        addBookToLocation(newRef.getKey().toString(), zipCode);

        getUser(userId);




        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                getBookInfoByBookID(newRef.getKey().toString());
            }
        });



//        Log.d(TAG, "hash code is  " + this.bookInfoArrayList.hashCode());

    }

    public void addBookToBookDatabase(String bookID, String title, String author, String isbn,
                                      String publisher,
                                      String subject, String price, String status, String zipCode,
                                      String longtitude, String latitude){
        BookInfo book = new BookInfo(bookID, title, author, isbn, publisher,
                subject, price, status, zipCode, longtitude, latitude);
        mDatabase.child("BookDB").child(bookID).setValue(book);
    }

    public void addBookToLocation(String bookID, String zipCode){
//        BookInfo book = new BookInfo(bookID, "Buy");
        mDatabase.child("ZipCodeBook").child(zipCode).child(bookID).setValue(bookID);
    }

    public void getUser(String userID){
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(
                "users/" + userID);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User value = dataSnapshot.getValue(User.class);
                Log.d(TAG, "Value is: " + value.email + value.userID + value.bookIDMap.keySet().toString());
                for(final String key : value.bookIDMap.keySet()){

                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            getBookInfoByBookID(key);
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    public void getBookInfoByBookID(String bookID){
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(
                "BookDB/" + bookID);

        final TaskCompletionSource<Object> tcs = new TaskCompletionSource<>();


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                BookInfo value = dataSnapshot.getValue(BookInfo.class);
                Log.d(TAG, "Book is: " + value.title + value.author + value.publisher + value.zipCode);
//                titleField.setText("adadasdasd");
                tcs.setResult(value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        Task<Object> t = tcs.getTask();
        try{
            Tasks.await(t);
        }catch (ExecutionException | InterruptedException d){

        }

        if(t.isSuccessful()){
            Object result = t.getResult();
            System.out.print("Successful ===============");
            System.out.print(result);
            BookInfo tempBook = (BookInfo)result;
            Log.w(TAG, "Successful ===============" + tempBook.title);
        }


    }

}
