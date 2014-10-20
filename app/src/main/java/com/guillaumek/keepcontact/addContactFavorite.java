package com.guillaumek.keepcontact;

import android.util.Log;
import android.content.Context;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.View;

/**
 * Created by guillaume on 07/10/2014.
 * AddContactFavorite est une classe qui permet d'ajouter des contact en favori en fonction des actions de l'utilisateur
 * La fonction permet en cas de selection en favori d'un contact déjà existant de le supprimer de la liste.
 */
public class addContactFavorite
{
    protected Context context;

    addContactFavorite(Context context){
        this.context=context;
    }

    /**
     * Si le contact n'est pas déjà dans la liste des favorits alors il sera ajouté
     * dans le cas contraire il sera enlevé
     * @param contactNumber
     * @return
     */
    public String[] addOrNotContactToFavorite(int contactNumber)
    {
        final String returnInfoContactFavorite[]=new String[3];

        if(KeepContact.FavoritesFriends.contains(KeepContact.friends_list.get(contactNumber).name))
        {
            int position = getItemPosition(KeepContact.friends_list.get(contactNumber).name);
            if( position>=0)
            {
                returnInfoContactFavorite[0]=KeepContact.friends_list.get(contactNumber).name;
                returnInfoContactFavorite[1]=KeepContact.friends_list.get(contactNumber).phoneNumber;
                returnInfoContactFavorite[2]="1";
                return returnInfoContactFavorite;
            }
        }
        returnInfoContactFavorite[1]=KeepContact.friends_list.get(contactNumber).phoneNumber;
        returnInfoContactFavorite[0]=KeepContact.friends_list.get(contactNumber).name;
        returnInfoContactFavorite[2]="0";

        return returnInfoContactFavorite;
    }

    /**
     * la fonction va tester si le contact cliqué existe déjà dans la liste des amis
     * @param contactNumber
     * @return
     */
    public boolean isAlreadySelected(int contacId){
        KeepContact.FavoritesFriends.clear();

        for(int i=0;i<ReadContactSms.ListeOfFavoritesFriends.size();i++){
            KeepContact.FavoritesFriends.add(ReadContactSms.ListeOfFavoritesFriends.get(i).getName());
        }

        if(KeepContact.FavoritesFriends.contains(KeepContact.friends_list.get(contacId).name))
            return true;
        return false;
    }

    public int getItemPosition(String name)
    {
        for (int position=0; position<KeepContact.FavoritesFriends.size(); position++)
            if (KeepContact.FavoritesFriends.get(position).equals(name)) return position;
        return -1;
    }

}
