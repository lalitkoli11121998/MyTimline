package com.example.dell.mytimline;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by dell on 6/22/2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        Log.d("meessegetoken", remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d("playlooddata" , remoteMessage.getData().toString());

               }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("bodymessege" , remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void sendNotification(String body) {
        Log.d("notificationbody", body);
        NotificationManager manager = (NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this ,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this ,0,intent ,PendingIntent.FLAG_ONE_SHOT);
        Uri Notificationsount = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("mychannel","My Channel", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder =(NotificationCompat.Builder) new NotificationCompat.Builder(this,"myid")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("firebase cloud messege")
                .setContentText("title")
                .setSound(Notificationsount)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_launcher_foreground ,"stop", pendingIntent);

        manager.notify( 1 , mBuilder.build());
           }
}
