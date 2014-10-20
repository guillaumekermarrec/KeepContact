package com.guillaumek.keepcontact;

/**
 * Created by guillaume on 07/10/2014.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;


public class ParameterUser extends PreferenceActivity {

    private static final int RESULT_SETTINGS = 1;

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.parameter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SETTINGS:
                SaveChanges();
                break;
        }
    }

    @Override
    public void onBackPressed()
    {
        finish();
    }


    protected void SaveChanges()
    {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        PreferenceConnector.writeBoolean(this, PreferenceConnector.ENABLEREMINDER, sharedPrefs.getBoolean("EnableNotification", true));
        PreferenceConnector.writeInteger(this, PreferenceConnector.READSMS, Integer.parseInt(sharedPrefs.getString("prefSyncFrequency", "4")));
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
            SaveChanges();
            finish();
            return true;
        }
        return true;
    }

    public void onDestroy()
    {
        SaveChanges();
        super.onDestroy();
    }

}