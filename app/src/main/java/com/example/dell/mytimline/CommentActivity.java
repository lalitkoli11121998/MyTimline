package com.example.dell.mytimline;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

public class CommentActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView save;
    EditText editText;
    ArrayList<comment>cmt = new ArrayList<>();
    String uid;
    int likes;
    String imageuri;
    String circleimage;
    String picimage;
    Intent newintent;
    similiarAdapter SimiliarAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        save = findViewById(R.id.imageView7);
        recyclerView = findViewById(R.id.commentrecycle);
        editText = findViewById(R.id.editText1);

       newintent = getIntent();
       uid = newintent.getStringExtra("uid");
       cmt = (ArrayList<comment>) getIntent().getSerializableExtra("comment");
        picimage = newintent.getStringExtra("picimage"); // image of the  user who comment on the pic
       final String email = newintent.getStringExtra("email");
       final String useremail = newintent.getStringExtra("useremail");
       SimiliarAdapter = new similiarAdapter(CommentActivity.this, cmt, new similiarAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(int position) {

           }
       });
        recyclerView.setAdapter(SimiliarAdapter);
        SimiliarAdapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new LinearLayoutManager(CommentActivity.this,GridLayoutManager.VERTICAL,false));

       likes = newintent.getIntExtra("likes" ,0);
       imageuri =  newintent.getStringExtra("image");//image on which user comment
        circleimage = newintent.getStringExtra("circleimage");


     //  Toast.makeText(CommentActivity.this ,imageuri,Toast.LENGTH_SHORT).show();
      save.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String ans = editText.getText().toString();
              comment cmm = new comment(picimage, email ,ans);
              cmt.add(cmm);
                SimiliarAdapter.notifyDataSetChanged();
              final FirebaseFirestore db;
              db = FirebaseFirestore.getInstance();
               DocumentReference ref = db.collection("photo").document(uid);
               photopick p = new photopick(imageuri ,likes,uid,cmt ,circleimage, useremail);
               //
              //photopick ph = new photopick(users.get(position).imageuri ,like ,id ,picimage.toString() ,email);
              ref.set(p);
          }
      });


    }


}
