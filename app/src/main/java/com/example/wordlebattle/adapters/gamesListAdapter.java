package com.example.wordlebattle.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordlebattle.DataModels.gamesModel;
import com.example.wordlebattle.Games.Game;
import com.example.wordlebattle.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;
import java.util.Map;

public class gamesListAdapter extends RecyclerView.Adapter<gamesListAdapter.Viewholder>{

    List<gamesModel> gamesModels;
    Context context;
    ParseUser user = ParseUser.getCurrentUser();

    public gamesListAdapter(Context context, List<gamesModel> gamesModels){
        this.gamesModels = gamesModels;
        this.context = context;
    }
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_games, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        gamesModel gamesModel = gamesModels.get(position);
        holder.bind(gamesModel);
    }

    @Override
    public int getItemCount() {
        return gamesModels.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        //Data Models
        TextView players;
        TextView scores;
        TextView gamenum;
        RelativeLayout relativeLayout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            //Binding models to xlm values
            gamenum = itemView.findViewById(R.id.tvGames);
            relativeLayout = itemView.findViewById(R.id.RLGames);
            players = itemView.findViewById(R.id.Player);
            scores = itemView.findViewById(R.id.score);
        }

        public void bind(gamesModel gamesModel) {
            //Players
            players.setText(gamesModel.getPlayers().toString());

            //Scores
            scores.setText(gamesModel.getScores().toString());

            //GameNumber
            gamenum.setText("Game Number: " + getAdapterPosition());

            //On games click listener
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, Game.class);
                    i.putExtra("GamesModel", gamesModel);
                    context.startActivity(i);
                }
            });

            relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    //Removes player entirely from game;
                    List<String> users = gamesModel.getUsers();
                    for(int i = 0; i < users.size(); i++){
                        if(users.get(i).equals(ParseUser.getCurrentUser().getObjectId())){
                            users.remove(i);
                            break;
                        }
                    }
                    Map<String, Boolean> players = gamesModel.getPlayers();
                    players.remove(user);
                    Map<String, Integer> score = gamesModel.getScores();
                    score.remove(user);
                    Map<String, Integer> attempt = gamesModel.getAttempts();
                    attempt.remove(user);

                    //sets new values for game and SaveCallback
                    gamesModel.setUsers(users);
                    gamesModel.setScores(score);
                    gamesModel.setPlayers(players);
                    gamesModel.setAttempted(attempt);
                    gamesModel.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e != null){
                                Log.e("gamesListAdapter", "LongClickListener");
                                return;
                            }
                        }
                    });

                    return true;
                }
            });

        }
    }
}
