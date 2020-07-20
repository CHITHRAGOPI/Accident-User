package com.example.accident;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.accident.utils.GlobalPreference;
import com.google.firebase.FirebaseApp;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class SplashActivity extends AppCompatActivity {

    private  static  final int SPLASH_TIME_OUT=1000;
    GlobalPreference  mGlobalPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mGlobalPreference=new GlobalPreference(this);
        mGlobalPreference.addIp("myclouddata.space");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mGlobalPreference.getLoginStatus()){
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                }
                else{
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }

                SplashActivity.this.finish();
            }
        },SPLASH_TIME_OUT);

    }
}
