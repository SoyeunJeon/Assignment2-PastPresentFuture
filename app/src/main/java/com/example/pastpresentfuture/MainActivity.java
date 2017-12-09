package com.example.pastpresentfuture;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor tempSensor;

    @RequiresApi(api = Build.VERSION_CODES.N) // Using Date will be required specific api version or higher
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Date curDate = new Date();
        String date = DateFormat.getDateTimeInstance().format(curDate);
        TextView today = (TextView) findViewById(R.id.today);
        today.setText(date);

        // Initialize temperature sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
    }

    // Get current temperature and display with corresponding message on degree
    @Override
    public void onSensorChanged(SensorEvent event) {
        float temp = event.values[0];
        Float tempFloat = new Float(temp);
        TextView temperature = (TextView) findViewById(R.id.temperature);
        temperature.setText(tempFloat.toString());
        TextView tempAlert = (TextView) findViewById(R.id.tempAlert);
        if (tempFloat < 20) {
            tempAlert.setText("Be warm!");
        } else if (tempFloat >= 20 && tempFloat < 30) {
            tempAlert.setText("Good to work!");
        } else if(tempFloat >= 30) {
            tempAlert.setText("Stay cool!");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }


    // Button actions
    public void goToHistory(View view) {
        Intent intent = new Intent(this, MyHistory.class);
        startActivity(intent);
    }
    public void goToTodayList(View view) {
        Intent intent = new Intent(this, ToDoToday.class);
        startActivity(intent);
    }
    public void goToFutureEvent(View view) {
        Intent intent = new Intent(this, FutureEvent.class);
        startActivity(intent);
    }


    // Logs
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("main activity tag", "now running onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("main activity tag", "now running onResume");
        sensorManager.registerListener(this,tempSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.i("main activity tag", "now running onPause");
        sensorManager.unregisterListener(this);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("main activity tag", "now running onRestart");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("main activity tag", "now running onStop");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("main activity tag", "now running onDestroy");
    }

}
//34:62:55:7E:D8:AF:C6:37:CA:66:1D:08:98:28:C3:22:70:9D:DB:15