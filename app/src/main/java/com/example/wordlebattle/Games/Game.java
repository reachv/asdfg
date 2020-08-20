package com.example.wordlebattle.Games;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wordlebattle.DataModels.gamesModel;
import com.example.wordlebattle.R;
import com.example.wordlebattle.adapters.GamesAdapter;
import com.parse.ParseUser;

import java.util.Map;

public class Game extends AppCompatActivity {

    //Data Assignment
    RecyclerView recyclerView;
    gamesModel gamesModel;
    GamesAdapter adapter;
    Button done;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        done = findViewById(R.id.Done);
        status = findViewById(R.id.annouce);
        recyclerView = findViewById(R.id.games);
        gamesModel = getIntent().getParcelableExtra("GamesModel");
        adapter = new GamesAdapter(gamesModel, getApplicationContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        if(gamesModel.getPlayers().get(ParseUser.getCurrentUser().getUsername())) {
            done.setVisibility(View.VISIBLE);
            done.setEnabled(true);
            recyclerView.setVisibility(View.INVISIBLE);
            status.setVisibility(View.VISIBLE);
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }
}