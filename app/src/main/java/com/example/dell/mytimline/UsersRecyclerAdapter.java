package com.example.dell.mytimline;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dell on 7/5/2019.
 */

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UsersViewHolder> {
    interface OnItemClickListener {

        void onItemClick(int position);
    }

    ArrayList<comment> users = new ArrayList<>();
   ArrayList<photopick>list = new ArrayList<>();
    Context context;
    UsersRecyclerAdapter.OnItemClickListener listener;
    String loginpicimageurl;
    String loginuseremail;
    int likes;
    String  ud;
    public UsersRecyclerAdapter(Context context,String loginpicimageurl,String loginuseremail, ArrayList<photopick>list, UsersRecyclerAdapter.OnItemClickListener listener) {
        this.loginuseremail = loginuseremail;
        this.list = list;
        this.loginpicimageurl = loginpicimageurl;
        this.context = context;
        this.listener = listener;
    }
    public UsersRecyclerAdapter.UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.smaple,parent,false);
        UsersRecyclerAdapter.UsersViewHolder holder = new UsersRecyclerAdapter.UsersViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final UsersRecyclerAdapter.UsersViewHolder holder, final int position) {
           final String circleimage = list.get(position).getPicimageurl();
           users = list.get(position).getArrayList();
           ud = list.get(position).getId();
        likes  = list.get(position).getLike();
        Picasso.get().load(circleimage).into(holder.circleImageView);
        String emailname = list.get(position).getEmail();
        holder.pictext.setText(emailname);
        final String imageurl = list.get(position).getImageuri();
        Picasso.get().load(imageurl).into(holder.Profileimage);
        holder.liketext.setText(String.valueOf(likes));
        holder.commentimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });

        holder.sharebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appShareIntent = new Intent(Intent.ACTION_SEND);
                appShareIntent.setType("text/plain");
                appShareIntent.putExtra(Intent.EXTRA_TEXT, list.get(position).getImageuri());
                context.startActivity(appShareIntent);
            }
        });
        holder.likeimageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int likkes  = list.get(position).getLike();
               likkes = likkes + 1;
               holder.liketext.setText(String.valueOf(likkes));
                Toast.makeText(context,String.valueOf(list.get(position).getArrayList().size()),Toast.LENGTH_LONG).show();
                final FirebaseFirestore db;
                db = FirebaseFirestore.getInstance();
                DocumentReference ref = db.collection("photo").document(list.get(position).getId());
                photopick p = new photopick(list.get(position).getImageuri() ,likkes,list.get(position).getId(),
                        list.get(position).getArrayList(),list.get(position).getPicimageurl(),list.get(position).getEmail());
                list.set(position,p);
                //photopick ph = new photopick(users.get(position).imageuri ,like ,id ,picimage.toString() ,email);
               ref.set(p);
               // holder.liketext.setText(likes);*/

            }
        });
    }

    @Override
    public int getItemCount() { return list.size();}


    public class UsersViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView pictext,liketext;
        ImageView Profileimage;
        ImageButton likeimageButton,commentimage,sharebutton;
        public UsersViewHolder(View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.CimageView3);
            pictext = itemView.findViewById(R.id.emailshow);
            Profileimage = itemView.findViewById(R.id.imagecard);
            liketext = itemView.findViewById(R.id.likecounttext);
            likeimageButton = itemView.findViewById(R.id.like);
            commentimage = itemView.findViewById(R.id.button2);
            sharebutton = itemView.findViewById(R.id.shareButton);

        }
    }
}
