package com.mieczkowskidev.voicerecorder;

import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private MediaRecorder recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepareMediaRecorder();
    }

    private void prepareMediaRecorder() {
        Log.d(TAG, "prepareMediaRecorder()");

        recorder = new MediaRecorder();

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);


        String timeStamp = new SimpleDateFormat("HH_mm_dd-MM", Locale.ENGLISH).format(new Date());
        String fileName = "/CALL_" + timeStamp + ".mp4";

        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);

        Log.d(TAG, "prepareMediaRecorder: " + storageDir.getAbsolutePath());
        recorder.setOutputFile(storageDir.getAbsolutePath() + fileName);
        Log.d(TAG, "prepareMediaRecorder: " + storageDir.getAbsolutePath() + fileName);

        recorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
            @Override
            public void onError(MediaRecorder mediaRecorder, int i, int i1) {
                Log.e(TAG, "onError() called with: " + "mediaRecorder = [" + mediaRecorder + "], i = [" + i + "], i1 = [" + i1 + "]");
            }
        });
        recorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
            @Override
            public void onInfo(MediaRecorder mediaRecorder, int i, int i1) {
                Log.d(TAG, "onInfo() called with: " + "mediaRecorder = [" + mediaRecorder + "], i = [" + i + "], i1 = [" + i1 + "]");
            }
        });

        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TAG, "onPause()");
        if (recorder != null) {
            recorder.stop();
        }
    }
}
