package com.example.medi_app;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.medi_app.model.reviewInfo;
import com.example.medi_app.util.CustomToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Objects;

import android.os.Bundle;

public class Review_activity extends AppCompatActivity {

    RatingBar coinratingbar;
    RatingBar predictratingbar;
    EditText otherfbck;
    Button submit;
    Button cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        TextView review_welcome_tv = findViewById(R.id.review_welcome_textview);
        RatingBar coinratingbar = findViewById(R.id.medicoin_ratingbar);
        RatingBar predictatingbar = findViewById(R.id.medipredict_ratingbar);
        otherfbck = findViewById(R.id.input_review);
        Button submit = findViewById(R.id.submit_review);
        Button cancel = findViewById(R.id.review_cancelbutton);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDb = mDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userKey = user.getUid();

        mDb.child("Patients").child(userKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String username = String.valueOf(dataSnapshot.child("firstName").getValue());
                String email = String.valueOf(dataSnapshot.child("email").getValue());

                Resources res = getResources();
                String text = String.format(res.getString(R.string.review_welcome_textview), username);
                review_welcome_tv.setText(text);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reviewInfo reviewinfo = new reviewInfo(username,email,String.valueOf(coinratingbar.getRating()),String.valueOf(predictatingbar.getRating()),otherfbck.getText().toString());
                        mDatabase.getReference().child("Patients").child("User Reviews").setValue(reviewinfo);
                        Intent i = new Intent(Review_activity.this, HomepageActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        CustomToast.createToast(Review_activity.this,"Feedback Submitted", false);
                        startActivity(i);
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(Review_activity.this, HomepageActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                CustomToast.createToast(Review_activity.this,"Database error",true);

            }
        });



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Review_activity.this,HomepageActivity.class);
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
                Intent intent = new Intent(Review_activity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }

}