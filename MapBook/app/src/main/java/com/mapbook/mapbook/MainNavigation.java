package com.mapbook.mapbook;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainNavigation extends AppCompatActivity
        implements GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnInfoWindowLongClickListener, GoogleMap.OnInfoWindowClickListener,OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;
    private BookInfo bookCreated;
    private FirebaseAuth mAuth;
    private HashMap<Marker, BookInfo> markerBookInfoHashMap = new HashMap<>();
    private ArrayList<BookInfo> bookInfos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_main_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_main_navigation);
        TextView headerName = (TextView) navHeaderView.findViewById(R.id.textView);
        headerName.setText(mAuth.getCurrentUser().getEmail());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle view_navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_add) {
            Intent intent = new Intent(this, CreateActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_my_sale) {
            Intent intent = new Intent(this, ViewSaleActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_users) {
            Intent intent = new Intent(this, Users.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        enableLocation();

//        Log.i("enter","before");
//        Intent intent = getIntent();
//        String location = intent.getStringExtra("location");
//        if (location != null) {
//            Log.i("enter","after");
//            Log.i("enter", location);
//            List<Address> addressList = null;
//            if (location != null || !location.equals("")) {
//                Geocoder geocoder = new Geocoder(this);
//                try {
//                    addressList = geocoder.getFromLocationName(location, 1);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Log.i("address", String.valueOf(addressList));
//                Address address = addressList.get(0);
//
//                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                mMap.addMarker(new MarkerOptions().position(latLng).title("click here to get book information!"));
//                mMap.setOnInfoWindowClickListener(this);
//                mMap.setOnInfoWindowLongClickListener(this);
//                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//            }
//        }
        markerBookInfoHashMap.clear();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            bookCreated = intent.getExtras().getParcelable("bookCreated");
            if (bookCreated != null) {
                LatLng latLng = new LatLng(Double.parseDouble(bookCreated.latitude), Double.parseDouble(bookCreated.longtitude));
                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title("click here to check book information"));
                mMap.setOnInfoWindowClickListener(this);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
                markerBookInfoHashMap.put(marker, bookCreated);
            }
        }

        String zip = intent.getStringExtra("zip");




        if (zip != null) {
            String title = intent.getStringExtra("title");
            if (title.equals("")) {
                getBookInfoByZipCode(zip);
            }
        }
    }


    public void getBookInfoByBookID(String bookID){
        final DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(
                "BookDB/" + bookID);
        final MainNavigation context = this;
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
                LatLng latLng = new LatLng(Double.parseDouble(value.latitude), Double.parseDouble(value.longtitude));
                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title("click here to check book information"));
                mMap.setOnInfoWindowClickListener(context);
                mMap.setOnInfoWindowLongClickListener(context);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
                bookInfos.add(value);
                markerBookInfoHashMap.put(marker, value);
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
        //Log.w(TAG, "step into get book info by zip code.");
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ZipCodeBook value = new ZipCodeBook();
                value.zipBookMap = (HashMap)dataSnapshot.getValue();
                //Log.d(TAG, "Book ID in zip code " + zipCode + " are " + value.toString());
                Log.i("ttttag", String.valueOf(value.zipBookMap.keySet().size()));
                for(final String key : value.zipBookMap.keySet()){
                    //Log.w(TAG, "Print string key here, see if we actually have the value");
                    //Log.w(TAG, key);
                    getBookInfoByBookID(key);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    private void enableLocation() {
        if (!(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        } else {
            //Log.i("jin","jin1");
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != 123) {
            return;
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            enableLocation();
        }
    }
    public void onInfoWindowClick(Marker marker) {
        if(marker != null)
        Log.w(TAG, "marker is not null!");


        BookInfo bookInfo = markerBookInfoHashMap.get(marker);
        if(bookInfo != null){
        //    Log.w(TAG, "bookInfo is not null!");
        //    Log.w(TAG, "bookInfo longtitude here");
        //    Log.w(TAG, bookInfo.longtitude);

        //    Log.w(TAG, "bookInfo latitude here");
        //    Log.w(TAG, bookInfo.latitude);

       //     Log.w(TAG, "bookInfo title here");
       //     Log.w(TAG, bookInfo.title);
       //     Log.w(TAG, "bookInfo author here");
      //      Log.w(TAG, bookInfo.author);
      //      Log.w(TAG, "bookInfo isbn here");
       //     Log.w(TAG, bookInfo.isbn);
      //      Log.w(TAG, bookInfo.price);
            Toast.makeText(this,
                    "        Title: " + bookInfo.title + "\n" +
                            "        Author: " + bookInfo.author + "\n" +
                            "        ISBN: " + bookInfo.isbn + "\n" +
                            "        Subject: " + bookInfo.subject + "\n" +
                            "        Price: " + bookInfo.price + "\n",
                    Toast.LENGTH_LONG).show();

        }

        else
            Log.w(TAG, "bookInfo is null!");

    }
    public void onInfoWindowLongClick(Marker marker) {
        Intent intent = new Intent(this, MainNavigation.class);
        startActivity(intent);
    }

}
