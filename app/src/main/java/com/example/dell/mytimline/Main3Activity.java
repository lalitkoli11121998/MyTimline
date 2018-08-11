package com.example.dell.mytimline;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class Main3Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imageView,popimage;
    StorageReference mStorageRef;
    SmoothProgressBar mSmoothProgressBar;
    Uri selectedImageUri;
    UsersRecyclerAdapter usersRecyclerAdapter;
    FirebaseStorage mstorage;
    CircleImageView circleImageView;
    ProgressBar progressBar;
    Dialog mydialog;
    DatabaseReference myRef;
    photopick pick;
    private int RC_PHOTO_PICKER=101;
    ArrayList<photopick>list = new ArrayList<>();
    ArrayList<String> uploadlist = new ArrayList<>();
    String id;
    UserData userData;
    String imageurl;
    String email;
    Intent newintent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mstorage = FirebaseStorage.getInstance();
       // progressBar = findViewById(R.id.progress);
        circleImageView = findViewById(R.id.CimageView);
        newintent = getIntent();
        String uid = newintent.getStringExtra("uid");
        final String pass = newintent.getStringExtra("pass");
         email = newintent.getStringExtra("email");
         imageurl = newintent.getStringExtra("imageurl");
         Toast.makeText(Main3Activity.this ,imageurl,Toast.LENGTH_SHORT).show();
        final String name = newintent.getStringExtra("name");
        circleImageView.setImageURI(Uri.parse(imageurl));
        Picasso.get().load(imageurl).into(circleImageView);
       // Toast.makeText(Main3Activity.this ,uid ,Toast.LENGTH_SHORT).show();



        imageView = findViewById(R.id.uploadimage);
        recyclerView = findViewById(R.id.recycleview);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Main3Activity.this, Main4Activity.class);
                in.putExtra("pass", pass);
                in.putExtra("imageurl", imageurl);
                in.putExtra("email", email);
                in.putExtra("uploadlilst", uploadlist);
                in.putExtra("name", name);
                startActivity(in);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Takeimage();

            }
        });


    fetchimagefromdatabase();
    }


    private void fetchimagefromdatabase() {

       // mSmoothProgressBar.progressiveStart();
       // progressBar.setVisibility(ProgressBar.VISIBLE);
      //  progressBar.setProgress(0);
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference coffeeRef = rootRef.collection("photo");
        coffeeRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d("herhe", task.getResult().toString());
                    for (DocumentSnapshot document : task.getResult()) {

                       photopick photo = document.toObject(photopick.class);
                       if(photo.getEmail() == email)
                        {
                            uploadlist.add(photo.getImageuri());
                        }
                       if(photo.getImageuri()!= null) {
                           list.add(photo);
                       }
                    }
                    Log.d("size", String.valueOf(list.size()));
                usersRecyclerAdapter = new UsersRecyclerAdapter(Main3Activity.this,imageurl ,email, list, new UsersRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {

                        mydialog = new Dialog(Main3Activity.this);
                        mydialog.setContentView(R.layout.poplayout);

                        popimage = (ImageView)mydialog.findViewById(R.id.popimage);
                        Picasso.get().load(list.get(position).getImageuri()).fit().into(popimage);
                        popimage.setEnabled(true);
                        mydialog.show();
                    }
                });
                    //   myRef.setValue(userData);

                if(list != null) {
                    recyclerView.setAdapter(usersRecyclerAdapter);
                    usersRecyclerAdapter.notifyDataSetChanged();
                    recyclerView.setLayoutManager(new LinearLayoutManager(Main3Activity.this, LinearLayout.VERTICAL, false));
                }
                //    mSmoothProgressBar.progressiveStop();

                  //  progressBar.setVisibility(ProgressBar.GONE);

                }
            }
        });

    }

    public void Takeimage() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(intent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, RC_PHOTO_PICKER);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_PHOTO_PICKER) {
            if (resultCode == RESULT_OK) {
                selectedImageUri = data.getData();
                mStorageRef.child(selectedImageUri.getLastPathSegment()).
                        putFile(selectedImageUri).
                        addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                               Toast.makeText(Main3Activity.this ,"success",Toast.LENGTH_SHORT).show();
                                final Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                final FirebaseFirestore db;
                                db = FirebaseFirestore.getInstance();
                                 pick = new photopick(downloadUrl.toString(),0,id);
                                db.collection("photo").add(pick).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        Toast.makeText(Main3Activity.this ,"add in database",Toast.LENGTH_SHORT).show();
                                                   id = task.getResult().getId();

                                        photopick p = new photopick(downloadUrl.toString() ,0,id ,imageurl,email );
                                        list.add(p);
                                        usersRecyclerAdapter.notifyDataSetChanged();
                                        DocumentReference ref = db.collection("photo").document(id);
                                        ref.set(p);

                                    }
                                }).
                                        addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d("failtoaddd", e.toString());
                                            }
                                        });


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d("whynotfailed" ,e.toString());
                        Toast.makeText(Main3Activity.this , "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
