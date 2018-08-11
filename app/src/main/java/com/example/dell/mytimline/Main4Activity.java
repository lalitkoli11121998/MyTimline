package com.example.dell.mytimline;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main4Activity extends AppCompatActivity {

    CircleImageView circleImageView;
    Intent i;
    TextView t1,t2,t3;
    Button logout;
    ArrayList<String>uploadimage = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        circleImageView = findViewById(R.id.CimageView2);
        logout = findViewById(R.id.logout);
        t1= findViewById(R.id.emailview);
        t2= findViewById(R.id.NmeView2);
        t3= findViewById(R.id.passwordview);
        i = getIntent();
       // uploadimage = (ArrayList<String>) getIntent().getSerializableExtra("uploadimage");
      //  Toast.makeText(Main4Activity.this ,uploadimage.size(), Toast.LENGTH_SHORT).show();
        String imageurl = i.getStringExtra("imageurl");
        String name = i.getStringExtra("name");
        String pass = i.getStringExtra("pass");
        String email = i.getStringExtra("email");
        circleImageView.setImageURI(Uri.parse(imageurl));
        Picasso.get().load(imageurl).into(circleImageView);
        t1.setText(email);
        t2.setText(name);
        t3.setText(pass);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intt = new Intent(Main4Activity.this ,MainActivity.class);
                Toast.makeText(Main4Activity.this ,"Successfull logout", Toast.LENGTH_SHORT).show();
                startActivity(intt);
            }
        });

    }

}
