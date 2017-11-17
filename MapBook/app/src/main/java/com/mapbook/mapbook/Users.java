package com.mapbook.mapbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.ContentValues.TAG;

import android.view.View;
import android.widget.Button;

public class Users extends AppCompatActivity implements View.OnClickListener {

    private Button go_back_button;

    private ArrayAdapter<String> listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

    }

    public void getHistory(String userID){
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(
                "Chat/user2/");

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                HashMap<String, ArrayList<String>> tempHist = (HashMap<String, ArrayList<String>>) dataSnapshot.getValue();
//                ChatHist chatHist = dataSnapshot.getValue(ChatHist.class);
                Log.d(TAG, "Value is " + dataSnapshot.getValue());
//                Log.d(TAG, "History is " + chatHist.toString());
                Log.d(TAG, "History is " + tempHist.keySet());
                ArrayList<String> userList = new ArrayList<>(tempHist.keySet());
                ListView tempList = findViewById(R.id.usersList);
                tempList.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, userList));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()) {
            case R.id.go_back:
                Intent intent = new Intent(this, MainNavigation.class);
                startActivity(intent);
                return;
            default:
                return;
        }
    }
}
