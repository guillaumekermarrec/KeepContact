package com.guillaumek.keepcontact;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class About extends Activity
{
    protected	TextView txtAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);

        txtAbout = (TextView) findViewById(R.id.About);
        txtAbout.setText("  With KeepContact, I wanted an application that help people to keep in touch with their friends or with their family. " +
                " \n \n Built by Guillaume Kermarrec");
    }
}