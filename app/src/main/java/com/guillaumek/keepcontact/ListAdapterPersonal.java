package com.guillaumek.keepcontact;

/**
 * Created by guillaume on 07/10/2014.
 */

import java.util.ArrayList;
import android.util.Log;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

public class ListAdapterPersonal extends BaseAdapter
{
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<contact_description_simple> objects;

    protected RadioButton currentRadioButton;

    ListAdapterPersonal(Context context, ArrayList<contact_description_simple> friends) {
        ctx = context;
        objects = friends;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.listview_contact, parent, false);
        }

        contact_description_simple c_desc = getFriend(position);

        TextView textView = (TextView) view.findViewById(R.id.text1);
        textView.setText(c_desc.name);

        currentRadioButton = (RadioButton) view.findViewById(R.id.radio);

        if(ReadContactSms.ListeOfFavoritesFriends.size()>0)
        {
            for(int j=0;j<ReadContactSms.ListeOfFavoritesFriends.size();j++)
            {
                if(ReadContactSms.ListeOfFavoritesFriends.get(j).getName().equals(c_desc.name))
                {
                    currentRadioButton.setChecked(true);
                    break;
                }
                else currentRadioButton.setChecked(false);
            }
        }
        else currentRadioButton.setChecked(false);

        return view;
    }

    contact_description_simple getFriend(int position) {
        return ((contact_description_simple) getItem(position));
    }
}