package com.mapbook.mapbook;
import java.util.ArrayList;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.content.Intent;
import static android.content.ContentValues.TAG;
public class DetailedBookInfoActivity extends AppCompatActivity {
    private BookInfo bookCreated;
    private String zipCode;
    private String status;
    private String sellerMail;
    private String markerID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_book_info);
        TextView lblTitle=(TextView)findViewById(R.id.lblTitle);
        Intent intent = getIntent();
        zipCode = intent.getStringExtra("zipCode");
        markerID = intent.getStringExtra("markerId");
        // TO-DO
        sellerMail = "1@qq.com";
        String display = "        Title: " + intent.getStringExtra("title") + "\n" +
                "        Author: " + intent.getStringExtra("author") + "\n" +
                "        ISBN: " + intent.getStringExtra("isbn")  + "\n" +
                "        Subject: " + intent.getStringExtra("subject")  + "\n" +
                "        Price: " + intent.getStringExtra("price")  ;

        lblTitle.setText(display);

    }

    public void onCancelButton(View v) {
        Intent intent = new Intent(this, MainNavigation.class);
        intent.putExtra("zip", zipCode);
        startActivity(intent);
    }

    public void onBuyButton(View v) {
        Intent intent = new Intent(this, MainNavigation.class);
        intent.putExtra("bookStatusChanges", markerID);
        intent.putExtra("zip", zipCode);
        Log.w(TAG, "send book status change signal");
        startActivity(intent);
    }
    public void onContactSellerButton(View v) {

        Intent intent = new Intent(this, Chat.class);
        intent.putExtra("sellerMail", sellerMail);
        startActivity(intent);
    }
}
