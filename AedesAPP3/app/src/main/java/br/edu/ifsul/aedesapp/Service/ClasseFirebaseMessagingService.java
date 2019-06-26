package br.edu.ifsul.aedesapp.Service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import br.edu.ifsul.aedesapp.R;


public class ClasseFirebaseMessagingService extends FirebaseMessagingService {

    //@Override
    public void onNewToken(String token){
        Log.d("novotoken", "Refreshed token: " + token);
        sendRegistrationToServer(token);    }

    private void sendRegistrationToServer(String token) {
        Log.d("sendtoken", "Refreshed token: " + token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d("onde:", "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData() != null) {
            Log.d("datin:", "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                //scheduleJob();
            } else {
                // Handle message within 10 seconds
                //handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("corpo", "Message Notification Body: " + remoteMessage.getNotification().getBody());

        }

        notification_m(remoteMessage);
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    /*Atualização na ocorrência: " + remoteMessage.getNotification().getBody()*/
    public void notification_m(RemoteMessage remoteMessage) {
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setDefaults(NotificationCompat.DEFAULT_ALL).setSmallIcon(R.mipmap.ic_logoaedesnotification).setContentTitle("Aedes App")
                .setContentText("Ouve uma atualização na sua ocorrência.");
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
