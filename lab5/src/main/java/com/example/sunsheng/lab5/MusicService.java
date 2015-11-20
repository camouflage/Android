package com.example.sunsheng.lab5;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;

public class MusicService extends Service {
    public static MediaPlayer mp = new MediaPlayer();

    public MusicService() {
        try {
            mp.setDataSource(Environment.getExternalStorageDirectory() + "/data/You.mp3");
            mp.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if ( mp.isPlaying() ) {
            mp.pause();
        } else {
            mp.start();
        }
    }

    public void stop() {
        if ( mp != null ) {
            mp.stop();
            try {
            mp.prepare();
            mp.seekTo(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        mp.stop();
        mp.release();
        super.onDestroy();
    }

    public class MyBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    private IBinder binder = new MyBinder();
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
