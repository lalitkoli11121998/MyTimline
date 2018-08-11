package com.example.dell.mytimline;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dell on 3/27/2018.
 */

public class similiarAdapter extends RecyclerView.Adapter<similiarAdapter.UserViewHolder> {

    interface OnItemClickListener {

        void onItemClick(int position);
    }

    ArrayList<comment> users = new ArrayList<>();
    Context context;
    similiarAdapter.OnItemClickListener listener;

    public similiarAdapter(Context context, ArrayList<comment> users, similiarAdapter.OnItemClickListener listener) {
        this.users = users;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public similiarAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.sample2, parent, false);
        similiarAdapter.UserViewHolder holder = new similiarAdapter.UserViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final similiarAdapter.UserViewHolder holder, int position) {
        String comment = users.get(position).getComt();
        String email =users.get(position).getEmail();
        Uri image = Uri.parse(users.get(position).getImageurl());
        Picasso.get().load(image).into(holder.imageview);
        holder.email.setText(email);
        holder.title.setText(comment);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        TextView email,title;
        CircleImageView imageview;
        View itemView;


        public UserViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            title = itemView.findViewById(R.id.textcomment);
            email= itemView.findViewById(R.id.emailshow);
            imageview =itemView.findViewById(R.id.CimageView3);


        }
    }
}

