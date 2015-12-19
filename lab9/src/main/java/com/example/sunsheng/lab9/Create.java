package com.example.sunsheng.lab9;

import android.app.ProgressDialog;
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

public class Create extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button create = (Button) findViewById(R.id.create);
        img = (ImageView) findViewById(R.id.codeImg);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) findViewById(R.id.code);
                code = et.getText().toString();

                progressDialog = ProgressDialog.show(Create.this, "Requesting", "Requesting", true);
                new Thread(new Download()).start();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create, menu);
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
