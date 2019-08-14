package com.example.dell.mytimline;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dell on 8/18/2018.
 */

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.UserViewHolder> {
    int like;

    interface OnItemClickListener {

        void onItemClick(int position);
    }
    ArrayList<photopick> users=new ArrayList<>();
    ArrayList<String>newlist = new ArrayList<>();
    Context context;
    String img;
    String eml;
    StatusAdapter.OnItemClickListener listener;

    public StatusAdapter(Context context,String img,String eml, ArrayList<photopick> users, StatusAdapter.OnItemClickListener listener){
        this.users = users;
        this.context = context;
        this.listener = listener;
        this.img = img;
        this.eml = eml;
    }

    @Override
    public StatusAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.smaple,parent,false);
        StatusAdapter.UserViewHolder holder = new StatusAdapter.UserViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final StatusAdapter.UserViewHolder holder, final int position) {
        //Toast.makeText(context,String.valueOf(users.size()),Toast.LENGTH_SHORT).show();
        final Uri uri = Uri.parse(users.get(position).getImageuri());
        like= users.get(position).getLike();
        holder.likecount.setText(String.valueOf(like));
        final String id = users.get(position).getId();
        Picasso.get().load(uri).fit().into(holder.imageView);
        final Uri picimage = Uri.parse(users.get(position).getPicimageurl());
        Intent intent = new Intent(context ,CommentActivity.class);
        //  Toast.makeText(context,users.get(position).getArrayList().get(0),Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        intent.putExtra("uid", id);
        intent.putExtra("email",eml);
        intent.putExtra("useremail", users.get(position).getEmail());
        intent.putExtra("picimage",img);// image of the user who comment on the pic

        intent.putExtra("comment", users.get(position).getArrayList());
        intent.putExtra("likes",like);
        intent.putExtra("newlist", newlist);
        intent.putExtra("circleimage" , users.get(position).getPicimageurl());// pic circle image
        String imu = uri.toString();
        intent.putExtra("image" ,imu);//image of the pic on which user comment

        context.startActivity(intent);
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.likebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // Glide.with(context.getApplicationContext()).load(uri).into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
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
        ImageView imageView;
        ImageButton likebutton,comment,share;
        TextView likecount,emailtext;
        CircleImageView circleImageView;


        public UserViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagecard);
            likebutton = itemView.findViewById(R.id.like);
            likecount = itemView.findViewById(R.id.likecounttext);
            comment= itemView.findViewById(R.id.button2);
            share = itemView.findViewById(R.id.shareButton);
            emailtext = itemView.findViewById(R.id.emailshow);
            circleImageView =itemView.findViewById(R.id.CimageView3);
        }
    }
}

