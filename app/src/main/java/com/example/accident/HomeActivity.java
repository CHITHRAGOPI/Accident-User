package com.example.accident;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;


import com.example.accident.model.accident.AccidentBase;
import com.example.accident.model.request.RequestBase;
import com.example.accident.model.request.RequestItem;
import com.example.accident.utils.GlobalPreference;

import android.util.Log;

import com.example.accident.utils.GpsTrackers;
import com.example.accident.utils.ShakeService;
import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GlobalPreference mGlobalPreference;
    private ApiInterface mApiInterface;
    public static final String TAG = HomeActivity.class.getSimpleName();
    private GoogleMap mMap;

    private  Boolean isHelpRequest=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        mGlobalPreference = new GlobalPreference(getApplicationContext());
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        startService(new Intent(HomeActivity.this, ShakeService.class));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        isHelpRequest=true;

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);


       //   startService(new Intent(HomeActivity.this,LocationService.class));
    }
//

    public void payment(View view){
        startActivity(new Intent(HomeActivity.this,PaymentDueActivity.class));
    }


    public void accident(View view){
        Log.d(TAG, "accident: "+isHelpRequest);
       // if(isHelpRequest){
            insertAccident();

       // }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("location-event"));
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String lat = intent.getStringExtra("lat");
            String lng = intent.getStringExtra("lng");
            //updateLocation(lat, lng);
            Log.d("receiver", "Got message: " + lat);
        }
    };

//
//    public void updateLocation(String lat, String lng) {
//        mGlobalPreference = new GlobalPreference(getApplicationContext());
//        Log.d(TAG, "updateLocation: " + mGlobalPreference.RetrieveIp());
//        Log.d(TAG, "updateLocation: " + mGlobalPreference.getPassword());
//        mApiInterface = ApiClient.getRetrofit(mGlobalPreference.RetrieveIp()).create(ApiInterface.class);
//        Call<LocationUpdate> call = mApiInterface.updateLocation(lat, lng, mGlobalPreference.getConductorId());
//
//        call.enqueue(new Callback<LocationUpdate>() {
//            @Override
//            public void onResponse(Call<LocationUpdate> call, Response<LocationUpdate> response) {
//                Log.d(TAG, "onResponse: " + response.body().isSuccess());
//            }
//
//            @Override
//            public void onFailure(Call<LocationUpdate> call, Throwable t) {
//                Log.d(TAG, "onFailure: " + t.getMessage());
//            }
//        });
//
//    }
//
    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
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
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            mGlobalPreference.setLoginStatus(false);
            startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            finish();
            return true;
        }

        if (id == R.id.cancelRequest) {
            getLastRequest();

          //  callCancelApi();

        }


        return super.onOptionsItemSelected(item);
    }

    public void getLastRequest(){

        mApiInterface = ApiClient.getRetrofit(mGlobalPreference.RetrieveIp()).create(ApiInterface.class);
        Call<RequestBase> mAccidentBaseCall=mApiInterface.getRequest(mGlobalPreference.getID());
        mAccidentBaseCall.enqueue(new Callback<RequestBase>() {
            @Override
            public void onResponse(Call<RequestBase> call, Response<RequestBase> response) {
                RequestItem mRequestItem=response.body().getData().get(response.body().getData().size()-1);
                if(mRequestItem.getStatus().equals("1")){
                    Toast.makeText(HomeActivity.this, "You cannot cancel request The Driver has accepted the request ", Toast.LENGTH_SHORT).show();

                }else{
                    callCancelApi(mRequestItem);
                }

            }

            @Override
            public void onFailure(Call<RequestBase> call, Throwable t) {

            }
        });



    }

    public void callCancelApi(RequestItem mRequestItem){
        mApiInterface = ApiClient.getRetrofit(mGlobalPreference.RetrieveIp()).create(ApiInterface.class);
        Call<AccidentBase> mAccidentBaseCall=mApiInterface.cancelRequest(mRequestItem.getId());
        mAccidentBaseCall.enqueue(new Callback<AccidentBase>() {
            @Override
            public void onResponse(Call<AccidentBase> call, Response<AccidentBase> response) {
                if(response.isSuccessful()){
                    Toast.makeText(HomeActivity.this, "Request Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<AccidentBase> call, Throwable t) {

                Toast.makeText(HomeActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void insertAccident(){
        mApiInterface = ApiClient.getRetrofit(mGlobalPreference.RetrieveIp()).create(ApiInterface.class);
        GpsTrackers gpsTrackers=new GpsTrackers(this);
        Call<AccidentBase> mAccidentBaseCall=
                mApiInterface.insertAccident(mGlobalPreference.getImei(),mGlobalPreference.getID(),String.valueOf(gpsTrackers.getLatitude()),String.valueOf(gpsTrackers.getLongitude()),mGlobalPreference.getVehicle());
        Toast.makeText(HomeActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

        mAccidentBaseCall.enqueue(new Callback<AccidentBase>() {
            @Override
            public void onResponse(Call<AccidentBase> call, Response<AccidentBase> response) {
                Toast.makeText(HomeActivity.this, "Response"+response, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onResponse: "+response);
                isHelpRequest=true;
                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Request Send", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<AccidentBase> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                Log.d(TAG, "onFailure: "+t.getMessage());
                Toast.makeText(HomeActivity.this, "Error"+t.getMessage(), Toast.LENGTH_SHORT).show();
                isHelpRequest=true;

            }
        });

    }




    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        GpsTrackers gpsTracker=new GpsTrackers(this);
     //   mMap.setMyLocationEnabled(true);
        LatLng  mLatLng = new LatLng(gpsTracker.getLatitude(),
                    gpsTracker.getLongitude());

        mMap.addMarker(new MarkerOptions().position(mLatLng).title("Current Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng,16));
    }


}
