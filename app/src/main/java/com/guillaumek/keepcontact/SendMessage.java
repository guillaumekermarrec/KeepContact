package com.guillaumek.keepcontact;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageButton;
import android.view.View.OnClickListener;
import android.telephony.SmsManager;
import android.os.Bundle;
import android.view.View;

public class SendMessage extends Activity {

    private  Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        bundle = getIntent().getExtras();

        init(bundle.getString("contactName"),bundle.getString("contactNumber"));
    }

    protected void init(String contactID, final String phoneNumber){
        TextView _phoneNumber = (TextView)findViewById(R.id.phoneNumberIndicator);
        _phoneNumber.setText(contactID+"\n"+phoneNumber);

        EditText _textMessage = (EditText)findViewById(R.id.editTextMessage);

        ImageButton _sendButton = (ImageButton) findViewById(R.id.sendButton);
        _sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText __textMessage = (EditText)findViewById(R.id.editTextMessage);
                String __message=__textMessage.getText().toString();
                if(__message.length()==0)__message=" ";
                SmsManager smsManager = SmsManager.getDefault();
                if(__message.equals("tssss!!")){
                    for (int nbMessage=0;nbMessage<=9;nbMessage++){
                        smsManager.sendTextMessage(phoneNumber, null, __message, null, null);
                    }
                }else
                    smsManager.sendTextMessage(phoneNumber, null, __message, null, null);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.send_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
