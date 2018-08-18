package com.example.dell.mytimline;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dell on 8/18/2018.
 */

public class MyuploadAdapter extends RecyclerView.Adapter<MyuploadAdapter.UserViewHolder> {
    interface OnItemClickListener {

        void onItemClick(int position);
    }

    ArrayList<String> users=new ArrayList<>();
    Context context;
    MyuploadAdapter.OnItemClickListener listener;

    public MyuploadAdapter(Context context, ArrayList<String> users, MyuploadAdapter.OnItemClickListener listener){
        this.users = users;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyuploadAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.simple3,parent,false);
        MyuploadAdapter.UserViewHolder holder = new MyuploadAdapter.UserViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyuploadAdapter.UserViewHolder holder, int position) {
        final String s = users.get(position);
        final Uri uri = Uri.parse(s);
        Picasso.get().load(uri).into(holder.imageView);
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


        public UserViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.uploadimageview);

        }
    }
}

