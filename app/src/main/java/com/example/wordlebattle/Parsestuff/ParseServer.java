package com.example.wordlebattle.Parsestuff;

import android.app.Application;

import com.example.wordlebattle.DataModels.friendsModel;
import com.example.wordlebattle.DataModels.gamesModel;
import com.example.wordlebattle.R;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseServer extends Application {
    public void onCreate(){
        super.onCreate();

        ParseObject.registerSubclass(friendsModel.class);
        ParseObject.registerSubclass(gamesModel.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.applicationkey))
                .clientKey(getString(R.string.clientKey))
                .server("https://parseapi.back4app.com")
                .build()
        );

    }
}
