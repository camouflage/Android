package com.example.sunsheng.lab3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.*;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] fruits = { "apple", "banana", "cherry", "coco", "kiwi", "orange", "pear",
                            "strawberry", "watermelon" };
        int[] image = { R.mipmap.apple, R.mipmap.banana, R.mipmap.cherry, R.mipmap.coco,
                      R.mipmap.kiwi, R.mipmap.orange, R.mipmap.pear, R.mipmap.strawberry,
                      R.mipmap.watermelon };

        List<Fruit> fruitList = new ArrayList<Fruit>();
        for ( int i = 0; i < fruits.length; ++i ) {
            fruitList.add(new Fruit(fruits[i], image[i]));
        }

        final List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        for ( Fruit f:fruitList ) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("image", f.getImage());
            item.put("name", f.getName());
            items.add(item);
        }

        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, items,
                R.layout.fruit, new String[] { "image", "name" },
                new int[] { R.id.imageId, R.id.name });

        ListView lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(simpleAdapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, Object> hm = (HashMap<String, Object>) parent.getAdapter().getItem(position);
                String fruit = (String) hm.get("name");
                Intent second = new Intent(MainActivity.this, Second.class);
                Bundle info = new Bundle();
                info.putString("name", fruit);
                second.putExtras(info);
                startActivity(second);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                simpleAdapter.notifyDataSetChanged();
                return true;
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
