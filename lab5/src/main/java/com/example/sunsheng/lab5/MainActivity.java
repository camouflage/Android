package com.example.sunsheng.lab5;

import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.content.*;

import java.text.SimpleDateFormat;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private MusicService ms;
    private SimpleDateFormat date = new SimpleDateFormat("m:ss");
    private SeekBar sb;
    private TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button play = (Button) findViewById(R.id.play);
        Button stop = (Button) findViewById(R.id.stop);
        Button quit = (Button) findViewById(R.id.quit);
        // http://stackoverflow.com/questions/17002666/unable-to-instantiate-activity-componentinfo-error-checked-manifest
        sb = (SeekBar) findViewById(R.id.sb);
        time = (TextView) findViewById(R.id.time);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ms.play();
                TextView tv = (TextView) findViewById(R.id.tv);

                if ( ms.mp.isPlaying() ) {
                    tv.setText("Playing");
                } else {
                    tv.setText("Paused");
                }

                time.setText(date.format(ms.mp.getCurrentPosition()) + "/" + date.format(ms.mp.getDuration()));
                sb.setProgress(ms.mp.getCurrentPosition());
                sb.setMax(ms.mp.getDuration());
                handler.post(r);
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ms.stop();
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(r);
                unbindService(sc);
                try {
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Intent intent = new Intent(MainActivity.this, MusicService.class);
        bindService(intent, sc, this.BIND_AUTO_CREATE);
    }

    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ms = ((MusicService.MyBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            ms = null;
        }
    };

    private Handler handler = new Handler();
    // http://www.scriptscoop.net/t/b9995e851693/java-android-updating-seekbar-with-handler-and-runnable.html
    private Runnable r = new Runnable() {
        @Override
        public void run() {
            time.setText(date.format(ms.mp.getCurrentPosition()) + "/" + date.format(ms.mp.getDuration()));
            sb.setProgress(ms.mp.getCurrentPosition());
            sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        ms.mp.seekTo(seekBar.getProgress());
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            handler.postDelayed(r, 1000);
        }
    };

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
