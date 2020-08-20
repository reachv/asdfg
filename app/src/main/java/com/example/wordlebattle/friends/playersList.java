package com.example.wordlebattle.friends;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.wordlebattle.Parsestuff.MainActivity;
import com.example.wordlebattle.R;
import com.example.wordlebattle.adapters.friendsAdapter;
import com.example.wordlebattle.fragments.createGamesFragment;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class playersList extends AppCompatActivity {

    List<ParseUser> users;
    ArrayList<String> res;
    RecyclerView recyclerView;
    friendsAdapter friendsAdapter;
    Button finish;
    friendsAdapter.OnClickListener onClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);


        users = new ArrayList<>();
        res = new ArrayList<String>();
        recyclerView = findViewById(R.id.rvFriends);
        finish = findViewById(R.id.finish);

        onClickListener = new friendsAdapter.OnClickListener() {
            @Override
            public void onCLick(int position) {
                res.add(users.get(position).getObjectId());
                users.remove(position);
                friendsAdapter.notifyItemRemoved(position);
            }
        };

        users.addAll(ParseUser.getCurrentUser().getList("FriendsList"));
        for(ParseUser user : users) {
            try {
                Log.e("here3", user.fetchIfNeeded().getUsername());
            } catch (ParseException e) {
                Log.e("here3", e.toString());
                e.printStackTrace();
            }
        }

        friendsAdapter = new friendsAdapter(users, onClickListener);
        recyclerView.setAdapter(friendsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putStringArrayListExtra("res", res);
                setResult(Activity.RESULT_OK, i);
                finish();

            }
        });
    }
}