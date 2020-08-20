package com.example.wordlebattle.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordlebattle.DataModels.gamesModel;
import com.example.wordlebattle.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.Map;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.Viewholder> {

    gamesModel gamesModels;
    Context context;
    int count = 1;

    public GamesAdapter(gamesModel gamesModels, Context context){
        this.gamesModels = gamesModels;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gamelayout, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.bind(gamesModels, position);
    }

    @Override
    public int getItemCount() {

        return count;
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        Button complete;
        EditText letter1,letter2,letter3,letter4,letter5;
        String userword, gameword;
        String user = ParseUser.getCurrentUser().getUsername();

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            gameword = gamesModels.getCurrWord();
            complete = itemView.findViewById(R.id.Solve);
            letter1 = itemView.findViewById(R.id.letter1);
            letter2 = itemView.findViewById(R.id.letter2);
            letter3 = itemView.findViewById(R.id.letter3);
            letter4 = itemView.findViewById(R.id.letter4);
            letter5 = itemView.findViewById(R.id.letter5);

            complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    userword = (letter1.getText().toString() + letter2.getText().toString() + letter3.getText().toString() + letter4.getText().toString() + letter5.getText().toString());
                    if(count >= 5) {
                        Toast.makeText(context, "Final attempt has been completed", Toast.LENGTH_LONG).show();
                        //Disables edit function once solved is clicked
                        letter1.setEnabled(false);
                        letter2.setEnabled(false);
                        letter3.setEnabled(false);
                        letter4.setEnabled(false);
                        letter5.setEnabled(false);

                        //Sets user completion status
                        Map<String, Boolean> Completionmap = gamesModels.getPlayers();
                        Completionmap.put(user, true);

                        //Sets Attempts
                        Map<String, Integer> attempts = gamesModels.getMap("attempts");
                        attempts.put(user, count);

                        //SaveCallBack
                        gamesModels.setPlayers(Completionmap);
                        gamesModels.setAttempted(attempts);
                        gamesModels.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e != null){
                                    Log.e("GamesAdapter", "SaveInBackground");
                                    return;
                                }
                                return;
                            }
                        });

                        return;
                    }
                    if(userword.toUpperCase().contentEquals(gameword.toUpperCase())){
                        Toast.makeText(context, "Congrats, you have figured out the word", Toast.LENGTH_LONG).show();

                        //Sets Attempts
                        Map<String, Integer> attempts = gamesModels.getMap("attempts");
                        attempts.put(user, count);

                        //Sets user score
                        Map<String, Integer> Scores = gamesModels.getScores();
                        Scores.put(user, Scores.get(user) + 1);

                        //Sets user completion status
                        Map<String, Boolean> Completionmap = gamesModels.getPlayers();
                        Completionmap.put(user, true);

                        //SaveCallBack
                        gamesModels.setAttempted(attempts);
                        gamesModels.setScores(Scores);
                        gamesModels.setPlayers(Completionmap);
                        gamesModels.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e != null){
                                    Log.e("GamesAdapter", "SaveInBackground");
                                    return;
                                }
                                return;
                            }
                        });

                        //Disables edit function once solved is clicked
                        letter1.setEnabled(false);
                        letter2.setEnabled(false);
                        letter3.setEnabled(false);
                        letter4.setEnabled(false);
                        letter5.setEnabled(false);

                    }else{
                        //Disables edit function once solved is clicked
                        letter1.setEnabled(false);
                        letter2.setEnabled(false);
                        letter3.setEnabled(false);
                        letter4.setEnabled(false);
                        letter5.setEnabled(false);
                        count++;
                        notifyItemInserted(count);
                    }

                }
            });
        }

        public void bind(gamesModel gamesModels, int position) {

        }
    }
}
