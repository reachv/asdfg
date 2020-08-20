package com.example.wordlebattle.Parsestuff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.wordlebattle.DataModels.friendsModel;
import com.example.wordlebattle.DataModels.gamesModel;
import com.example.wordlebattle.R;
import com.example.wordlebattle.adapters.gamesListAdapter;
import com.example.wordlebattle.fragments.FriendsRequestFragment;
import com.example.wordlebattle.fragments.HomeFragment;
import com.example.wordlebattle.fragments.createGamesFragment;
import com.example.wordlebattle.fragments.friendsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Data Values
    FragmentManager fragmentManager = getSupportFragmentManager();
    BottomNavigationView bottomNavigationView;
    Fragment Home, createGames, friends, requests;
    List<ParseUser> friend;
    List<friendsModel> temp;
    ParseUser user = ParseUser.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        friend = new ArrayList<>();
        temp = new ArrayList<>();
        bottomNavigationView = findViewById(R.id.bottomnav);
        Home = new HomeFragment();
        createGames = new createGamesFragment();
        friends = new friendsFragment();
        requests = new FriendsRequestFragment();

        ParseQuery<friendsModel> query = ParseQuery.getQuery(friendsModel.class);
        query.whereEqualTo("sender", user);
        query.whereEqualTo("status", true);
        query.findInBackground(new FindCallback<friendsModel>() {
            @Override
            public void done(List<friendsModel> objects, ParseException e) {
                for(int i = 0; i < objects.size(); i++){
                    friend.add(objects.get(i).getReceiver());
                    temp.add(objects.get(i));
                }
                friend.addAll(user.getList("FriendsList"));
                user.put("FriendsList", friend);
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e != null)Log.e("MainActivity", "Query");
                    }
                });
                for(friendsModel friendsModel : temp){
                    friendsModel.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e != null)Log.e("MainActivity", "delete");
                        }
                    });
                }
            }
        });

        fragmentManager.beginTransaction()
                .add(R.id.flContainer,createGames)
                .add(R.id.flContainer,Home)
                .add(R.id.flContainer, friends)
                .add(R.id.flContainer, requests)
                .hide(friends)
                .hide(createGames)
                .hide(requests)
                .commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch(item.getItemId()){
                case R.id.main:
                default:
                    fragmentManager.beginTransaction()
                            .show(Home)
                            .hide(createGames)
                            .hide(friends)
                            .commit();
                    break;
                case R.id.CreateGames:
                    fragmentManager.beginTransaction()
                            .hide(Home)
                            .show(createGames)
                            .hide(friends)
                            .commit();
                    break;
                case R.id.friends:
                    fragmentManager.beginTransaction()
                            .hide(Home)
                            .hide(createGames)
                            .show(friends)
                            .commit();
                    break;
                case R.id.request:
                    fragmentManager.beginTransaction()
                            .hide(Home)
                            .hide(createGames)
                            .hide(friends)
                            .show(requests)
                            .commit();
            }
            return true;
        });

    }


}