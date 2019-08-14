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
    ArrayList<photopick>mnlist = new ArrayList<>();
    ArrayList<comment>tmpcmt = new ArrayList<>();
    String uid;
    int likes;
    String imageuri;
    String circleimage;
    String picimage;
    Intent newintent;
    String loginpic; //pic of person who logged in app
    String loginemail; // email of preson who logged in app
    similiarAdapter SimiliarAdapter;
    int position;
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
       imageuri = newintent.getStringExtra("imageurl");
       uid = newintent.getStringExtra("uid");
       cmt = (ArrayList<comment>) getIntent().getSerializableExtra("comment");
       mnlist = (ArrayList<photopick>)getIntent().getSerializableExtra("mainlist");
       position = newintent.getIntExtra("pos",0);
        picimage = newintent.getStringExtra("picimageurl");
        loginpic = newintent.getStringExtra("loginpic");
        loginemail = newintent.getStringExtra("loginemail");
       final String useremail = newintent.getStringExtra("useremail");
        likes = newintent.getIntExtra("likes" ,0);
       SimiliarAdapter = new similiarAdapter(CommentActivity.this, cmt, new similiarAdapter.OnItemClickListener() {
           @Override
           public void onItemClick(int position) {

           }
       });
        recyclerView.setAdapter(SimiliarAdapter);
        SimiliarAdapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(new LinearLayoutManager(CommentActivity.this,GridLayoutManager.VERTICAL,false));




     //  Toast.makeText(CommentActivity.this ,imageuri,Toast.LENGTH_SHORT).show();
      save.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              String ans = editText.getText().toString();
              comment cmm = new comment(loginpic,loginemail,ans);
              cmt.add(cmm);
              tmpcmt = cmt;
              SimiliarAdapter.notifyDataSetChanged();
              mnlist.get(position).setArrayList(cmt);
             // Toast.makeText(CommentActivity.this, picimage, Toast.LENGTH_SHORT).show();
            //  Toast.makeText(CommentActivity.this,String.valueOf(cmt.size()),Toast.LENGTH_LONG).show();
             newintent.putExtra("newcomment", cmm.getComt());
             newintent.putExtra("newcommentemail", cmm.getEmail());
             newintent.putExtra("newcommentpic", cmm.getImageurl());
             newintent.putExtra("returnposition", position);
             newintent.putExtra("newuid", uid);
             setResult(199,newintent);


          }
      });


    }


}
