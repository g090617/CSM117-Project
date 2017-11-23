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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static android.content.ContentValues.TAG;

public class SearchActivity extends AppCompatActivity {
//    public BookInfo someBook;
//    public ZipCodeBook someZipCodeBook;
    //private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //mAuth = FirebaseAuth.getInstance();
//        testingFunc(mAuth.getCurrentUser().getUid());
//        getBookByZipCodeAndTitle(((EditText) findViewById(R.id.editZipcode)).getText().toString(),"Kjnkjnkjn");
        //getUserInfoByUserID(mAuth.getCurrentUser().getUid());
    }


    public void onCancelClick(View v) {
        Intent intent = new Intent(this, MainNavigation.class);
        startActivity(intent);
    }

    public void onConfirmClick(View v) {
        Intent intent = new Intent(this, MainNavigation.class);
        EditText zip = (EditText) findViewById(R.id.editZipcode);
        String zipcode = zip.getText().toString();
        EditText title = (EditText) findViewById(R.id.editTitle);
        String bookTitle = title.getText().toString();
        intent.putExtra("zip", zipcode);
        intent.putExtra("title", bookTitle);
        Log.w(TAG, "on confirm print zip here");
        if(zip != null)
            Log.w(TAG, zipcode.toString());
        else
            Log.w(TAG, "print title here");
        Log.w(TAG, bookTitle);
        startActivity(intent);

//        getBookInfoByZipCode(((EditText) findViewById(R.id.editZipcode)).getText().toString());
//        mAuth = FirebaseAuth.getInstance();
//        testingFunc(mAuth.getCurrentUser().getUid());
//        getBookByZipCodeAndTitle(((EditText) findViewById(R.id.editZipcode)).getText().toString(),"Kjnkjnkjn");
//        getUserInfoByUserID(mAuth.getCurrentUser().getUid());
    }
}
