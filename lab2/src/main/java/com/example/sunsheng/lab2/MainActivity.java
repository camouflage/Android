package com.example.sunsheng.lab2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

public class MainActivity extends AppCompatActivity {
    private EditText user;
    private EditText pass;
    private ImageButton imgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        imgBtn = (ImageButton) findViewById(R.id.imgBtn);

        LinearLayout LLayout = (LinearLayout) this.findViewById(R.id.mainLayout);
        TextView tv = new TextView(this);
        tv.setText("这是动态添加的textView");
        LLayout.addView(tv);
        /*
        View.OnClickListener imgBtnListener = new View.OnClickListener() {
            public void onClick(View view) {
                if ( user.getText().toString().equals("LeiBusi") && pass.getText().toString().equals("Halo3Q") ) {
                    user.setVisibility(View.GONE);
                    pass.setVisibility(View.GONE);
                    imgBtn.setImageDrawable(getResources().getDrawable(R.mipmap.state2));
                }
            }
        };
        */
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
