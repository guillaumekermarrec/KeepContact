package com.guillaumek.keepcontact;

import java.util.ArrayList;

/**
 * Created by guillaume on 07/10/2014.
 */
public class contact_description_simple
{
    public String id,name,phoneNumber;
    public ArrayList<String> allPhoneNumber;

    public contact_description_simple(String id, String name, String phoneNumber, ArrayList<String>  allPhoneNumber)
    {
        this.id=id;
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.allPhoneNumber=allPhoneNumber;
    }

    public contact_description_simple(String id, String name, String phoneNumber)
    {
        this.id=id;
        this.name=name;
        this.phoneNumber=phoneNumber;
    }
}