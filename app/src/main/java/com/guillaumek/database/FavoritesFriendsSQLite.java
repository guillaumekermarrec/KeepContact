package com.guillaumek.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by guillaume on 07/10/2014.
 */
public class FavoritesFriendsSQLite {

    protected static final String TABLE_BEST_FRIENDS_USER = "table_best_friends";
    protected static final String COL_ID = "ID";
    protected static final int NUM_COL_ID = 0;
    protected static final String COL_NAME = "name";
    protected static final int NUM_COL_NAME = 1;
    protected static final String COL_PHONE_NUMBER = "phoneNumber";
    protected static final int NUM_COL_PHONE_NUMBER = 2;
    protected static final int VERSION_BDD = 1;
    protected static final String NOM_BDD = "favoritesContactsSQLite.db";

    protected SQLiteDatabase bdd;

    protected CreateSQLite BaseSQLite;

    public FavoritesFriendsSQLite(Context context){
        //On crée la BaseSQLDD et sa table
        BaseSQLite = new CreateSQLite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BaseSQL en écriture
        bdd = BaseSQLite.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertFriend(FavoritesFriendsDefBDD favoritesfriends){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)

        values.put(COL_NAME, favoritesfriends.getName());
        values.put(COL_PHONE_NUMBER, favoritesfriends.getphoneNumer());
        //on insère l'objet dans la BaseSQL via le ContentValues
        return bdd.insert(TABLE_BEST_FRIENDS_USER, null, values);
    }

    public int updateUser(int id, FavoritesFriendsDefBDD favoritesfriends){
        ContentValues values = new ContentValues();
        values.put(COL_NAME, favoritesfriends.getName());
        values.put(COL_PHONE_NUMBER, favoritesfriends.getphoneNumer());
        return bdd.update(TABLE_BEST_FRIENDS_USER, values, COL_ID + " = " +id, null);
    }

    public void addNewLocation(FavoritesFriendsDefBDD favoritesfriends) {

        ContentValues values = new ContentValues();
        values.put(COL_NAME, favoritesfriends.getName());
        values.put(COL_PHONE_NUMBER, favoritesfriends.getphoneNumer());
        // Inserting Row
        bdd.insert(TABLE_BEST_FRIENDS_USER, null, values);
    }

    public int removeFavoritesFriendsWithName(int id){
        //Suppression d'un utilisateur de la BaseSQL grâce à son ID
        return bdd.delete(TABLE_BEST_FRIENDS_USER, COL_ID + " = " +id, null);
    }

    public int removeFavoritesFriendsWithPhoneNumber(int id){
        //Suppression d'un utilisateur de la BaseSQL grâce à son numero de telephone
        return bdd.delete(TABLE_BEST_FRIENDS_USER, COL_PHONE_NUMBER + " = " +id, null);
    }

    public FavoritesFriendsDefBDD getUserWithName(String nameFriend){
        Cursor c = bdd.query(TABLE_BEST_FRIENDS_USER, new String[] {COL_ID, COL_NAME, COL_PHONE_NUMBER}, COL_NAME + " LIKE \"" + nameFriend  +"\"", null, null, null, null);
        return cursorToFriend(c);
    }

    public FavoritesFriendsDefBDD getUserWithPhoneNumber(String phoneNumber){
        Cursor c = bdd.query(TABLE_BEST_FRIENDS_USER, new String[] {COL_ID, COL_NAME, COL_PHONE_NUMBER}, COL_PHONE_NUMBER + " LIKE \"" + phoneNumber  +"\"", null, null, null, null);
        return cursorToFriend(c);
    }
    public FavoritesFriendsDefBDD getUserWithId(int id){
        Cursor c = bdd.query(TABLE_BEST_FRIENDS_USER, new String[] {COL_ID, COL_NAME, COL_PHONE_NUMBER}, COL_ID + " LIKE \"" + id  +"\"", null, null, null, null);
        return cursorToFriend(c);
    }

    private FavoritesFriendsDefBDD cursorToFriend (Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        //Sinon on se place sur le premier élément
        c.moveToFirst();

        FavoritesFriendsDefBDD favoritesfriends = new FavoritesFriendsDefBDD();
        //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
        favoritesfriends.setId(c.getInt(NUM_COL_ID));
        favoritesfriends.setName(c.getString(NUM_COL_NAME));
        favoritesfriends.setphoneNumer(c.getString(NUM_COL_PHONE_NUMBER));
        //On ferme le cursor
        c.close();

        return favoritesfriends;
    }

    public List<FavoritesFriendsDefBDD> getAllUser() {
        List<FavoritesFriendsDefBDD> friendsList = new ArrayList<FavoritesFriendsDefBDD>();

        String selectQuery = "SELECT  * FROM " + TABLE_BEST_FRIENDS_USER;

        SQLiteDatabase bd = BaseSQLite.getWritableDatabase();
        Cursor cursor = bd.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                FavoritesFriendsDefBDD favoritesfriends = new FavoritesFriendsDefBDD();
                favoritesfriends.setId(Integer.parseInt(cursor.getString(NUM_COL_ID)));
                favoritesfriends.setName(cursor.getString(NUM_COL_NAME));
                favoritesfriends.setphoneNumer(cursor.getString(NUM_COL_PHONE_NUMBER));
                friendsList.add(favoritesfriends);
            } while (cursor.moveToNext());
        }

        // return contact list
        return friendsList;
    }

    public List<FavoritesFriendsDefBDD> getOneUser() {
        List<FavoritesFriendsDefBDD> userList = new ArrayList<FavoritesFriendsDefBDD>();
        String selectQuery = "SELECT  * FROM " + TABLE_BEST_FRIENDS_USER;

        SQLiteDatabase bd = BaseSQLite.getWritableDatabase();
        Cursor cursor = bd.rawQuery(selectQuery, null);

        if (cursor.moveToLast())
        {
            FavoritesFriendsDefBDD favoritesfriends = new FavoritesFriendsDefBDD();
            favoritesfriends.setId(Integer.parseInt(cursor.getString(NUM_COL_ID)));
            favoritesfriends.setName(cursor.getString(NUM_COL_NAME));
            favoritesfriends.setphoneNumer(cursor.getString(NUM_COL_PHONE_NUMBER));
            userList.clear();
            userList.add(favoritesfriends);
        }
        // return contact list
        return userList;
    }

    public List<FavoritesFriendsDefBDD> getOneUserWithId(int IdUser) {
        List<FavoritesFriendsDefBDD> userList = new ArrayList<FavoritesFriendsDefBDD>();
        String selectQuery = "SELECT  * FROM " + TABLE_BEST_FRIENDS_USER;

        SQLiteDatabase bd = BaseSQLite.getWritableDatabase();
        Cursor cursor = bd.rawQuery(selectQuery, null);

        if (cursor.moveToPosition(IdUser))
        {
            FavoritesFriendsDefBDD favoritesfriends = new FavoritesFriendsDefBDD();
            favoritesfriends.setId(Integer.parseInt(cursor.getString(NUM_COL_ID)));
            favoritesfriends.setName(cursor.getString(NUM_COL_NAME));
            favoritesfriends.setphoneNumer(cursor.getString(NUM_COL_PHONE_NUMBER));
            userList.clear();
            userList.add(favoritesfriends);
        }

        // return contact list
        return userList;
    }
}
