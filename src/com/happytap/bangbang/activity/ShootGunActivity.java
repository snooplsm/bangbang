package com.happytap.bangbang.activity;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.happytap.bangbang.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by IntelliJ IDEA.
 * User: rgravener
 * Date: 1/18/11
 * Time: 3:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class ShootGunActivity extends Activity implements SensorEventListener, View.OnTouchListener {

    private SensorManager sensorManager;
    private Sensor gyroscope;

    private SensorEvent lastEvent;

    private FileOutputStream fos;
    private FileOutputStream canShoot;

    private MediaPlayer[] gunShot;
    private int gunShotIndex = 1;
    private int maxGunShots = 3;

    private MediaPlayer bulletWhizBy;
    private MediaPlayer bulletHitsGround;
    private MediaPlayer bulletHitsSelf;


    private TextView x;
    private TextView y;
    private TextView z;

    public ShootGunActivity() {
    }



    protected void onCreate(Bundle savedInstance) {
        setTheme(android.R.style.Theme_Light_NoTitleBar);
        super.onCreate(savedInstance);
        setContentView(R.layout.shoot_gun);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        registerListeners();
        View view = findViewById(R.id.shoot_gun);
        view.setOnTouchListener(this);
        x = (TextView)findViewById(R.id.x);
        y = (TextView)findViewById(R.id.y);
        z = (TextView)findViewById(R.id.z);

        try {
            fos = getApplication().openFileOutput("coordinates.csv", Context.MODE_WORLD_READABLE);
            canShoot = getApplication().openFileOutput("canshoot.csv", Context.MODE_WORLD_READABLE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        makeGunShots();
        bulletWhizBy = MediaPlayer.create(this, R.raw.bullet_whiz);
    }

    protected void makeGunShots() {
        for(int i = 0; i < maxGunShots; i++) {
            gunShot[i] = MediaPlayer.create(this, R.raw.ps45);
            gunShot[i].prepareAsync();
        }
    }

    protected void onResume() {
         super.onResume();
           registerListeners();
     }

    private void registerListeners() {
         sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_UI);
    }

     protected void onPause() {
         super.onPause();
         sensorManager.unregisterListener(this);
     }

     public void onAccuracyChanged(Sensor sensor, int accuracy) {
     }

     public void onSensorChanged(SensorEvent event) {
         lastEvent = event;
         float[] xyz = event.values;
         float x = xyz[0];
         float y = xyz[1];
         float z = xyz[2];
         this.x.setText(Float.toString(x));
         this.y.setText(Float.toString(y));
         this.z.setText(Float.toString(z));
         String f = event.timestamp+","+x + "," + y + "," + z+"\n";
         try {fos.write(f.getBytes());
            fos.flush();
         } catch (Exception e) {
             e.printStackTrace();;
         }
     }

    public boolean onTouch(View view, MotionEvent motionEvent) {
         float[] xyz = lastEvent.values;
         float x = xyz[0];
         float y = xyz[1];
         float z = xyz[2];
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                boolean played = false;
                gunShotIndex+=1;
                gunShot[gunShotIndex%maxGunShots].start();
                return null;
            }
        }.execute();
        String f = lastEvent.timestamp+","+x + "," + y + "," + z+"\n";
         try {canShoot.write(f.getBytes());
            canShoot.flush();
         } catch (Exception e) {
             e.printStackTrace();;
         }
        return false;
    }
}
