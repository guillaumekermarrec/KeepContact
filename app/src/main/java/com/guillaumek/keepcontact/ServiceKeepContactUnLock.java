package com.guillaumek.keepcontact;

/**
 * Created by guillaume on 07/10/2014.
 */
import java.util.Random;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;


public class ServiceKeepContactUnLock extends IntentService
{
    ParameterUser parameterUser = new ParameterUser();
    ReadContactSms readContactSms = new ReadContactSms();

    public ServiceKeepContactUnLock()
    {
        super("ServiceKeepContact");
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        System.out.print("sending notification 21");
        sendNotification();
        return START_STICKY;
    }

    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    protected void onHandleIntent(Intent arg0) {
        // TODO Auto-generated method stub
    }

    protected void sendNotification()
    {
        System.out.print("sending notification 22"+ReadContactSms.friendToCall.size());
        if(ReadContactSms.friendToCall.size()>0 && PreferenceConnector.readBoolean(this, PreferenceConnector.ENABLEREMINDER, true))
        {
            Random r = new Random();
			/*if(Build.VERSION.SDK_INT >= 4.1){
				createNotification_JELLY_BEAN(ReadContactSms.friendToCall.get(r.nextInt(ReadContactSms.friendToCall.size() - 0)).getName());
			}
			else*/
            //createNotification_Under_JELLY_BEAN(ReadContactSms.friendToCall.get(r.nextInt(ReadContactSms.friendToCall.size() - 0)).getName());
            createNotification_JELLY_BEAN(ReadContactSms.friendToCall.get(r.nextInt(ReadContactSms.friendToCall.size() - 0)).getName());
        }
    }

    protected final void createNotification_JELLY_BEAN(String notifGetNews)
    {
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("KeepContact")
                .setContentText(notifGetNews);

        Intent resultIntent = new Intent(this, Remember.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(Remember.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Id permettant d'identifier la notification
        mNotificationManager.notify(10, mBuilder.build());

    }


    protected void deleteNotification()
    {
        final NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        //la suppression de la notification se fait grâce à son ID
        notificationManager.cancel(10);
    }
}
