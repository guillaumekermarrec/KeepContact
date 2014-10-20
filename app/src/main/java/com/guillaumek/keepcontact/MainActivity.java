package com.guillaumek.keepcontact;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Loading the application
        Intent changeIntent;
        changeIntent = new Intent(MainActivity.this, KeepContact.class);
        MainActivity.this.startActivity(changeIntent);
        finish();
    }

}
