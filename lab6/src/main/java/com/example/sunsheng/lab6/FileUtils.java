package com.example.sunsheng.lab6;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by sunsheng on 11/28/15.
 */
public class FileUtils {
    public void saveContent(Context context, String fileName, String fileText) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(fileText.getBytes());
            fos.close();

            Toast.makeText(context, "Save content succeeds", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(context, "Save content fails", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    public String readContent(Context context, String fileName) {
        try {
            FileInputStream fis = context.openFileInput(fileName);
            byte[] contents = new byte[fis.available()];
            fis.read(contents);
            fis.close();

            Toast.makeText(context, "Read content succeeds", Toast.LENGTH_SHORT).show();

            return new String(contents);
        } catch (IOException e) {
            Toast.makeText(context, "Read content fails", Toast.LENGTH_SHORT).show();
            e.printStackTrace();

            return new String("");
        }
    }

    public void deleteFile(Context context, String fileName) {
        context.deleteFile(fileName);
        Toast.makeText(context, "Delete content succeeds", Toast.LENGTH_SHORT).show();
    }
}
