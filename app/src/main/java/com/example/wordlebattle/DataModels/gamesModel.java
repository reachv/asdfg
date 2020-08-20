package com.example.wordlebattle.DataModels;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.*;


@ParseClassName("Games")
public class gamesModel extends ParseObject {

    //Keys
    public static final String players = "players";
    public static final String scores = "scores";
    public static final String currWord = "currWord";
    public static final String Key_CreatedAt = "createdAt";
    public static final String attempted = "attempts";
    public static final String users = "users";

    //Constructor
    public gamesModel(){}

    //Getters
    public Map<String, Boolean> getPlayers(){return getMap(players);}
    public Map<String, Integer> getScores(){return getMap(scores);}
    public String getCurrWord(){return getString(currWord);}
    public Map<String, Integer> getAttempts(){return getMap(attempted);}
    public List<String> getUsers(){return getList(users);}

    //Setters
    public void setPlayers(Map<String, Boolean> player){put(players, player);}
    public void setCurrWord(String currWords){put(currWord, currWords);}
    public void setScores(Map<String, Integer> score){put(scores, score);}
    public void setAttempted(Map<String, Integer> attempts){put(attempted, attempts); }
    public void setUsers(List<String> user){put(users, user);}
}
