package com.example.wordlebattle.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordlebattle.DataModels.friendsModel;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;
import java.util.Map;

public class add_remove_adapter extends RecyclerView.Adapter<add_remove_adapter.Viewholder> {

    List<ParseUser> usernames;
    Map<String, String> users;
    Context context;


    public add_remove_adapter(List<ParseUser> usernames, Map<String, String> users, Context context){
        this.usernames = usernames;
        this.users = users;
        this.context = context;

    }

    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        ParseUser user = usernames.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return usernames.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        TextView display;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            display = itemView.findViewById(android.R.id.text1);


        }

        public void bind(ParseUser user) {
            display.setText(user.getUsername());
            display.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view){

                    //Creating a friend request
                    friendsModel friendsModel = new friendsModel();
                    friendsModel.setReceiver(user);
                    friendsModel.setSender(ParseUser.getCurrentUser());
                    friendsModel.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e != null) Log.e("AddRemoveAdapter", "Parse create friendsModel" + e);else{
                                Toast.makeText(context, "Successfully added", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
    }
}
