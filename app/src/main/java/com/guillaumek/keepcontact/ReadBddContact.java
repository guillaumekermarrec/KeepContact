package com.guillaumek.keepcontact;

/**
 * Created by guillaume on 07/10/2014.
 */

import com.guillaumek.database.FavoritesFriendsDefBDD;
import com.guillaumek.database.FavoritesFriendsSQLite;

import android.content.Context;

public class ReadBddContact
{

    /**
     * updateBDD permet de mettre a jour et d'ajouter les differentes informations concernant l'utilisateur:
     * 	Son identifiant,son activité, sa position, et l'heure a laquelle il etait connecté.
     */
    protected void updateBddUserData(Context context, String name, String phoneNumber, int updateType)
    {
        System.out.println("updating bdd");
        //Création d'une instance
        FavoritesFriendsSQLite friendBdd = new FavoritesFriendsSQLite(context);

        FavoritesFriendsDefBDD friend = new FavoritesFriendsDefBDD(name, phoneNumber);

        friendBdd.open();
        switch (updateType)
        {
            case 0 :
                if(friendBdd.getUserWithName(name)==null) friendBdd.insertFriend(friend);
                break;
            case 1 :
                if(friendBdd.getUserWithName(name)!=null) friendBdd.removeFavoritesFriendsWithName(friendBdd.getUserWithName(name).getId());
                break;
            case 2 :
                //nothing to do here. Only initialization
                break;
            default :
                //nothing to do here. Only initialization
                break;
        }

        friendBdd.close();

        ReadContactSms.ListeOfFavoritesFriends.clear();
        ReadContactSms.ListeOfFavoritesFriends.addAll(friendBdd.getAllUser());

    }
}