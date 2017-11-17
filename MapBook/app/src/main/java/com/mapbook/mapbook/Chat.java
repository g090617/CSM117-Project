package com.mapbook.mapbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;



public class Chat extends AppCompatActivity {

    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Toolbar toolbar;
    FirebaseAuth mAuth;
    DatabaseReference messageRef;
    User chatTo;
    ArrayList<String> chatHistory;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mAuth = FirebaseAuth.getInstance();
        layout = (LinearLayout) findViewById(R.id.layout1);
        layout_2 = (RelativeLayout) findViewById(R.id.layout2);
        sendButton = (ImageView) findViewById(R.id.sendButton);
        messageArea = (EditText) findViewById(R.id.messageArea);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        toolbar = findViewById(R.id.chat_with_toolbar);

        Intent myIntent = getIntent();
        final String userID = myIntent.getStringExtra("userID");
        Log.d(TAG, "userID is " + userID);
        toolbar.setTitle(userID);
        messageRef = FirebaseDatabase.getInstance().getReference("Chat/");
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = "0" + messageArea.getText().toString();
                DatabaseReference tempRef = FirebaseDatabase.getInstance().getReference("Chat/user2/user1");
                if (!messageText.equals("")) {
//                    HashMap<String, ArrayList<String>> map = new HashMap<>();
//                    ArrayList<String> tempList = chatHistory;
//                    tempList.add(messageText);
//                    map.put("user1", tempList);
//                    tempRef.setValue(map);

                    ArrayList<String> tempList = chatHistory;
                    tempList.add(messageText);
                    tempRef.setValue(tempList);
                    addMessageBox("You:\n" + messageText.substring(1), 1);
//
                    messageArea.setText("");
                }
            }
        });
        if (mAuth.getCurrentUser()!=null) {
            messageRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Log.d(TAG, "Value is " + dataSnapshot.getValue());
                    HashMap<String, ArrayList<String>> tempHist = (HashMap<String, ArrayList<String>>)
                            dataSnapshot.getValue();
                    Log.d(TAG, "History is " + tempHist.keySet());
                    chatHistory = tempHist.get("user1");
                    for(int i = 0; i < chatHistory.size(); i++) {
                        if(chatHistory.get(i).charAt(0) == '0')
                            addMessageBox("You:\n" + chatHistory.get(i), 1);
                        else
                            addMessageBox(userID +":\n" + chatHistory.get(i), 2);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            startActivity(new Intent(Chat.this, Chat.class));

        }
    }

    public void addMessageBox(String message, int type) {
        TextView textView = new TextView(Chat.this);
        textView.setText(message);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if (type == 1) {
            lp2.gravity = Gravity.END;
            textView.setBackgroundResource(R.mipmap.bubble_in);
            textView.getBackground().setAlpha(150);
            textView.setTextSize(18);
        } else {
            lp2.gravity = Gravity.START;
            textView.setBackgroundResource(R.mipmap.bubble_out);
            textView.getBackground().setAlpha(150);
            textView.setTextSize(18);
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}
