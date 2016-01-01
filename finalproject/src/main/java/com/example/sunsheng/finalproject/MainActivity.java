package com.example.sunsheng.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Record record = new Record();
    String fileName;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        record.init();
        /*
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("before", "before");
                    record.listObjects();
                    Log.e("after", "after");
                } catch ( Exception e ) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        */


        if ( !record.isSDCardExist() ) {
            Log.e("Record", "SD card does not exist!");
        }

        Button start = (Button) findViewById(R.id.start);
        Button stop = (Button) findViewById(R.id.stop);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileName = MainActivity.this.getExternalFilesDir(null).toString();
                fileName += "/" + id + ".3gp";
                record.startRecording(fileName);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                record.stopRecording();
                record.startPlaying(fileName);

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e("before", "before");
                            record.upload(fileName, id);
                            Log.e("after", "after");
                        } catch ( Exception e ) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();

                id += 1;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        record.stopRecording();
        record.stopPlaying();
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
