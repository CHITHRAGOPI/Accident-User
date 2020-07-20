package com.example.accident.utils;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.accident.ApiClient;
import com.example.accident.ApiInterface;
import com.example.accident.model.accident.AccidentBase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShakeService extends Service implements SensorEventListener {

    SensorManager senSensorManager;
    Sensor senAccelerometer;
    long lastUpdate = 0;
    float last_x, last_y, last_z;
    int SHAKE_THRESHOLD = 2000;
    Activity activity;
    private static final String TAG = "ShakeService";
    GpsTrackers gpsTrackers;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private ApiInterface mApiInterface;
    private boolean isInsert=true;

    public ShakeService() {

    }

    GlobalPreference mGlobalPreference;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub

        Toast.makeText(getApplicationContext(), "service", Toast.LENGTH_LONG).show();
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        senSensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mGlobalPreference = new GlobalPreference(this);
        gpsTrackers = new GpsTrackers(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;
                float accident = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;
                Log.d(TAG, "onSensorChanged: " + accident);
                if (accident> SHAKE_THRESHOLD) {

                    if(isInsert) {
                        insertAccident();
                       isInsert=false;
                    }

                }

            }
        }
    }
      private void insertAccident(){
          mApiInterface = ApiClient.getRetrofit(mGlobalPreference.RetrieveIp()).create(ApiInterface.class);
          GpsTrackers gpsTrackers=new GpsTrackers(this);
        Call<AccidentBase> mAccidentBaseCall=
                mApiInterface.insertAccident(mGlobalPreference.getImei(),mGlobalPreference.getID(),String.valueOf(gpsTrackers.getLatitude()),String.valueOf(gpsTrackers.getLongitude())
                ,mGlobalPreference.getVehicle()
                );
          mAccidentBaseCall.enqueue(new Callback<AccidentBase>() {
              @Override
              public void onResponse(Call<AccidentBase> call, Response<AccidentBase> response) {
                  Log.d(TAG, "onResponse: accident"+response.body().isSuccess());
                  isInsert=true;
                  if(response.isSuccessful()){
                      Toast.makeText(getApplicationContext(), "Accident details Send", Toast.LENGTH_LONG).show();
                  }

              }

              @Override
              public void onFailure(Call<AccidentBase> call, Throwable t) {
                  Log.d(TAG, "onFailure: "+t.getMessage());
                  isInsert=true;
              }
          });

      }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
