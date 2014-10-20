package com.guillaumek.keepcontact;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by guillaume on 07/10/2014.
 * Cette classe permet de stocker les differentes informations concernant l'utilisateur.
 * Il n'a donc pas besoin de rentrer ses informations personelles a chaque démarrage de l'application
 * @author Guillaume Kermarrec
 *
 */
public class PreferenceConnector{

    public static final String PREF_NAME = "PEOPLE_PREFERENCES";
    public static final int MODE = Context.MODE_PRIVATE;

    public static final String ENABLEREMINDER = "ENABLEREMINDER";
    public static final String READSMS = "READSMS";
//	public static final String STARTREMINDTIME = "STARTREMINDTIME";
//	public static final String ENDREMINDTIME = "ENDREMINDTIME";
//	public static final String LAPSREMINDTIME = "LAPSREMINDTIME";
//	public static final String FIRSTSTART = "FIRSTSTART";

    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static boolean readBoolean(Context context, String key, boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static void writeFloat(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();
    }

    public static float readFloat(Context context, String key, float defValue) {
        return getPreferences(context).getFloat(key, defValue);
    }

    public static void writeLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();
    }

    public static long readLong(Context context, String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);
    }

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

}