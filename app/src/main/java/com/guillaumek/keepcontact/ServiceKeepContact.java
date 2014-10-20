package com.guillaumek.keepcontact;

/**
 * Created by guillaume on 07/10/2014.
 */

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

public class ServiceKeepContact extends IntentService
{

    ParameterUser parameterUser = new ParameterUser();
    ReadContactSms readContactSms = new ReadContactSms();
    ReadBddContact readBddContact = new ReadBddContact();

    protected static boolean isRunning = false, messageReceived;
    protected static boolean finishedReadContact;

    public ServiceKeepContact()
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
        ServiceKeepContact.finishedReadContact=false;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        ServiceKeepContact.isRunning=true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        readContactSms.readContact(this);
        ServiceKeepContact.finishedReadContact=true;
        startThreads();
        return START_STICKY;
    }

    protected void startThreads()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    try {
                        updateData();
                        Thread.sleep(60*60000);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    protected void updateData()
    {
        readBddContact.updateBddUserData(ServiceKeepContact.this,"null","null",2);
        readContactSms.readSmsFromFavoritesFriends(this);
        readContactSms.shouldGiveNews();
    }

    public void onDestroy()
    {
        ServiceKeepContact.isRunning=false;
        ServiceKeepContact.finishedReadContact=false;
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
}
