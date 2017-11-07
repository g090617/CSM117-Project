package com.mapbook.mapbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchActivity extends AppCompatActivity {


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
        Intent intent = new Intent(this, MainNavigation.class);
        EditText locationSearch = (EditText) findViewById(R.id.editZipcode);
        String location = locationSearch.getText().toString();
        intent.putExtra("location", location);
        startActivity(intent);
    }

}
