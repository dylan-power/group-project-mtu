package com.example.medi_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.medi_app.model.UserDetail;
import com.example.medi_app.util.CustomToast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {     // sign up to the app page
    EditText emailId, firstName, lastName, address, password;
    Button btnSignUp;
    TextView tvSignIn;                                              // set up views

    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase firebaseDatabase;                              // firebase
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        emailId = findViewById(R.id.editTextEmail);
        firstName = findViewById(R.id.editTextFirstName);
        lastName = findViewById(R.id.editTextLastName);
        address = findViewById(R.id.editTextAddress);
        password = findViewById(R.id.editTextPassword);         // initialise views
        btnSignUp = findViewById(R.id.signUpButton);
        tvSignIn = findViewById(R.id.textViewSignIn);


        mFirebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();                  // firebase
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailId.getText().toString();
                final String fName = firstName.getText().toString();
                final String lName = lastName.getText().toString();
                final String add = address.getText().toString();
                final String pwd = password.getText().toString();

                if (email.isEmpty()) {
                    emailId.setError("Please provide email id");
                    emailId.requestFocus();
                } else if (fName.isEmpty()) {
                    firstName.setError("Please provide your first name");
                    firstName.requestFocus();
                } else if (lName.isEmpty()) {
                    lastName.setError("Please provide your last name");
                    lastName.requestFocus();                                            // error checking
                } else if (add.isEmpty()) {
                    address.setError("Please provide your address");
                    address.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please provide password");
                    password.requestFocus();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {                           // if details entered - create account with firebase
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pwd)
                            .addOnCompleteListener(Signup.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {        //failure
                                    if(!task.isSuccessful()) {
                                        CustomToast.createToast(Signup.this,"SignUp Unsuccessful, Please Try Again!"
                                        + task.getException().getMessage(),true);
                                    }
                                    else {
                                        UserDetail userDetail = new UserDetail(fName,lName,add,email);          // details correct - send to firebase
                                        String uid = task.getResult().getUser().getUid();
                                        firebaseDatabase.getReference().child("Patients").child(uid).setValue(userDetail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent intent = new Intent(Signup.this,HomepageActivity.class );
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                intent.putExtra("name", fName+ "" + lName);
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                }
                            });

                } else {
                    CustomToast.createToast(Signup.this, "Error Occurred !", true);
                }



            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Signup.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }
}