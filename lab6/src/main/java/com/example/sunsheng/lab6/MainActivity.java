package com.example.sunsheng.lab6;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    String rememberedUser = null;
    String rememberedPassword = null;
    TextView user;
    TextView password;
    MessageDigest messageDigest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (TextView) findViewById(R.id.user);
        password = (TextView) findViewById(R.id.password);
        Button register = (Button) findViewById(R.id.register);
        Button login = (Button) findViewById(R.id.login);
        final Context mContext = this;
        final SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

        if ( rememberedUser != null ) {
            user.setText(rememberedUser);
            password.setText(rememberedPassword);
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {
                    String filledUser = user.getText().toString();
                    String realPassword = sharedPreferences.getString(filledUser, "$DEFAULT$");
                    String defaultPassword = "$DEFAULT$";

                    messageDigest = MessageDigest.getInstance("MD5");
                    messageDigest.update(realPassword.getBytes());
                    byte[] realPasswordMD5 = messageDigest.digest();
                    messageDigest.update(defaultPassword.getBytes());
                    byte[] defaultPasswordMD5 = messageDigest.digest();

                    // allow multiple users
                    if ( !MessageDigest.isEqual(realPasswordMD5, defaultPasswordMD5) ) {
                        Toast toast = Toast.makeText(mContext, "Register fails(User exists)", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();
                    } else {
                        String filledPassword = password.getText().toString();
                        messageDigest.update(filledPassword.getBytes());
                        byte[] filledPasswordMD5 = messageDigest.digest();

                        editor.putString(user.getText().toString(), new String(filledPasswordMD5));
                        editor.commit();

                        Toast toast = Toast.makeText(mContext, "Register succeeds", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();
                    }

                } catch ( NoSuchAlgorithmException e ) {
                    e.printStackTrace();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filledUser = user.getText().toString();
                String filledPassword = password.getText().toString();
                String realPassword = sharedPreferences.getString(filledUser, "$DEFAULT$");
                String defaultPassword = "$DEFAULT$";

                try {
                    messageDigest = MessageDigest.getInstance("MD5");
                    messageDigest.update(defaultPassword.getBytes());
                    byte[] defaultPasswordMD5 = messageDigest.digest();
                    messageDigest.update(filledPassword.getBytes());
                    byte[] filledPasswordMD5 = messageDigest.digest();

                    if ( realPassword.equals(new String(defaultPasswordMD5))
                            || !realPassword.equals(new String(filledPasswordMD5)) ) {
                        Toast toast = Toast.makeText(mContext, "Login Error", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast.show();
                    } else {
                        Intent intent = new Intent(MainActivity.this, EditFileActivity.class);
                        startActivity(intent);
                    }

                } catch ( NoSuchAlgorithmException e ) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        CheckBox checkbox = (CheckBox) findViewById(R.id.checkbox);
        if ( !checkbox.isChecked() ) {
            user = (TextView) findViewById(R.id.user);
            password = (TextView) findViewById(R.id.password);
            user.setText("");
            password.setText("");
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
