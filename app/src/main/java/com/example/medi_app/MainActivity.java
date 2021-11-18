package com.example.medi_app;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medi_app.model.UserDetail;
import com.example.medi_app.util.CustomToast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText emailId, password;
    Button btnSignIn;
    TextView tvSignUp;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailId = findViewById(R.id.editTextEmailSignIn);
        password = findViewById(R.id.editTextPasswordSignIn);
        btnSignIn = findViewById(R.id.signInButton);
        tvSignUp = findViewById(R.id.textViewSignUp);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseuser = firebaseAuth.getCurrentUser();
                if (mFirebaseuser !=null) {
                    moveToHomeActivity(mFirebaseuser);
                }
                else
                    {
                        CustomToast.createToast(MainActivity.this, "Please login", false);
                    }
                }
            };

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= emailId.getText().toString();
                String pwd = password.getText().toString();

                if(email.isEmpty()) {
                    emailId.setError("Please provide your email");
                    emailId.requestFocus();
                }
                else if (pwd.isEmpty()) {
                    password.setError("Please provide your Password");
                    password.requestFocus();
                }
                else if (email.isEmpty() && pwd.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Fields cannot be left empty!", Toast.LENGTH_LONG).show();
                }
                else if(!(email.isEmpty()&&pwd.isEmpty()))
                {
                    firebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this, "Login Error, Please log in",Toast.LENGTH_LONG).show();
                            } else
                            {
                              moveToHomeActivity(task.getResult().getUser());
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this,"Error occured!", Toast.LENGTH_LONG).show();
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intSignUp = new Intent(MainActivity.this,Signup.class);
                intSignUp.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intSignUp);
            }
        });

        }

        @Override
        protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthStateListener);
        }

    private void moveToHomeActivity(FirebaseUser mFirebaseuser) {
        firebaseDatabase.getReference().child("Patients").child(mFirebaseuser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDetail userDetail=snapshot.getValue(UserDetail.class);
                String name = userDetail.getFirstName() + " " + userDetail.getLastName();
                Intent i = new Intent(getApplicationContext(),HomepageActivity.class);
                CustomToast.createToast(getApplicationContext(),"Login Successful",false);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.putExtra("name", name);
                startActivity(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




}