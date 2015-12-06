package com.example.sunsheng.lab7;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    MyDatabaseHelper myDBHelper = new MyDatabaseHelper(this);
    ListView lv;
    List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
    SimpleAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button add = (Button) findViewById(R.id.add);
        lv = (ListView) findViewById(R.id.lv);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContact.class);
                startActivity(intent);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                LayoutInflater inflater = getLayoutInflater();
                final View layout = inflater.inflate(R.layout.modify, (ViewGroup) findViewById(R.id.modify));
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("请输入要修改的信息");
                builder.setView(layout);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        // http://blog.csdn.net/mal327/article/details/7011052
                        String modifyStuId = ((EditText) layout.findViewById(R.id.modifyStuId)).getText().toString();
                        String modifyName = ((EditText) layout.findViewById(R.id.modifyName)).getText().toString();
                        String modifyTel = ((EditText) layout.findViewById(R.id.modifyTel)).getText().toString();

                        if (!modifyStuId.isEmpty() && !modifyName.isEmpty()) {
                            myDBHelper.update(new Contact(modifyStuId, modifyName, modifyTel));

                            // http://stackoverflow.com/questions/1669885/what-happens-when-a-duplicate-key-is-put-into-a-hashmap
                            Map<String, Object> data = dataList.get(position);
                            data.put("stuId", modifyStuId);
                            data.put("name", modifyName);
                            data.put("tel", modifyTel);

                            sa.notifyDataSetChanged();
                        } else {
                            Toast toast = Toast.makeText(MainActivity.this, "Text cannot be empty", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                            toast.show();
                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();
            }
        });

        // http://www.cnblogs.com/salam/archive/2010/11/15/1877512.html
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Do you want to delete?");
                builder.setTitle("Attention");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        myDBHelper.delete(new Contact(
                                ((TextView) view.findViewById(R.id.stuId)).getText().toString(),
                                ((TextView) view.findViewById(R.id.name)).getText().toString(),
                                ((TextView) view.findViewById(R.id.tel)).getText().toString()
                        ));

                        dataList.remove(position);
                        sa.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.create().show();

                return true;
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

        lv = (ListView) findViewById(R.id.lv);

        setData(dataList);
        sa = new SimpleAdapter(this, dataList, R.layout.contact,
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
