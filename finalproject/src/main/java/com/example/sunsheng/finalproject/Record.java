package com.example.sunsheng.finalproject;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import com.sina.cloudstorage.auth.AWSCredentials;
import com.sina.cloudstorage.auth.BasicAWSCredentials;
import com.sina.cloudstorage.services.scs.SCS;
import com.sina.cloudstorage.services.scs.SCSClient;
import com.sina.cloudstorage.services.scs.model.ObjectListing;
import com.sina.cloudstorage.services.scs.model.PutObjectResult;

import java.io.File;
import java.io.IOException;

/**
 * Created by sunsheng on 1/1/16.
 */
public class Record {
    private SCS conn;

    public void init() {
        String accessKey = "uoc0ehVj2EqsgU7IMpHS";
        String secretKey = "b3110b6ad77c06eeeb503390e18ebbe69386f1ff";
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        conn = new SCSClient(credentials);
    }

    public void listObjects() {
        ObjectListing objectListing = conn.listObjects("record");
        Log.e("obj", "" + objectListing);
    }

    public void upload(String path, int id) {
        File localFile = new File(path);
        if ( localFile.exists() ){
            conn.putObject("record", "" + id, localFile);
        } else {
            Log.e("Upload", "Failed to upload the file");
        }
    };

    // Return true if SDCard exists.
    public boolean isSDCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    private MediaRecorder recorder = null;
    // http://developer.android.com/reference/android/media/MediaRecorder.html
    // http://developer.android.com/guide/topics/media/audio-capture.html
    public void startRecording(String path) {
        try {
            if ( recorder == null ) {
                recorder = new MediaRecorder();
                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                recorder.setOutputFile(path);
                recorder.prepare();
                recorder.start();   // Recording is now started
            }
        } catch ( Exception e ) {
            Log.e("Record", "Failed to record!");
        }
    }

    public void stopRecording() {
        if ( recorder != null ) {
            recorder.stop();
            recorder.release();
            recorder = null;
        }
    }

    private MediaPlayer mediaPlayer = null;
    public void startPlaying(String path) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch ( IOException e ) {
            Log.e("Record", "Failed to play!");
        }
    }

    public void stopPlaying() {
        if ( mediaPlayer != null ) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public boolean deleteRecording(String path) {
        File file = new File(path);
        return file.delete();
    }

}
