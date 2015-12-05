package com.example.sunsheng.lab7;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        Button ok = (Button) findViewById(R.id.ok);
        final Context mContext = this;

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText stuId = (EditText) findViewById(R.id.addStuId);
                EditText name = (EditText) findViewById(R.id.addName);
                EditText tel = (EditText) findViewById(R.id.addTel);
                if ( !stuId.getText().toString().isEmpty() && !name.getText().toString().isEmpty() ) {
                    MyDatabaseHelper dbHelper = new MyDatabaseHelper(mContext);
                    Contact newContact = new Contact(stuId.getText().toString(), name.getText().toString(),
                            tel.getText().toString());
                    dbHelper.insert(newContact);
                    finish();
                } else {
                    Toast toast = Toast.makeText(mContext, "Text cannot be empty", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_contact, menu);
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
