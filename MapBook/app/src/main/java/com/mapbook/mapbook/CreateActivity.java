package com.mapbook.mapbook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CreateActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private RequestAccess requestAccessDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            final EditText titleField = (EditText)findViewById(R.id.editText2);
            String title = titleField.getText().toString();
            final EditText nameField = (EditText)findViewById(R.id.editText3);
            String name = nameField.getText().toString();
            final EditText isbnField = (EditText)findViewById(R.id.editText5);
            String isbn  = isbnField.getText().toString();
            final EditText pubField = (EditText)findViewById(R.id.editText6);
            String pub = pubField.getText().toString();
            final EditText priceField = (EditText)findViewById(R.id.editText8);
            String price = priceField.getText().toString();
            final Spinner subjectSpinner = (Spinner)findViewById(R.id.spinner);
            String subject = subjectSpinner.getSelectedItem().toString();
                mAuth = FirebaseAuth.getInstance();
                mDatabase = FirebaseDatabase.getInstance().getReference();

            requestAccessDB = new RequestAccess();
            requestAccessDB.addBookToUser(mAuth.getCurrentUser().getUid(), mAuth.getCurrentUser().getUid(),
                                    title, name, isbn,
                                    pub, subject, price,"BUY");

                Snackbar.make(view, "Added to database", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();


            }
        });
    }


}

