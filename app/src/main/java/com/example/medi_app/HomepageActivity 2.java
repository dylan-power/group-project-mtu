package com.example.medi_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class HomepageActivity  extends AppCompatActivity implements RecyclerviewAdapter.ItemClickListener {

    TextView userTextView;
    RecyclerviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        userTextView=findViewById(R.id.loggedInTextView);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDb = mDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userKey = user.getUid();
        mDb.child("Patients").child(userKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username = String.valueOf(snapshot.child("firstName").getValue());
                userTextView.setText("Welcome "+ username);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        // data to populate the RecyclerView with
        ArrayList<String> homepage_icons_text= new ArrayList<>();
        homepage_icons_text.add("Set up Appointment");
        homepage_icons_text.add("Medi Coin");
        homepage_icons_text.add("Medi Predict");
        homepage_icons_text.add("My Medical Information");
        homepage_icons_text.add("Request an expert");
        homepage_icons_text.add("Support");
        homepage_icons_text.add("Leave us a review");

        //arraylist to store images
        ArrayList<Integer> homepage_icons_images=new ArrayList<Integer>();

        homepage_icons_images.add(R.drawable.setupappointment);
        homepage_icons_images.add(R.drawable.medicoin);
        homepage_icons_images.add(R.drawable.medipredict);
        homepage_icons_images.add(R.drawable.medicalinfo);
        homepage_icons_images.add(R.drawable.requestexpert);
        homepage_icons_images.add(R.drawable.ic_baseline_contact_support_24);
        homepage_icons_images.add(R.drawable.leaveusareview);

        // set up the RecyclerView
        //1- initialise the recyclerview
        RecyclerView recyclerView = findViewById(R.id.recyclerview_homepage);

        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(manager);

        adapter = new RecyclerviewAdapter(this, homepage_icons_text,homepage_icons_images);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);



    }
      @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
          Intent intent = new Intent(HomepageActivity.this,MainActivity.class);
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
                Intent intent = new Intent(HomepageActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onItemClick(View view, int position) {
        CustomToast.createToast(this, "Showing " + adapter.getItem(position),false); //custom toast is created
        Context context=view.getContext();
        switch (position){
            case 0:
                Intent intent =  new Intent(context, SetupAppointment.class);
                context.startActivity(intent);
                break;
            case 1:
                 intent =  new Intent(context, MediCoin.class);
                context.startActivity(intent);
                break;
            case 2:
                 intent =  new Intent(context, MediPredict.class);
                context.startActivity(intent);
                break;
            case 3:
                 intent =  new Intent(context, My_Medical_Information.class);
                context.startActivity(intent);
                break;

            case 4:
                intent =  new Intent(context, Request_expert.class);
                context.startActivity(intent);
                break;

            case 5:
                intent =  new Intent(context, Support.class);
                context.startActivity(intent);
                break;

            case 6:
                intent =  new Intent(context, Review_activity.class);
                context.startActivity(intent);
                break;
        }
    }
}