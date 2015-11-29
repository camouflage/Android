package com.example.sunsheng.lab6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

public class EditFileActivity extends AppCompatActivity {
    EditText fileContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_file);

        Button save = (Button) findViewById(R.id.save);
        Button read = (Button) findViewById(R.id.read);
        Button delete = (Button) findViewById(R.id.delete);
        final AutoCompleteTextView fileName = (AutoCompleteTextView) findViewById(R.id.name);
        final FileUtils fileUtils = new FileUtils();
        fileContent = (EditText) findViewById(R.id.content);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileUtils.saveContent(EditFileActivity.this, fileName.getText().toString(),
                        fileContent.getText().toString());

                fileName.setAdapter(new ArrayAdapter<String>(EditFileActivity.this,
                        android.R.layout.simple_dropdown_item_1line, EditFileActivity.this.fileList()));
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content= fileUtils.readContent(EditFileActivity.this, fileName.getText().toString());
                fileContent.setText(content);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fileUtils.deleteFile(EditFileActivity.this, fileName.getText().toString());

                fileName.setAdapter(new ArrayAdapter<String>(EditFileActivity.this,
                        android.R.layout.simple_dropdown_item_1line, EditFileActivity.this.fileList()));

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_file, menu);
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
