package com.example.sunsheng.lab7;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    MyDatabaseHelper myDBHelper = new MyDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button add = (Button) findViewById(R.id.add);
        ListView lv = (ListView) findViewById(R.id.lv);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContact.class);
                startActivity(intent);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                
            }
        });
    }

    public void setData(List<Map<String, Object>> dataList) {
        dataList.clear();
        Cursor c = myDBHelper.query();
        while ( c.moveToNext() ) {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("stuId", c.getString(c.getColumnIndex("_stuId")));
            data.put("name", c.getString(c.getColumnIndex("_name")));
            data.put("tel", c.getString(c.getColumnIndex("_tel")));
            dataList.add(data);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        ListView lv = (ListView) findViewById(R.id.lv);
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();

        setData(dataList);
        SimpleAdapter sa = new SimpleAdapter(this, dataList, R.layout.contact,
                new String[] { "stuId", "name", "tel" }, new int[] { R.id.stuId, R.id.name, R.id.tel} );
        lv.setAdapter(sa);
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
