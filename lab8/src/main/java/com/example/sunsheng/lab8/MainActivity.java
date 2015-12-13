package com.example.sunsheng.lab8;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {
    private static final int UPDATE_CONTENT = 0;
    private TextView content;
    // http://www.cnblogs.com/keyindex/articles/1822463.html
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch ( message.what ) {
                case UPDATE_CONTENT:
                    content.setText(message.obj.toString());
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button search = (Button) findViewById(R.id.search);
        content = (TextView) findViewById(R.id.content);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfo();
            }
        });
    }

    private void getInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EditText number = (EditText) findViewById(R.id.phone);
                String phone = number.getText().toString();
                // http://stackoverflow.com/questions/3134683/android-toast-in-a-thread
                if ( phone.length() < 7 ) {
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, "phone number should be at least 7 digits", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }

                String url = "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/" + "getMobileCodeInfo";
                // http://stackoverflow.com/questions/17360924/securityexception-permission-denied-missing-internet-permission
                HttpURLConnection conn = null;
                try {
                    conn = (HttpURLConnection) ((new URL(url)).openConnection());

                    conn.setRequestMethod("POST");
                    conn.setConnectTimeout(40000);
                    conn.setReadTimeout(40000);

                    // POST
                    DataOutputStream dout = new DataOutputStream(conn.getOutputStream());
                    dout.writeBytes("mobileCode=" + phone + "&userId=");

                    // GET data
                    // http://blog.csdn.net/rmn190/article/details/1492013
                    InputStream ins = conn.getInputStream();
                    BufferedReader breader = new BufferedReader(new InputStreamReader(ins));
                    StringBuilder response = new StringBuilder();

                    String line;
                    while ( (line = breader.readLine()) != null ) {
                        response.append(line);
                    }

                    Log.e("response", response.toString());

                    final Message message = new Message();
                    message.what = UPDATE_CONTENT;
                    // message.obj = parsePull(response.toString());
                    message.obj = parseDom(response.toString());

                    // http://stackoverflow.com/questions/25342706/using-handler-to-change-ui-still-only-the-original-thread-that-created-a-view
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            handler.handleMessage(message);
                        }
                    });


                } catch ( IOException e ) {
                    e.printStackTrace();
                } finally {
                    if ( conn != null ) {
                        conn.disconnect();
                    }
                }
            }
        }).start();
    }

    private String parsePull(String xml) {
        String str = "";
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(new StringReader(xml));

            int eventType = parser.getEventType();
            while ( eventType != XmlPullParser.END_DOCUMENT ) {
                switch ( eventType ) {
                    case XmlPullParser.START_TAG:
                        if ( parser.getName().compareTo("string") == 0 ) {
                            str = parser.nextText();
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch ( XmlPullParserException e ) {
            e.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        return str;
    }

    private String parseDom(String xml) {
        String str = "";
        try {
            DocumentBuilderFactory facotry = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = facotry.newDocumentBuilder();
            // http://stackoverflow.com/questions/247161/how-do-i-turn-a-string-into-a-stream-in-java
            InputStream is = new ByteArrayInputStream(xml.getBytes());
            Document d = builder.parse(is);

            Node n = d.getElementsByTagName("string").item(0);
            str = n.getFirstChild().getNodeValue();
            Log.e("info", str);

        } catch ( ParserConfigurationException e ) {
            e.printStackTrace();
        } catch ( SAXException e ) {
            e.printStackTrace();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return str;
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
