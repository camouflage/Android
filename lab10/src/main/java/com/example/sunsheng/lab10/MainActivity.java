package com.example.sunsheng.lab10;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private LocationManager locationManager;
    private TextView latitude;
    private TextView longitude;
    private TextView address;

    private ImageView compass;

    private SensorEventListener listener = new SensorEventListener() {
        float[] accelerometerValues = new float[3];
        float[] magneticValues = new float[3];
        private float lastRotateDegree;

        @Override
        public void onSensorChanged(SensorEvent event) {
            if ( event.sensor.getType() == Sensor.TYPE_ACCELEROMETER ) {
                accelerometerValues = event.values.clone();
            } else if ( event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD ) {
                magneticValues = event.values.clone();
            }

            float[] R = new float[9];
            float[] values = new float[3];

            SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticValues);
            SensorManager.getOrientation(R, values);
            float rotateDegree = -(float)Math.toDegrees(values[0]);

            if ( Math.abs(rotateDegree - lastRotateDegree) > 1 ) {
                RotateAnimation animation = new RotateAnimation(lastRotateDegree, rotateDegree,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                animation.setFillAfter(true);
                compass.startAnimation(animation);
                lastRotateDegree = rotateDegree;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if ( location != null ) {
                showLocation(location);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    private void showLocation(Location loc) {
        if ( loc != null ) {
            latitude.setText("" + loc.getLatitude());
            longitude.setText("" + loc.getLongitude());

            List<Address> result = null;
            try {
                Geocoder gc = new Geocoder(this, Locale.getDefault());
                result = gc.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
            } catch ( Exception e ) {
                e.printStackTrace();
            }

            Log.e("add", result + "");
            if ( result.get(0) != null ) {
                Address add = result.get(0);
                address.setText(add.getAddressLine(0) + add.getAddressLine(1) + add.getFeatureName());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt = (Button) findViewById(R.id.location);
        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);
        address = (TextView) findViewById(R.id.address);

        compass = (ImageView) findViewById(R.id.compass);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(listener, magneticSensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(listener, accelerometerSensor, SensorManager.SENSOR_DELAY_GAME);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                List<String> providerList = locationManager.getProviders(true);
                String provider;
                if ( providerList.contains(LocationManager.GPS_PROVIDER) ) {
                    provider = LocationManager.GPS_PROVIDER;
                } else if ( providerList.contains(LocationManager.NETWORK_PROVIDER) ) {
                    provider = LocationManager.NETWORK_PROVIDER;
                } else {
                    Toast.makeText(MainActivity.this, "No location provider to use", Toast.LENGTH_SHORT).show();
                    return;
                }

                Location loc = locationManager.getLastKnownLocation(provider);

                if ( loc != null ) {
                    showLocation(loc);
                }

                locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if ( sensorManager != null ) {
            sensorManager.unregisterListener(listener);
        }

        if ( locationListener != null ) {
            locationManager.removeUpdates(locationListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
