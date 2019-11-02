package com.example.justin.getterupperalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.AlarmClock;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class smsReceiver extends BroadcastReceiver {

    public static final String pdu_type = "pdus";
    public static final int REQUEST_CODE=101;


    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        String strMessage = "";
        String format = bundle.getString("format");
        // Retrieve the SMS message received.
        Object[] pdus = (Object[]) bundle.get(pdu_type);
        if (pdus != null) {
            // Check the Android version.

            // Fill the msgs array.
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {

                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);

//                strMessage += "SMS from " + msgs[i].getOriginatingAddress();
//                strMessage += " :" + msgs[i].getMessageBody() + "\n";
                String numberReceiver = msgs[i].getOriginatingAddress();
                numberReceiver = numberReceiver.substring(8);
                    if (Integer.valueOf(numberReceiver) == 9054) {
                        Log.d("##9", "CAROLINA");
                        if(msgs[i].getMessageBody().equals("safeword")){
                            Log.d("##9","safeword");

                            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                            Intent intentAlarm = new Intent(context, AlarmReceiver.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intentAlarm,0);
                            alarmManager.set(alarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(),pendingIntent);

                        }
                    }
            }
        }



    }




}
