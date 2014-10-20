package com.guillaumek.keepcontact;

/**
 * Created by guillaume on 07/10/2014.
 */

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapterContactNews extends BaseAdapter
{
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<contact_description_simple> objects;

    ListAdapterContactNews(Context context, ArrayList<contact_description_simple> friends) {
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
            view = lInflater.inflate(R.layout.remember_per_contact, parent, false);
        }

        contact_description_simple c_desc = getFriend(position);

        TextView textView = (TextView) view.findViewById(R.id.contactRemember);
        textView.setText(c_desc.name);

        return view;
    }

    contact_description_simple getFriend(int position) {
        return ((contact_description_simple) getItem(position));
    }
}