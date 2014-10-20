package com.guillaumek.keepcontact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;
import android.net.Uri;


public class SelectCallMessage {

    Context context;

    SelectCallMessage(Context _context){
        this.context=_context;
    }
    /**
     * Affiche un dialogue demandant à l'utilisateur ce qu'il souhairte faire
     * Dans ce cas l'utilisateur peut soit envoyer un message soit apperler la personne selectionnée
     * @param activity
     * @param title
     * @param i
     */
    public void showDialog( String title, final String contactName, final String contactNumber) {

        AlertDialog.Builder builder = new AlertDialog.Builder((Activity) context);

        if (title != null) builder.setTitle(title);

        builder.setPositiveButton("Send Message",  new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { sendMessageToPerson(contactName,contactNumber);}});
        builder.setNegativeButton("Call", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { callPerson(contactName,contactNumber);}});

        builder.show();
    }

    /**
     * Permet d'appeler la personne selectionnée
     * @param i
     */
    public void callPerson(String contactName, String contactNumber){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+contactNumber));
        context.startActivity(callIntent);
    }

    /**
     * Permet l'envi de message la personne selectionnée.
     * L'application va ouvrir un diaglog demandant à l'utilisateur de rentrer son message
     * @param i
     */
    public void sendMessageToPerson(String contactName, String contactNumber){

        Intent changeIntent = new Intent(context, SendMessage.class);
        changeIntent.putExtra("contactName",contactName);
        changeIntent.putExtra("contactNumber",contactNumber);
        context.startActivity(changeIntent);
    }
}
