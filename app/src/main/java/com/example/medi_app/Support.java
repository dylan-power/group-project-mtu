package com.example.medi_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.medi_app.util.CustomToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Support extends AppCompatActivity {  // class for support forms
    TextView support_welcome_tv;
    Button cancel;                                  // set up views
    Button in;
    Button gp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        support_welcome_tv = findViewById(R.id.support_welcome_textv);
        cancel = findViewById(R.id.cancelbuttonSupport);                    // initialise views
        gp = findViewById(R.id.SupportGPbutton);
        in = findViewById(R.id.SupportInsurancebutton);


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();            // firebase
        DatabaseReference mDb = mDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userKey = user.getUid();

        mDb.child("Patients").child(userKey).addValueEventListener(new ValueEventListener() {
            @Override                                                                               // set the users name to the welcome textfield
            public void onDataChange(DataSnapshot dataSnapshot) {
                String username = String.valueOf(dataSnapshot.child("firstName").getValue());
                Resources res = getResources();
                String text = String.format(res.getString(R.string.support_welcome_textview), username);
                support_welcome_tv.setText(text);
    }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                CustomToast.createToast(Support.this,"Database error",true);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {      //back to homepage
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Support.this, HomepageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        gp.setOnClickListener(new View.OnClickListener() {                      // takes user to form for GP support
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Support.this, Support_GP.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        in.setOnClickListener(new View.OnClickListener() { // takes user to form for insurance support
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Support.this, Support_Insurance.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Support.this,HomepageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Support.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }
}
