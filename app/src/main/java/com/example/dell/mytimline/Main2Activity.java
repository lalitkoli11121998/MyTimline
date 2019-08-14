package com.example.dell.mytimline;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.speech.tts.TextToSpeech.QUEUE_FLUSH;
import static com.example.dell.mytimline.R.layout.activity_main2;

public class Main2Activity extends AppCompatActivity {

    EditText name, email,pass, phone;
    TextView aadhar;
    Button signup;
    private int RC_PHOTO_PICKER=101;
    private static final int REQ_CODE_SPEECH_INPUT = 168;
    Calendar myCalendar;
    StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    Uri selectedImageUri;
    String phone_no;
    private  TextToSpeech mTTs;
    ImageView DB;
    String nametext;
    ProgressDialog dialog;
     String emailText;
    String password;
    FirebaseStorage mstorage;
    FirebaseFirestore db;
    CountDownTimer countDownTimer;
    public String s2;
    int year1;
    int  day,month1;
    String adhaar_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_main2);
        DB = findViewById(R.id.imageView9);
        name = findViewById(R.id.Nametext2);
        dialog= new ProgressDialog(Main2Activity.this);
        mstorage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        email = findViewById(R.id.Emailtext2);
        pass= findViewById(R.id.passwordtext2);
        aadhar = findViewById(R.id.adhar_number);
        phone = findViewById(R.id.Phonetext);
        signup =findViewById(R.id.signup_button);
        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        DB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(Main2Activity.this,date , myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 emailText=email.getText().toString();
                password= pass.getText().toString();
                if(password.length()<6)
                {
                   Toast.makeText(Main2Activity.this ,"password should atleast 6 character" ,Toast.LENGTH_SHORT).show();
                }
                 adhaar_no =aadhar.getText().toString();
                 phone_no = phone.getText().toString();
                 nametext = name.getText().toString();

                final UserData userData=new UserData(emailText,password,nametext ,adhaar_no ,phone_no);

                mAuth.createUserWithEmailAndPassword(emailText ,password).
                        addOnCompleteListener(Main2Activity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){

                                    Toast.makeText(Main2Activity.this, "successfull", Toast.LENGTH_SHORT).show();
                                    AlertDialog.Builder builder;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        builder = new AlertDialog.Builder(Main2Activity.this, android.R.style.Theme_Material_Dialog_Alert);
                                    } else {
                                        builder = new AlertDialog.Builder(Main2Activity.this);
                                    }
                                    builder.setTitle("Choose pic")
                                            .setMessage("Upload your pic")
                                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    // continue with delete

                                                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                                    intent.setType("image/*");

                                                    Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                                    pickIntent.setType("image/*");

                                                    Intent chooserIntent = Intent.createChooser(intent, "Select Image");
                                                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

                                                    startActivityForResult(chooserIntent, RC_PHOTO_PICKER);

                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();

                                }
                                else{
                                    Log.d("failer", String.valueOf(task.getException().getStackTrace()));
                                    Toast.makeText(Main2Activity.this ,"not seccessfull", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });


    }
    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (mTTs != null) {
            mTTs.stop();
            mTTs.shutdown();
        }
        super.onDestroy();
    }
    public  void updateLabel() {


        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        year1 = myCalendar.get(Calendar.YEAR);
        month1 = myCalendar.get(Calendar.MONTH);
        day = myCalendar.get(Calendar.DAY_OF_MONTH);
        aadhar.setText(sdf.format(myCalendar.getTime()));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_PHOTO_PICKER) {
            if (resultCode == RESULT_OK) {
                selectedImageUri = data.getData();
                dialog.setMessage("uploading image..");
                dialog.show();
                mStorageRef.child(selectedImageUri.getLastPathSegment()).
                        putFile(selectedImageUri).
                        addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Toast.makeText(Main2Activity.this ,"success",Toast.LENGTH_SHORT).show();
                                final Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                final FirebaseFirestore db;
                                db = FirebaseFirestore.getInstance();
                                UserData user  = new UserData(emailText,password,nametext,adhaar_no,phone_no, downloadUrl.toString());
                                db.collection(emailText).add(user).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        Toast.makeText(Main2Activity.this ,"add in database",Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        String id = task.getResult().getId();
                                        Intent n = new Intent(Main2Activity.this, Main3Activity.class);
                                        n.putExtra("uid", id);
                                        n.putExtra("email", emailText);
                                        n.putExtra("pass", aadhar.getText());
                                        n.putExtra("imageurl", downloadUrl.toString());
                                        startActivity(n);
                                       // photopick p = new photopick(downloadUrl.toString() ,0,id);
                                      //  list.add(p);
                                      //  usersRecyclerAdapter.notifyDataSetChanged();
                                      //  DocumentReference ref = db.collection("photo").document(id);
                                       // Map<String, Object> zeroMap = new HashMap<>();
                                        //zeroMap.put(id ,p);
                                        //ref.set(p);

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
                        Toast.makeText(Main2Activity.this , "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }


}
