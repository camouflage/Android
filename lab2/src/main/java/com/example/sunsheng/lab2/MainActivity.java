package com.example.sunsheng.lab2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.content.*;

public class MainActivity extends AppCompatActivity {
    private EditText user;
    private EditText pass;
    private ImageButton imgBtn;
    private LinearLayout subLayout;
    private Context mContext;
    private Button btn;
    private Button extensionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        imgBtn = (ImageButton) findViewById(R.id.imgBtn);
        subLayout = (LinearLayout) this.findViewById(R.id.subLayout);
        mContext = this;
        btn = (Button) findViewById(R.id.btn);
        extensionBtn = (Button) findViewById(R.id.extensionBtn);

        // Generate textview dynamically
        TextView tv = new TextView(this);
        tv.setText("这是动态添加的textView");
        subLayout.addView(tv);

        View.OnClickListener imgBtnListener = new View.OnClickListener() {
            public void onClick(View view) {
                if ( user.getText().toString().equals("LeiBusi") && pass.getText().toString().equals("Halo3Q") ) {
                    user.setVisibility(View.GONE);
                    pass.setVisibility(View.GONE);
                    imgBtn.setImageResource(R.mipmap.state2);
                } else {
                    user.setText("LeiBusi");
                    pass.setText("");
                    pass.setHint("帐号或密码错误");
                    pass.requestFocus();

                    Toast toast = Toast.makeText(mContext, "密码错误", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                }
            }
        };

        View.OnLongClickListener imgBtnLongListener = new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                TextView tv = new TextView(mContext);
                tv.setText("这是动态添加的textView");
                subLayout.addView(tv);
                return true;
            }
        };

        View.OnClickListener btnListener = new View.OnClickListener() {
            public void onClick(View view) {
                user.setText("");
                pass.setText("");
                pass.setHint("请输入密码");
                user.setVisibility(View.VISIBLE);
                pass.setVisibility(View.VISIBLE);
                user.requestFocus();
                imgBtn.setImageResource(R.mipmap.state1);

                if ( subLayout.getChildCount() > 0 ) {
                    subLayout.removeAllViews();
                }

                TextView tv = new TextView(mContext);
                tv.setText("这是动态添加的textView");
                subLayout.addView(tv);
            }
        };

        View.OnClickListener extensionBtnListener = new View.OnClickListener() {
            public void onClick(View view) {
                Intent Extra = new Intent(MainActivity.this, Extra.class);
                startActivity(Extra);
            }
        };

        imgBtn.setOnClickListener(imgBtnListener);
        imgBtn.setOnLongClickListener(imgBtnLongListener);
        btn.setOnClickListener(btnListener);
        extensionBtn.setOnClickListener(extensionBtnListener);
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
