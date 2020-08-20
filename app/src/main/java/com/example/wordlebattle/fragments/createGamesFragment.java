package com.example.wordlebattle.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wordlebattle.DataModels.gamesModel;
import com.example.wordlebattle.R;
import com.example.wordlebattle.friends.playersList;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class createGamesFragment extends Fragment {

    //Data declaration
    EditText currWord;
    TextView addFriends;
    List<String> players;
    Button createGame;
    Map<String, Boolean> completionPlayers;
    Map<String, Integer> attempted, scores;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_games, container, false);

        //Binding data
        players = new ArrayList<>();
        createGame = view.findViewById(R.id.createGames);
        currWord = view.findViewById(R.id.currWord);
        addFriends = view.findViewById(R.id.addFriends);
        attempted = new ArrayMap<>();
        scores = new ArrayMap<>();
        completionPlayers = new ArrayMap<>();


        //Onclick Listener for addFriend
        addFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), playersList.class);
                someActivityResultLauncher.launch(i);
            }
        });

        createGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Empty Check
                if(currWord.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Nothing set to the mystery word", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(players.isEmpty()){
                    Toast.makeText(getActivity(), "No players added", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Setting data values
                for(String user : players){
                    try {
                        attempted.put(ParseUser.getQuery().get(user).getUsername(), 0);
                        scores.put(ParseUser.getQuery().get(user).getUsername(), 0);
                        completionPlayers.put(ParseUser.getQuery().get(user).getUsername(), false);
                    } catch (ParseException e) {
                        Log.e("here", e.toString());
                        e.printStackTrace();
                    }
                }

                //New game creation
                gamesModel gamesModel = new gamesModel();
                gamesModel.setUsers(players);
                gamesModel.setPlayers(completionPlayers);
                gamesModel.setAttempted(attempted);
                gamesModel.setScores(scores);
                gamesModel.setCurrWord(currWord.getText().toString());
                gamesModel.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e != null){
                            Log.e("createGames", "createGames onClickListener" + e);
                            return;
                        }
                    }
                });
                players.clear();
                attempted.clear();
                completionPlayers.clear();
                scores.clear();
                currWord.setText("");
            }
        });

        return view;
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent x = result.getData();
                    players.addAll(x.getStringArrayListExtra("res"));
                    players.add(ParseUser.getCurrentUser().getObjectId());
                }
                else {
                    Log.w("createGamesFragment", "Unknown call to onActivityResult");
                }
            }
        }
    );

}