package com.example.wordlebattle.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wordlebattle.DataModels.gamesModel;
import com.example.wordlebattle.R;
import com.example.wordlebattle.adapters.gamesListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    //Data Values
    RecyclerView recyclerView;
    List<gamesModel> gamesModels;
    gamesListAdapter gamesAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Defining Values
        recyclerView = view.findViewById(R.id.rvGames);
        gamesModels = new ArrayList<>();
        gamesAdapter = new gamesListAdapter(getContext(), gamesModels);

        //Binding Recycler to Adapter
        recyclerView.setAdapter(gamesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Games query
        query();

        // Inflate the layout for this fragment
        return view;
    }

    protected void query(){
        ParseQuery<gamesModel> queryGames = ParseQuery.getQuery(gamesModel.class);
        queryGames.addDescendingOrder(gamesModel.Key_CreatedAt);
        queryGames.findInBackground(new FindCallback<gamesModel>() {
            @Override
            public void done(List<gamesModel> objects, ParseException e) {
                if(e != null){
                    Log.e("MainActivity","QueryPost" + e);
                    return;
                }
                for(gamesModel gamesModel : objects){
                    for(String user : gamesModel.getUsers()) {
                        if(ParseUser.getCurrentUser().getObjectId().equals(user))gamesModels.add(gamesModel);
                    }
                }
                gamesAdapter.notifyDataSetChanged();
            }
        });
    }
}