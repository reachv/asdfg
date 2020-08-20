package com.example.wordlebattle.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wordlebattle.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.List;

public class friendsAdapter extends RecyclerView.Adapter<friendsAdapter.Viewholder> {
    public interface OnClickListener{
         void onCLick(int position);
    }


    List<ParseUser> users;
    OnClickListener onClickListener;

    public friendsAdapter(List<ParseUser> users, OnClickListener onClickListener){
        this.users = users;
        this.onClickListener = onClickListener;
    }
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        ParseUser User = users.get(position);
        holder.bind(User);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        TextView username;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(android.R.id.text1);
        }

        public void bind(ParseUser user) {

            username.setText(user.getUsername());

            username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onCLick(getAdapterPosition());
                }
            });

        }
    }
}
