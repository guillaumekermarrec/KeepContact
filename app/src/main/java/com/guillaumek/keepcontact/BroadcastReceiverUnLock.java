package com.guillaumek.keepcontact;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by guillaume on 11/10/2014.
 *
 * Ce broadcast permet d'afficher une notificaiton lors du dévérouillage du téléphone
 */

public class BroadcastReceiverUnLock extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent arg1) {
        Intent service = new Intent(context, ServiceKeepContactUnLock.class);
        // Débute le service necessaire à l'affichage de la notification
        context.startService(service);
    }

}