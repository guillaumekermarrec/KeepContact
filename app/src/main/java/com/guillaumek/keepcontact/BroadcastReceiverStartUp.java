package com.guillaumek.keepcontact;

/**
 * Created by guillaume on 11/10/2014.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.guillaumek.keepcontact.ServiceKeepContact;


/**
 * Ce broadcast permet de lancer automatiquement l'application au démarrage du téléphone
 */

public class BroadcastReceiverStartUp extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent arg1) {
        Intent service = new Intent(context, ServiceKeepContact.class);
        context.startService(service);
    }

}