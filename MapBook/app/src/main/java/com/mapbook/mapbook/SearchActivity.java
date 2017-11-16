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
import com.google.firebase.auth.FirebaseAuth;
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
    private FirebaseAuth mAuth;

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

        getBookInfoByZipCode(((EditText) findViewById(R.id.editZipcode)).getText().toString());
        mAuth = FirebaseAuth.getInstance();
//        testingFunc(mAuth.getCurrentUser().getUid());
    }

    public void getBookInfoByBookID(String bookID){
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(
                "BookDB/" + bookID);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                BookInfo value = dataSnapshot.getValue(BookInfo.class);
                Log.d(TAG, "Book title: " + value.title + "\n" +
                        "Author: " + value.author + "\n" +
                        "Publisher: " + value.publisher + "\n" +
                        "Zip code : " + value.zipCode);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    public void getBookInfoByZipCode(final String zipCode){
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(
                "ZipCodeBook/" + zipCode);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ZipCodeBook value = new ZipCodeBook();
                value.zipBookMap = (HashMap)dataSnapshot.getValue();
                Log.d(TAG, "Book ID in zip code " + zipCode + " are " + value.toString());
                for(final String key : value.zipBookMap.keySet()){
                    getBookInfoByBookID(key);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }


    public void testingFunc(String userID){
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(
                "users/" + userID);
//        userRef.orderByChild("title")

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User value = dataSnapshot.getValue(User.class);
                Log.d(TAG, "Testing Value is: " + value.email + value.userID +
                        value.bookIDMap.keySet().toString());
                EditText tempEdit = findViewById(R.id.editZipcode);
                tempEdit.setText(value.email);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}
