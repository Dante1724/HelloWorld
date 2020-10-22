package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

class BroadcastRecieverClass extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
//            Toast.makeText(context, "Connectivity changed", Toast.LENGTH_LONG).show();
//
//        }

        String stats = getConnectivity(context);
        onNotificationRecieve(context, stats);
        Toast.makeText(context, "Broadcast Reciever", Toast.LENGTH_LONG);

    }

    private String getConnectivity(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(null != networkInfo) {
            if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                return "Connected";
            }
        }
        return "Not Connected";

    }

    private void onNotificationRecieve(Context context, String stats) {
        String CHANNEL_ID = "MY_NOTIF_CHANNEL";
        NotificationChannel myChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            myChannel = new NotificationChannel(CHANNEL_ID, "My Channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notifcationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notifcationManager.createNotificationChannel(myChannel);
        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Wifi Connection")
                .setContentText(stats)
                .build();

        int notificationID = 0;
        notificationManager.notify(notificationID, notification);
    }

    //

}
