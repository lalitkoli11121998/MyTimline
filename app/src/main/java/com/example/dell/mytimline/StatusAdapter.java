package com.example.dell.mytimline;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dell on 8/18/2018.
 */

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.UserViewHolder> {
    interface OnItemClickListener {

        void onItemClick(int position);
    }

    ArrayList<String> users=new ArrayList<>();
    Context context;
    StatusAdapter.OnItemClickListener listener;

    public StatusAdapter(Context context, ArrayList<String> users, StatusAdapter.OnItemClickListener listener){
        this.users = users;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public StatusAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.status,parent,false);
        StatusAdapter.UserViewHolder holder = new StatusAdapter.UserViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final StatusAdapter.UserViewHolder holder, int position) {
        final String s = users.get(position);
        final Uri uri = Uri.parse(s);
        Picasso.get().load(uri).into(holder.circleImageView);
        Log.d("findf",s);
        holder.circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return  users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;

        public UserViewHolder(View itemView) {
            super(itemView);
            circleImageView= itemView.findViewById(R.id.statuscircle);

        }
    }
}

