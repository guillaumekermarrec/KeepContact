package com.guillaumek.keepcontact;

/**
 * Created by guillaume on 07/10/2014.
 */

import android.database.Cursor;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import android.util.Log;

import com.guillaumek.database.FavoritesFriendsDefBDD;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.ContactsContract;

public class ReadContactSms
{

    protected contact_description currentContact;
    protected static List<String> lastFriendSMS = new ArrayList<String>();
    protected static Vector<contact_description> contact= new Vector<contact_description>();
    protected static List<FavoritesFriendsDefBDD> friendToCall = new ArrayList<FavoritesFriendsDefBDD>();
    protected static List<FavoritesFriendsDefBDD> ListeOfFavoritesFriends = new ArrayList<FavoritesFriendsDefBDD>();

    protected void readContact(Context context)
    {
        contact.clear();
        ContentResolver cr = context.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, "UPPER("+ContactsContract.Contacts.DISPLAY_NAME + ") ASC");

        if (cur.getCount() > 0) {
            @SuppressWarnings("unused")
            int multiplePhoneNumber=0;
            while (cur.moveToNext()) {
                currentContact=new contact_description();
                currentContact.phoneNumber=new ArrayList<String>();
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    currentContact.firstName = name;
                    currentContact.id = id;

                    // get the phone number
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phone = pCur.getString(
                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        currentContact.phoneNumber.add(phone);
                        multiplePhoneNumber++;
                    }
                    pCur.close();
                    contact.add(currentContact);
                }
            }
        }
    }

    protected List<String> readSmsFromFavoritesFriends(Context context){

        System.out.println("reading sms");

        ContentResolver cr = context.getContentResolver();

        Cursor cursor = cr.query(Uri.parse("content://sms/inbox"), new String[]{"address", "date"}, null, null, "date DESC");

        long lapsReadingSMS=1000*60*60*24*7;

        switch(PreferenceConnector.readInteger(context, PreferenceConnector.READSMS, 4))
        {
            case 1:
                lapsReadingSMS=1000*60*60*24;
                break;
            case 2:
                lapsReadingSMS=1000*60*60*24*2;
                break;
            case 3:
                lapsReadingSMS=1000*60*60*24*4;
                break;
            case 4:
                lapsReadingSMS=1000*60*60*24*7;
                break;
            case 5:
                lapsReadingSMS=1000*60*60*24*7*2;
                break;
            default:
                lapsReadingSMS=1000*60*60*24*7;
                break;
        }
        //long oneWeek=1000*60*60*24*7;
        if (cursor != null) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);

                //long messageId = cursor.getLong(0);
                //long threadId = cursor.getLong(1);
                String address = cursor.getString(0);
                //long contactId = cursor.getLong(3);
                long timestamp = cursor.getLong(1);
                if(timestamp+lapsReadingSMS<System.currentTimeMillis()) break;
                for (int comptContact = 0; comptContact < contact.size(); comptContact++)
                {
                    for (int multiplePhoneNumber = 0; multiplePhoneNumber < contact.get(comptContact).phoneNumber.size(); multiplePhoneNumber++)
                    {
                        if(PhoneNumberUtils.compare(address, contact.get(comptContact).phoneNumber.get(multiplePhoneNumber)))
                        {
                            if(lastFriendSMS.size()!=0)
                            {
                                for (int nameAlready = 0; nameAlready < lastFriendSMS.size(); nameAlready++)
                                {
                                    if(contact.get(comptContact).firstName==lastFriendSMS.get(nameAlready))break;
                                    if(nameAlready+1==lastFriendSMS.size())lastFriendSMS.add(contact.get(comptContact).firstName);
                                }
                            }
                            else lastFriendSMS.add(contact.get(comptContact).firstName);
                        }
                    }
                }
            }
        }
        return lastFriendSMS;
    }

    protected void shouldGiveNews()
    {
        boolean searchAllFriend;
        friendToCall.clear();
        for (int i = 0; i < ListeOfFavoritesFriends.size(); i++)
        {
            searchAllFriend=false;
            for (int j = 0; j < ReadContactSms.lastFriendSMS.size(); j++)
            {
                if(ReadContactSms.lastFriendSMS.get(j).equals(ListeOfFavoritesFriends.get(i).getName()))//lastFriendSMS.get(j).contains(FavoritesFriends.get(i)))
                {
                    searchAllFriend=true;
                }
                if(j+1==ReadContactSms.lastFriendSMS.size() && !searchAllFriend)
                {
                    friendToCall.add(ListeOfFavoritesFriends.get(i));
                    Log.v("Read Contact sms","adding friend");
                }
            }
        }
    }

}