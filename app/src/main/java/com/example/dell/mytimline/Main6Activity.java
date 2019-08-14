package com.example.dell.mytimline;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;

public class Main6Activity extends AppCompatActivity {

    private ImageButton btnSpeak;
ProgressDialog dialog;
    public static String TAG="LoginStatus";
    private FirebaseAuth mAuth;
    String emailtext;
    String passtext,adhar,imageurl,nametext;
    private TextToSpeech myTTS;
    private int MY_DATA_CHECK_CODE = 0;
    private static final int REQ_CODE_SPEECH_INPUT = 100;

    CountDownTimer countDownTimer;
    String email = "lalitkoli11121998@gmail.com";
    String pass = "123456";
    final String s = "Speak login to get login into your account.....";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_main6);
        mAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(Main6Activity.this);

        myTTS = new TextToSpeech(Main6Activity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
               // Toast.makeText(Main6Activity.this , s , Toast.LENGTH_SHORT).show();
                myTTS.speak(s, TextToSpeech.QUEUE_FLUSH, null);
               // myTTS.speak(s2,TextToSpeech.QUEUE_FLUSH,null);
            }
        });
       countDownTimer =  new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                startVoiceInput();
            }
        }.start();


    }
    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    dialog.setMessage("login....");
                    dialog.show();
                    mAuth.signInWithEmailAndPassword(email, pass)

                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        final FirebaseUser user = mAuth.getCurrentUser();
                                        if (user != null)
                                            Toast.makeText(Main6Activity.this, "successfull login", Toast.LENGTH_SHORT).show();

                                        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                                        CollectionReference coffeeRef = rootRef.collection(user.getEmail());
                                        coffeeRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (DocumentSnapshot document : task.getResult()) {
                                                        emailtext = document.getString("email");
                                                        passtext = document.getString("password");
                                                        adhar = document.getString("adhar_no");
                                                        imageurl = document.getString("imageurl");
                                                        nametext = document.getString("name");


                                                    }

                                                    Intent in = new Intent(Main6Activity.this, Main3Activity.class);
                                                    in.putExtra("imageurl", imageurl);
                                                    in.putExtra("uid", user.getUid());
                                                    in.putExtra("email", emailtext);
                                                    in.putExtra("pass", adhar);
                                                    in.putExtra("name", nametext);
                                                    dialog.dismiss();
                                                    startActivity(in);
                                                }
                                            }
                                        });
//                            updateUI(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        dialog.dismiss();
                                        Toast.makeText(Main6Activity.this, "User is already registered",
                                                Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                                    }

                                    // ...
                                }
                            });


                }
                break;
            }

        }
    }
}
