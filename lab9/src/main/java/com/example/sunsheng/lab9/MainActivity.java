package com.example.sunsheng.lab9;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

//http://stackoverflow.com/questions/11590382/android-view-windowleaked
// For android.view.WindowLeaked

public class MainActivity extends AppCompatActivity {
    private static ImageView img;
    public static String code;
    public static ProgressDialog progressDialog;
    private static final int UPDATE_CONTENT = 0;

    public static Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch ( message.what ) {
                case UPDATE_CONTENT:
                    byte[] data = Base64.decode((message.obj.toString()).getBytes(), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    img.setImageBitmap(bitmap);
                    break;
                default:
                    break;
            }
        }
    };

    // http://blog.csdn.net/xiaoyukid/article/details/6041697
    public static String getRandomString() {
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer b = new StringBuffer();
        for ( int i = 0; i < 8; i++ ) {
            int number = random.nextInt(base.length());
            b.append(base.charAt(number));
        }
        return b.toString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newCode = (Button) findViewById(R.id.newCode);
        Button confirm = (Button) findViewById(R.id.confirm);
        Button refresh = (Button) findViewById(R.id.refresh);
        img = (ImageView) findViewById(R.id.img);
        final EditText et = (EditText) findViewById(R.id.et);

        code = getRandomString();
        progressDialog = ProgressDialog.show(MainActivity.this, "Please wait", "Loading code picture", true);

        new Thread(new AutoCode()).start();

        newCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, Create.class);
                startActivity(i);
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = getRandomString();
                progressDialog = ProgressDialog.show(MainActivity.this, "Please wait", "Loading code picture", true);
                new Thread(new AutoCode()).start();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = et.getText().toString();
                if ( input.equals(code) ) {
                    Toast.makeText(MainActivity.this, "Correct code!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Wrong code!", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
