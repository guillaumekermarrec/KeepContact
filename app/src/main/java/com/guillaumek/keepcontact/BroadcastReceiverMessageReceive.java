package com.guillaumek.keepcontact;

/**
 * Created by guillaume on 11/10/2014.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Permet de mettre à jour la base de donnée lors de la reception d'un message (SMS ou MMS)
 */

public class BroadcastReceiverMessageReceive extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent arg1) {
        ServiceKeepContact.messageReceived=true;
    }
}