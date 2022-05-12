package com.example.watch_together;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    Context context;
    ArrayList<User> list;

    public SearchAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_search, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        User user = list.get(position);
        holder.username.setText(user.getUsername());
        holder.email.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView username, email;
        ImageView avatar;
        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username_label);
            email = itemView.findViewById(R.id.email_label);
            avatar = itemView.findViewById(R.id.avatar);
        }
    }
}
