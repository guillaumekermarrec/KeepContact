package com.guillaumek.keepcontact;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.util.Log;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageButton;
import android.view.View.OnClickListener;

/**
 * Created by guillaume on 07/10/2014.
 *
 * Cette classe permet de visualier les personnes dont l'utilisateur doit prendre des nouvelles
 * Cette activité présente une liste de contact. Lors de l'appui sur un contact, l'application demandera
 * a l'utilsateur ce qu'il souhaite faire :
 *  Appeler
 *  Envoyer message
 */

public class Remember extends Activity
{
    ListView lv;
    ArrayList<contact_description_simple> friends_list = new ArrayList<contact_description_simple>();
    ListAdapterContactNews friendsAdapter;
    ListView lvMain;

    ReadContactSms readContactSms = new ReadContactSms();
    ReadBddContact readBddContact = new ReadBddContact();
    addContactFavorite addContactFavorite= new addContactFavorite(Remember.this);
    SelectCallMessage selectCallMessage = new SelectCallMessage(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.remember);

        readBddContact.updateBddUserData(this,"null","null",2);

        if(ServiceKeepContact.messageReceived) readContactSms.readSmsFromFavoritesFriends(this);

        ServiceKeepContact.messageReceived=false;
        readContactSms.shouldGiveNews();

        for(int i = 0 ; i < ReadContactSms.friendToCall.size() ; i++)
        {
            try{
                friends_list.add(new contact_description_simple("null", ReadContactSms.friendToCall.get(i).getName(), ReadContactSms.friendToCall.get(i).getphoneNumer()));
            }
            catch (Exception e) {
                // Nothing to do here
            }
        }

        lv = (ListView) findViewById(R.id.lv_remember);

        friendsAdapter = new ListAdapterContactNews(this, friends_list);

        lv.setAdapter(friendsAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                showDialog(Remember.this,"What do you want to do ?", i);
            }
        });
/*
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int i, long l) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Remember.this);

                builder.setTitle("Do you want to remove this contact");

                builder.setNegativeButton("Yes",  new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //ReadContactSms.friendToCall.remove(i);
                        //friendsAdapter.notifyDataSetChanged();

                        String returnInfoContactFavorite[]=new String[3];
                        returnInfoContactFavorite=addContactFavorite.addOrNotContactToFavorite(i);
                        readBddContact.updateBddUserData(Remember.this, returnInfoContactFavorite[0], returnInfoContactFavorite[1], 1);

                    }});
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }});
                builder.show();
                return true;
            }
        });*/
    }

    /**
     * Affiche un dialogue demandant à l'utilisateur ce qu'il souhairte faire
     * Dans ce cas l'utilisateur peut soit envoyer un message soit apperler la personne selectionnée
     * @param activity
     * @param title
     * @param i
     */
    public void showDialog(Activity activity, String title, int i) {
        selectCallMessage.showDialog("What do you what to do ?",friends_list.get(i).name,friends_list.get(i).phoneNumber);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.remember, menu);
        return true;
    }

    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_done) {
            finish();
            return true;
        }
        return true;
    }

    public void onDestroy()
    {
        super.onDestroy();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

}
