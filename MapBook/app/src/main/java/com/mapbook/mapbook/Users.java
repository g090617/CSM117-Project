package com.mapbook.mapbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Users extends AppCompatActivity implements View.OnClickListener {

    private Button go_back_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        this.go_back_button = (Button)this.findViewById(R.id.go_back);
        this.go_back_button.setOnClickListener(this);
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
