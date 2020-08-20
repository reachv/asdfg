package com.example.wordlebattle.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wordlebattle.DataModels.friendsModel;
import com.example.wordlebattle.R;
import com.example.wordlebattle.adapters.friendsrequestadapter;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class FriendsRequestFragment extends Fragment {

    //Data Values
    RecyclerView recyclerView;
    friendsrequestadapter adapter;
    List<friendsModel> requests;


    public FriendsRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends_request, container, false);

        requests = new ArrayList<>();

        ParseQuery<friendsModel> query = ParseQuery.getQuery(friendsModel.class);
        query.whereEqualTo("status", false);
        query.whereEqualTo(friendsModel.receiver, ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<friendsModel>() {
            @Override
            public void done(List<friendsModel> objects, ParseException e) {
                if(e != null){
                    Log.e("friendsrequest", "query");
                    return;
                }
                for(friendsModel friendsModel : objects) {
                    requests.add(friendsModel);
                }
                adapter.notifyDataSetChanged();
            }
        });

        recyclerView = view.findViewById(R.id.request);
        adapter = new friendsrequestadapter(requests);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
        return view;
    }
}