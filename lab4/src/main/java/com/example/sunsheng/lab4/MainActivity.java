package com.example.sunsheng.lab4;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.content.*;


public class MainActivity extends AppCompatActivity {
    private Boolean flag;
    private Button btn0;
    private Button send;
    private Button send1;
    private MyBroadcastReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flag = false;
        btn0 = (Button) findViewById(R.id.register);
        send = (Button) findViewById(R.id.send);
        send1 = (Button) findViewById(R.id.send1);
        myReceiver = new MyBroadcastReceiver();

        View.OnClickListener btn0Listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( flag == false ) {
                    btn0.setText("Unregister Broadcast");
                    flag = true;
                    IntentFilter dynamicFilter = new IntentFilter();
                    dynamicFilter.addAction("dynamicReceiver");
                    registerReceiver(myReceiver, dynamicFilter);
                } else {
                    btn0.setText("Register Broadcast");
                    flag = false;
                    unregisterReceiver(myReceiver);
                }
            }
        };

        View.OnClickListener sendListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("dynamicReceiver");
                intent.putExtra("message", ((EditText) findViewById(R.id.et)).getText().toString());
                sendBroadcast(intent);
            }
        };

        View.OnClickListener sendListener1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("staticReceiver");
                intent.putExtra("message", ((EditText) findViewById(R.id.et)).getText().toString());
                sendBroadcast(intent);
            }
        };

        btn0.setOnClickListener(btn0Listener);
        send.setOnClickListener(sendListener);
        send1.setOnClickListener(sendListener1);
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
