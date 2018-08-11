package com.example.dell.mytimline;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ralph on 18/03/18.
 */

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder> {

    int like;

    interface OnItemClickListener {

        void onItemClick(int position);
    }
    ArrayList<photopick> users=new ArrayList<>();
    ArrayList<String>newlist = new ArrayList<>();
    Context context;
    String img;
    String eml;
    UsersRecyclerAdapter.OnItemClickListener listener;

    public UsersRecyclerAdapter(Context context,String img,String eml, ArrayList<photopick> users, UsersRecyclerAdapter.OnItemClickListener listener){
        this.users = users;
        this.context = context;
        this.listener = listener;
        this.img = img;
        this.eml = eml;
    }

    @Override
    public UsersRecyclerAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.smaple,parent,false);
        UsersRecyclerAdapter.UserViewHolder holder = new UsersRecyclerAdapter.UserViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final UsersRecyclerAdapter.UserViewHolder holder, final int position) {
        //Toast.makeText(context,String.valueOf(users.size()),Toast.LENGTH_SHORT).show();
        final Uri uri = Uri.parse(users.get(position).getImageuri());
         like= users.get(position).getLike();
        holder.likecount.setText(String.valueOf(like));
        final String id = users.get(position).getId();
        Picasso.get().load(uri).fit().into(holder.imageView);
        final Uri picimage = Uri.parse(users.get(position).getPicimageurl());
        if(picimage!=null) {
            Picasso.get().load(picimage).fit().into(holder.circleImageView);
        }
        final String email = users.get(position).getEmail();
        if(email!= null)
        {
            holder.emailtext.setText(email);
        }
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent shareintent = new Intent();
                shareintent.setAction(Intent.ACTION_SEND);
                shareintent.setType("text/plain");
                shareintent.putExtra(Intent.EXTRA_TEXT ,users.get(position).getImageuri());
                context.startActivity(Intent.createChooser(shareintent, "SHARE INTENT"));
            }
        });
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

            }
        });
        holder.likebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int like = users.get(position).getLike();
                like= like+1;
                FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                DocumentReference ref = rootRef.collection("photo").document(id);
                photopick ph = new photopick(users.get(position).imageuri ,like ,id ,users.get(position).getArrayList() ,picimage.toString() ,email);
                Map<String, Object> zeroMap = new HashMap<>();
                zeroMap.put(id ,ph);
                ref.set(ph, SetOptions.merge());
                users.set(position ,ph);
                holder.likecount.setText(String.valueOf(like));

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
         likecount = itemView.findViewById(R.id.likecount);
         comment= itemView.findViewById(R.id.button2);
         share = itemView.findViewById(R.id.shareButton);
         emailtext = itemView.findViewById(R.id.emailshow);
         circleImageView =itemView.findViewById(R.id.CimageView3);
             }
    }
}
