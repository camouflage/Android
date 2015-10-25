package com.example.sunsheng.lab2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

public class Extra extends AppCompatActivity {
    private TableRow tr;
    private TextView tv;
    private Button btn;
    private Button returnBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra);

        tr = (TableRow) findViewById(R.id.row1);
        returnBtn = (Button) findViewById(R.id.returnBtn);

        for ( int i = 1; i <= 3; ++i ) {
            Button btn = new Button(this);
            btn.setText(Integer.toString(i));
            btn.setId(i);
            tr.addView(btn);
        }

        tr = (TableRow) findViewById(R.id.row2);

        for ( int i = 4; i <= 6; ++i ) {
            Button btn = new Button(this);
            btn.setText(Integer.toString(i));
            btn.setId(i);
            tr.addView(btn);
        }

        tr = (TableRow) findViewById(R.id.row3);

        for ( int i = 7; i <= 9; ++i ) {
            Button btn = new Button(this);
            btn.setText(Integer.toString(i));
            btn.setId(i);
            tr.addView(btn);
        }

        View.OnClickListener btnListener = new View.OnClickListener() {
            public void onClick(View view) {
                tv = (TextView) findViewById(R.id.tv);
                tv.setText(tv.getText() + Integer.toString(view.getId()));
            }
        };

        View.OnClickListener returnBtnListener = new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        };

        for ( int i = 1; i <= 9; ++i ) {
            btn = (Button) findViewById(i);
            btn.setOnClickListener(btnListener);
        }
        returnBtn.setOnClickListener(returnBtnListener);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_extra, menu);
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
