package com.example.wordlebattle.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.wordlebattle.R;
import com.example.wordlebattle.adapters.add_remove_adapter;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class friendsFragment extends Fragment {

    //Data declaration
    List<Integer> remove;
    Map<String, String> users;
    List<ParseUser> username;
    List<ParseUser> usernamesFullList;
    EditText name;
    add_remove_adapter adapter;
    RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends, container, false);

        //Initialization
        name = view.findViewById(R.id.friendusername);
        recyclerView = view.findViewById(R.id.friendsfragmentrecycler);
        username = new ArrayList<>();
        usernamesFullList = new ArrayList<>();
        remove = new ArrayList<>();
        users = new ArrayMap<>();

        adapter = new add_remove_adapter(username, users, getContext());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e != null) {
                    Log.e("ADD/REMOVEFRIENDS", "QUERYUSER" + e);
                    return;
                }
                for (int i = 0; i < objects.size(); i++) {
                    if (!objects.get(i).getObjectId().equals(ParseUser.getCurrentUser().getObjectId())) {
                        usernamesFullList.add(objects.get(i));
                        users.put(objects.get(i).getUsername(), objects.get(i).toString());
                    }
                }
                username.addAll(usernamesFullList);
                adapter.notifyDataSetChanged();
            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                username.clear();
                if(charSequence.toString().isEmpty()) {
                    username.addAll(usernamesFullList);
                    adapter.notifyDataSetChanged();
                }
                for(int x = 0; x < usernamesFullList.size(); x++) {
                    if(!usernamesFullList.get(x).getUsername().contains(charSequence))remove.add(x);
                }
                for(Integer x : remove) {
                    username.remove(x);
                    adapter.notifyItemRemoved(x);
                }
                remove.clear();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return view;
    }
}