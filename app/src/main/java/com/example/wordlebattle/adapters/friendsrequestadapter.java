package com.example.wordlebattle.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordlebattle.DataModels.friendsModel;
import com.example.wordlebattle.R;
import com.parse.DeleteCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class friendsrequestadapter extends RecyclerView.Adapter<friendsrequestadapter.Viewholder> {

    List<friendsModel> requests;

    public friendsrequestadapter(List<friendsModel> requests){
        this.requests = requests;
    }

    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friendsrequest, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        friendsModel receive = requests.get(position);
        holder.bind(receive, position);
    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        TextView sender;
        Button accept, reject;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            accept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.reject);
            sender = itemView.findViewById(R.id.sender);

        }

        public void bind(friendsModel receive, int position) {
            try {
                sender.setText(receive.getSender().fetch().getUsername());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    receive.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e != null){
                                Log.e("friendsrequestAdapter", "rejectonclick");
                                return;
                            }
                            Toast.makeText(view.getContext(), "Successfully rejected", Toast.LENGTH_SHORT).show();
                            requests.remove(position);
                            notifyItemRemoved(position);
                        }
                    });
                }
            });

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ParseUser receiver = ParseUser.getCurrentUser();
                    List<ParseUser> friendsList = new ArrayList<>();
                    if(receive.getReceiver().getList("FriendsList") != null)friendsList.addAll(receive.getReceiver().getList("FriendsList"));
                    friendsList.add(receive.getSender());
                    receiver.put("FriendsList", friendsList);
                    receiver.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e != null){
                                Log.e("friendrequestAdapter", "Recieveronclick" + e);
                                return;
                            }
                        }
                    });
                    receive.setStatus(true);
                    receive.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e != null)Log.e("friendrequestAdapter", "Recieveronclick" + e);
                        }
                    });
                    Toast.makeText(view.getContext(), "Successfully added", Toast.LENGTH_SHORT).show();
                    requests.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }
    }
}
