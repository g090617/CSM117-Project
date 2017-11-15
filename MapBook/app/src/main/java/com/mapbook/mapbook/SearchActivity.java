package com.mapbook.mapbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static android.content.ContentValues.TAG;

public class SearchActivity extends AppCompatActivity {
    public BookInfo someBook;
    public ZipCodeBook someZipCodeBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }


    public void onCancelClick(View v) {
        Intent intent = new Intent(this, MainNavigation.class);
        startActivity(intent);
    }

    public void onConfirmClick(View v) {
        //Intent intent = new Intent(this, MarkerDemoActivity.class);
        Intent intent = new Intent(this, MainNavigation.class);
        EditText locationSearch = (EditText) findViewById(R.id.editZipcode);
        String location = locationSearch.getText().toString();
//        intent.putExtra("location", location);
//        startActivity(intent);

        getterBookInFoByZipCode(((EditText) findViewById(R.id.editZipcode)).getText().toString());
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

        BookInfo tempBook;
        if(t.isSuccessful()){
            Object result = t.getResult();
            tempBook = (BookInfo)result;
            Log.w(TAG, "Successful ===============" + tempBook.title);
            someBook = tempBook;
        }

    }

    public BookInfo getterBookInFoByBookID(final String bookID){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                getBookInfoByBookID(bookID);
            }
        });
//        Log.w(TAG, "Successful somebook ===============" + this.someBook.title);
        return this.someBook;
    }

    public void getBookInfoByZipCode(final String zipCode){
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(
                "ZipCodeBook/" + zipCode);

        final TaskCompletionSource<Object> tcs = new TaskCompletionSource<>();


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ZipCodeBook value = new ZipCodeBook();
                value.zipBookMap = (HashMap)dataSnapshot.getValue();
                Log.d(TAG, "Book ID in zip code " + zipCode + " are " + value.toString());
                tcs.setResult(value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        Task<Object> t = tcs.getTask();
        try{
            Tasks.await(t);
        }catch (ExecutionException | InterruptedException d){

        }

        ZipCodeBook tempZip;
        if(t.isSuccessful()){
            Object result = t.getResult();
            tempZip = (ZipCodeBook) result;
            Log.w(TAG, "Successful in thread===============" + tempZip.zipBookMap);
            this.someZipCodeBook = tempZip;
            for(final String key : someZipCodeBook.zipBookMap.keySet()){
                getterBookInFoByBookID(key);
                }
        }

    }

    public ZipCodeBook getterBookInFoByZipCode(final String zipCode){
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                getBookInfoByZipCode(zipCode);
            }
        });

        Log.w(TAG, "Successful zipCode ===============" + this.someZipCodeBook);
        return this.someZipCodeBook;
    }
}
