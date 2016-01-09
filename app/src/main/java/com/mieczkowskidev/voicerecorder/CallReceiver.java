package com.mieczkowskidev.voicerecorder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CallReceiver extends BroadcastReceiver {

    public static final String TAG = CallReceiver.class.getSimpleName();

    MediaRecorder recorder;
    TelephonyManager telManager;
    Context ctx;
    boolean recordStarted;
    static boolean status = false;
    String phoneNumber;
    byte[] incrept;
    byte[] decrpt;
    String selected_song_name;

    public CallReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "onReceive() called with: " + "context = [" + context + "], intent = [" + intent + "]");
        ctx = context;

        try {

            TelephonyManager tmgr = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);

            MyPhoneStateListener PhoneListener = new MyPhoneStateListener();

            tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        } catch (Exception e) {
            Log.e(TAG, "Phone Receive Error" + e);
        }

    }

    private class MyPhoneStateListener extends PhoneStateListener {

        public void onCallStateChanged(int state, String incomingNumber) {

            Log.d("MyPhoneListener",state+"   incoming no:"+incomingNumber);

            if (state == 1) {

                String msg = "New Phone Call Event. Incomming Number : "+incomingNumber;
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(ctx, msg, duration);
                toast.show();

            }
        }
    }
}


//        ctx = context;
//        String action = intent.getAction();
//        Log.d(TAG, "onReceive: " + action);
//
//        if (!status) {
//
//            try {
//                recorder = new MediaRecorder();
//                recorder.reset();
//                recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//                recorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
//                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
//
//                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
//                String imageFileName = "CALL_" + timeStamp + "_";
//                File storageDir = Environment.getExternalStoragePublicDirectory(
//                        Environment.DIRECTORY_PICTURES);
//
//                File call = File.createTempFile(imageFileName, "", storageDir);
//
//                Log.d(TAG, "onReceive: format");
//                Log.d(TAG, "file:" + call.getAbsolutePath());
//
//                recorder.setOutputFile(call.getAbsolutePath());
//
//                recorder.prepare();
//
//                recorder.start();
//
//                recordStarted = true;
//
//                status = true;
//            } catch (Exception e) {
//
//                Log.e(TAG, "onReceive: EXCEPTION" + e);
//                e.printStackTrace();
//            }
//        }