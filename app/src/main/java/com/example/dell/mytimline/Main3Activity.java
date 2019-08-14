package com.example.dell.mytimline;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
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
import java.*;
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

import org.w3c.dom.Comment;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class Main3Activity extends AppCompatActivity {

    RecyclerView recyclerView, statusrecycler;
    ImageView imageView, popimage,notification;
    StorageReference mStorageRef;
    Uri selectedImageUri;
    ProgressDialog dialog;
    UsersRecyclerAdapter usersRecyclerAdapter;
    FirebaseStorage mstorage;
    CircleImageView circleImageView;
    Dialog mydialog;
    photopick pick;
    private int RC_PHOTO_PICKER = 101;
    ArrayList<photopick> list = new ArrayList<>();
    ArrayList<String> uploadlist = new ArrayList<>();
    ArrayList<comment>newcmt = new ArrayList<>();
    ArrayList<comment>prevcmt = new ArrayList<>();
    String id;
    String loginpicimageurl;
    String loginuseremail;
    ArrayList<String> ST = new ArrayList<>();
    Intent newintent;
    int prposition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        dialog = new ProgressDialog(Main3Activity.this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        statusrecycler = findViewById(R.id.statusrecycle);
        setSupportActionBar(toolbar);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mstorage = FirebaseStorage.getInstance();
        // progressBar = findViewById(R.id.progress);
        circleImageView = findViewById(R.id.CimageView);
        newintent = getIntent();
        String uid = newintent.getStringExtra("uid");
        final String pass = newintent.getStringExtra("pass");
        loginuseremail = newintent.getStringExtra("email");
        loginpicimageurl = newintent.getStringExtra("imageurl");
        final String name = newintent.getStringExtra("name");
       // circleImageView.setImageURI(Uri.parse(loginpicimageurl));
        Picasso.get().load(loginpicimageurl).into(circleImageView);
         Toast.makeText(Main3Activity.this ,loginpicimageurl ,Toast.LENGTH_SHORT).show();


        imageView = findViewById(R.id.uploadimage);
        notification = findViewById(R.id.notification);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        recyclerView = findViewById(R.id.recycleview);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Main3Activity.this, Main4Activity.class);
                in.putExtra("pass", pass);
                in.putExtra("imageurl", loginpicimageurl);
                in.putExtra("email", loginuseremail);
                in.putExtra("uploadlist", uploadlist);
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
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference coffeeRef = rootRef.collection("photo");
        coffeeRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful())
                    for (DocumentSnapshot document : task.getResult()) {
                        photopick photo = document.toObject(photopick.class);
                        //  Toast.makeText(Main3Activity.this,photo.getEmail(), Toast.LENGTH_LONG).show();
                        ST.add(photo.getPicimageurl());
                        if (Objects.equals(loginuseremail, photo.getEmail())) {
                            uploadlist.add(photo.getImageuri());
                        }


                        if (photo.getImageuri() != null) {
                            list.add(photo);
                        }
                    }
                    Log.d("newfind3", String.valueOf(ST.size()));
                    usersRecyclerAdapter = new UsersRecyclerAdapter(Main3Activity.this, loginpicimageurl, loginuseremail, list, new UsersRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            prposition = position;
                                Intent intent = new Intent(Main3Activity.this , CommentActivity.class);
                               intent.putExtra("mainlist", list);
                               intent.putExtra("pos",position);
                                intent.putExtra("comment",list.get(position).getArrayList());
                              intent.putExtra("uid" , list.get(position).getId());
                                  intent.putExtra("loginpic", loginpicimageurl);
                              intent.putExtra("loginemail" , loginuseremail);
                              intent.putExtra("picimageurl",list.get(position).getPicimageurl());
                             intent.putExtra("useremail",list.get(position).getEmail());
                             intent.putExtra("likes", list.get(position).getLike());
                             intent.putExtra("imageurl" , list.get(position).getImageuri());
                            startActivityForResult(intent ,991);

                          /*  mydialog = new Dialog(Main3Activity.this);
                            mydialog.setContentView(R.layout.poplayout);

                            popimage = (ImageView) mydialog.findViewById(R.id.popimage);
                            Picasso.get().load(list.get(position).getImageuri()).fit().into(popimage);
                            popimage.setEnabled(true);
                            mydialog.show();*/
                        }
                    });
                    //   myRef.setValue(userData);

                    if (list != null) {
                        recyclerView.setAdapter(usersRecyclerAdapter);
                        usersRecyclerAdapter.notifyDataSetChanged();
                        recyclerView.setLayoutManager(new LinearLayoutManager(Main3Activity.this, LinearLayout.VERTICAL, false));
                    }
                    //    mSmoothProgressBar.progressiveStop();

                    //  progressBar.setVisibility(ProgressBar.GONE);

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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 991){
            String newcomment = data.getStringExtra("newcomment");
            String newcommentemail = data.getStringExtra("newcommentemail");
            String newcommentpic = data.getStringExtra("newcommentpic");
            int  returnpostion = data.getIntExtra("returnposition" ,0);
            String nid = data.getStringExtra("newuid");
            comment nwcmt = new comment(newcommentpic,newcommentemail,newcomment);
            prevcmt = list.get(returnpostion).getArrayList();
            prevcmt.add(nwcmt);
         //  Toast.makeText(Main3Activity.this,prevcmt.size(),Toast.LENGTH_LONG).show();
            list.get(prposition).setArrayList(prevcmt);
            final FirebaseFirestore db;
            db = FirebaseFirestore.getInstance();
            DocumentReference ref = db.collection("photo").document(list.get(prposition).getId());
            photopick p = new photopick(list.get(prposition).getImageuri() ,list.get(prposition).getLike(),list.get(prposition).getId(),
                    prevcmt,list.get(prposition).getPicimageurl(),list.get(prposition).getEmail());
            ref.set(p);

     //       newintent.putExtra("newcomment", cmm.getComt());
       //     newintent.putExtra("newcommentemail", cmm.getEmail());
         //   newintent.putExtra("newcommentpic", cmm.getImageurl());
           // newintent.putExtra("returnposition", position);
            //ewintent.putExtra("newuid", uid);
        }
        if (requestCode == RC_PHOTO_PICKER) {
            if (resultCode == RESULT_OK) {
                selectedImageUri = data.getData();
                Toast.makeText(Main3Activity.this, selectedImageUri.toString(), Toast.LENGTH_LONG).show();
                // dialog.setMessage("your image is uploading...");
                //  dialog.show();
                mStorageRef.child(selectedImageUri.getLastPathSegment()).
                        putFile(selectedImageUri).
                        addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Toast.makeText(Main3Activity.this, "success", Toast.LENGTH_SHORT).show();
                                final Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                final FirebaseFirestore db;
                                db = FirebaseFirestore.getInstance();
                                pick = new photopick(downloadUrl.toString(), 0, id);
                                db.collection("photo").add(pick).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        Toast.makeText(Main3Activity.this, "add in database", Toast.LENGTH_SHORT).show();
                                        id = task.getResult().getId();

                                        photopick p = new photopick(downloadUrl.toString(), 0, id, loginpicimageurl, loginuseremail);
                                        list.add(p);
                                        usersRecyclerAdapter.notifyDataSetChanged();
                                        //dialog.show();
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

                        Log.d("whynotfailed", e.toString());
                        Toast.makeText(Main3Activity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

}
}
