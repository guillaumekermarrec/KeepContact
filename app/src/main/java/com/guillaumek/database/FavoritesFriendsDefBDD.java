package com.guillaumek.database;

/**
 * Created by guillaume on 07/10/2014.
 */
public class FavoritesFriendsDefBDD {

    private int id;
    protected String name, phoneNumer;

    public FavoritesFriendsDefBDD(){ }

    public FavoritesFriendsDefBDD(String name, String phoneNumer)
    {
        this.name=name;
        this.phoneNumer=phoneNumer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getphoneNumer() {
        return phoneNumer;
    }

    public void setphoneNumer(String phoneNumer) {
        this.phoneNumer = phoneNumer;
    }

    public String toString(){
        return "ID : "+id + "\n "+ "name : "+name+"\n"+"phoneNumber : "+phoneNumer;
    }
}
