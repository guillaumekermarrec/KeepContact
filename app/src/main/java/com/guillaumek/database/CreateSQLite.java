package com.guillaumek.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * Created by Guillaume on 27/11/13.
 */
public class CreateSQLite extends SQLiteOpenHelper{

    private static final String TABLE_BEST_FRIENDS = "table_best_friends";
    private static final String COL_ID = "ID";
    private static final String COL_NAME = "Name";
    private static final String COL_PHONE_NUMBER = "PhoneNumber";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_BEST_FRIENDS + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT NOT NULL, "
            + COL_PHONE_NUMBER + " TEXT NOT NULL);";

    public CreateSQLite(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        //creation de la table a partir de la requerte CREATE_BDD
        database.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_BEST_FRIENDS + ";");
        onCreate(db);
    }
}

