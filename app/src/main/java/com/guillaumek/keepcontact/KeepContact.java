package com.guillaumek.keepcontact;

import java.util.ArrayList;
import java.util.List;

import android.widget.ArrayAdapter;

import android.util.Log;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.AdapterView;
import android.view.View;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageButton;
import java.lang.Runnable;

/**
 *  Keepcontact est la classe permettant l'affichage de l'interface
 *  Elle affiche la liste de tous les contacts de l'utilisateur et propose en différentes fonctionnalités en fonctiion de l'interaction avec l'utilisateur
 */
public class KeepContact extends Activity {

    String DEBUG_TAG="KeepContact";

    ReadContactSms readContactSms = new ReadContactSms();
    ReadBddContact readBddContact = new ReadBddContact();

    protected int i=0;

    static List<String> FavoritesFriends = new ArrayList<String>();

    ListView lv;

    addContactFavorite addContactFavorite= new addContactFavorite(KeepContact.this);

    static ArrayList<contact_description_simple> friends_list = new ArrayList<contact_description_simple>();
    ListAdapterPersonal friendsAdapter;
    ListView lvMain;

    Handler handler = new Handler(Looper.getMainLooper());
    SelectCallMessage selectCallMessage = new SelectCallMessage(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_keep_contact);

        if(ReadContactSms.contact.size()==0) readContactSms.readContact(this);

        friends_list.clear();
        for(int i = 0 ; i < ReadContactSms.contact.size() ; i++) {
            try {
                if(ReadContactSms.contact.get(i).phoneNumber.size()!=0)
                {
                    //Log.v("DEBUG", "name "+ReadContactSms.contact.get(i).firstName);
                    friends_list.add(new contact_description_simple(ReadContactSms.contact.get(i).id, ReadContactSms.contact.get(i).firstName, ReadContactSms.contact.get(i).phoneNumber.get(0), ReadContactSms.contact.get(i).phoneNumber));
                }else{
                    ReadContactSms.contact.remove(i);
                }
            }
            catch( IndexOutOfBoundsException e){
                Log.v("DEBUG", "ArrayIndexOutOfBoundsException");
                i--;
            }
        }
        lv = (ListView) findViewById(R.id.lv);

        friendsAdapter = new ListAdapterPersonal(this, friends_list);

        lv.setAdapter(friendsAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View view, final int i, long l) {
                Log.v("ANDROID","Clicked");
                SelectNumberIfSeveralNumber(i,false);

                String returnInfoContactFavorite[]=new String[3];
                returnInfoContactFavorite=addContactFavorite.addOrNotContactToFavorite(i);
                updateBdd(returnInfoContactFavorite);

                friendsAdapter = (ListAdapterPersonal) lv.getAdapter();
                friendsAdapter.notifyDataSetChanged();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int i, long l) {

                SelectNumberIfSeveralNumber(i,true);

                return true;
            }
        });

        readBddContact.updateBddUserData(this,"initialization", "initialization", 3);

        if(!ServiceKeepContact.isRunning)
        {
            Intent service = new Intent(this, ServiceKeepContact.class);
            ServiceKeepContact.isRunning=true;
            this.startService(service);
        }
    }

    protected void SelectNumberIfSeveralNumber(final int contactId, final boolean showDialogWhatToDo){
        if(!addContactFavorite.isAlreadySelected(contactId) || showDialogWhatToDo){

            final Dialog dialog = new Dialog(KeepContact.this);
            dialog.setContentView(R.layout.list_several_number);

            ListView listSeveralPhoneNumber = (ListView) dialog.findViewById(R.id.listSeveralPhoneNumber);

            ArrayAdapter<String> __adapter = new ArrayAdapter<String>(KeepContact.this,android.R.layout.simple_list_item_1,friends_list.get(contactId).allPhoneNumber);
            listSeveralPhoneNumber.setAdapter(__adapter);

            listSeveralPhoneNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> av, View view, int valueListClicked, long l) {
                    if(showDialogWhatToDo){
                        friends_list.set(contactId,new contact_description_simple( friends_list.get(contactId).id, friends_list.get(contactId).name, friends_list.get(contactId).allPhoneNumber.get(valueListClicked),friends_list.get(contactId).allPhoneNumber));
                        dialog.cancel();
                        showDialog(KeepContact.this,"What do you whant to do ?", contactId);
                    }
                    else dialog.cancel();
                }
            });

            dialog.setCancelable(true);
            dialog.setTitle("Phone number");
            dialog.show();
        }
    }
    /**
     * Affiche un dialogue demandant à l'utilisateur ce qu'il souhairte faire
     * Dans ce cas l'utilisateur peut soit envoyer un message soit apperler la personne selectionnée
     * @param activity
     * @param title
     * @param i
     */
    public void showDialog(Activity activity, String title, int i) {
        Log.v("DEBUG","show dialog"+friends_list.get(i).name+" - "+friends_list.get(i).phoneNumber);
        selectCallMessage.showDialog("What do you what to do ?",friends_list.get(i).name,friends_list.get(i).phoneNumber);
    }

    /**
     * Permet la mise a jour de la base de donnée. Elle contient toutes les inforamtions sur les contatcs favoris.
     * La fonction prend en parametre un tableau composé de l'id, du nom et du numéro de la personne à ajouter
     * @param returnInfoContactFavorite
     */
    protected void updateBdd(String returnInfoContactFavorite[])
    {
        readBddContact.updateBddUserData(this, returnInfoContactFavorite[0], returnInfoContactFavorite[1], Integer.parseInt(returnInfoContactFavorite[2]));
    }

    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        int itemId = item.getItemId();
        if (itemId == R.id.action_contact_to_call) {
            Intent changeIntentParameter = new Intent(KeepContact.this, Remember.class);
            KeepContact.this.startActivity(changeIntentParameter);
            return true;
        }
        else if (itemId == R.id.action_settings) {
            Intent changeIntentParameter = new Intent(KeepContact.this, ParameterUser.class);
            KeepContact.this.startActivity(changeIntentParameter);
            return true;
        }
        return true;
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.keep_contact, menu);
        return true;
    }
}

