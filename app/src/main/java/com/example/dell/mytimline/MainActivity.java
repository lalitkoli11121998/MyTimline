package com.example.dell.mytimline;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    Button signIn ,signout;
   EditText name, email,pass;
    public static String TAG="LoginStatus";
    private FirebaseAuth mAuth;
    String emailtext ;
    String passtext;
    TextView t;
    String idu;
    ProgressDialog dialog;
    String adhar;
    String imageurl;
    Dialog mydialog;
    String nametext;
    String ph;
   TextView signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signIn = findViewById(R.id.signIn_button);
        mAuth = FirebaseAuth.getInstance();
        dialog =new ProgressDialog(this);
        signout = findViewById(R.id.signout_button);

        name = findViewById(R.id.Nametext);
        email = findViewById(R.id.Emailtext);
        pass = findViewById(R.id.passwordtext);
        signup = findViewById(R.id.textView);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this ,Main2Activity.class);
                startActivity(intent);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInUser();
            }
        });


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this ,"Your password on your screen", Toast.LENGTH_SHORT).show();
                String s = email.getText().toString();
                if (s == null) {
                    Toast.makeText(MainActivity.this, "provide your email", Toast.LENGTH_SHORT).show();
                } else {
                    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
                    CollectionReference coffeeRef = rootRef.collection(s);
                    coffeeRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    idu = document.getId();
                                    emailtext = document.getString("email");
                                    passtext = document.getString("password");
                                    adhar = document.getString("adhar_no");
                                    imageurl = document.getString("imageurl");
                                    nametext = document.getString("name");
                                    ph = document.getString("phone");

                                }

                                mydialog = new Dialog(MainActivity.this);
                                mydialog.setContentView(R.layout.showpassword);

                                t = (TextView) mydialog.findViewById(R.id.textView2);
                                t.setText(passtext);
                                t.setEnabled(true);
                                mydialog.show();
                            }
                        }
                    });
                }
            }
        });

    }

    private void SignInUser() {
        dialog.setMessage("login....");
        dialog.show();
        final String emailText=email.getText().toString();
        String password=pass.getText().toString();
        mAuth.signInWithEmailAndPassword(emailText, password)

                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            final FirebaseUser user = mAuth.getCurrentUser();
                            if(user!=null)
                                Toast.makeText(MainActivity.this,"successfull login", Toast.LENGTH_SHORT).show();

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

                                        Intent in = new Intent(MainActivity.this , Main3Activity.class);
                                        in.putExtra("imageurl", imageurl);
                                        in.putExtra("uid", user.getUid());
                                        in.putExtra("email", emailText);
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
                            Toast.makeText(MainActivity.this, "User is already registered",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.skip)
        {
            Intent i = new Intent(MainActivity.this ,Main3Activity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
