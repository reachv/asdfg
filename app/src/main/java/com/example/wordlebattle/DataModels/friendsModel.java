package com.example.wordlebattle.DataModels;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("FriendsRequest")
public class friendsModel extends ParseObject {

    //Constuctor
    public friendsModel() {}

    //Keys
    public static String sender = "sender";
    public static String receiver = "receiver";
    public static String status = "status";

    //Getters
    public ParseUser getSender(){return getParseUser(sender);}
    public ParseUser getReceiver(){return getParseUser(receiver);}
    public Boolean getStatus(){return getBoolean(status);}

    //Setters
    public void setSender(ParseUser send){put(sender, send);}
    public void setReceiver(ParseUser rece){put(receiver, rece);}
    public void setStatus(boolean statu){put(status, statu);}
}
