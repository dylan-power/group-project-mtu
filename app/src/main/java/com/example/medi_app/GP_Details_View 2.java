package com.example.medi_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class GP_Details_View extends AppCompatActivity { // activity for viewing gp details on file

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gp_details_view);


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(); // firebase
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDb = mDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userKey = user.getUid();

        TextView nameFB = findViewById(R.id.gpname_FB); // initialise views
        TextView addrFB = findViewById(R.id.gpaddr_FB);
        TextView numFB = findViewById(R.id.gpnumb_FB);
        TextView emailFB = findViewById(R.id.gpemail_FB);
        TextView uuid = findViewById(R.id.gp_uuid_FB);
        Button amend = findViewById(R.id.amend_gpv);
        Button ok = findViewById(R.id.ok_gpv);


        mDb.child("Patients").child(userKey).child("Associated GP").addValueEventListener(new ValueEventListener() { // see if user has entered their gp details
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = String.valueOf(snapshot.child("_Name").getValue());
                String adr = String.valueOf(snapshot.child("office_address").getValue());
                String numbr = String.valueOf(snapshot.child("contact_num").getValue());
                String emal = String.valueOf(snapshot.child("_Email").getValue());
                String uid = String.valueOf(snapshot.child("uuid").getValue());

                if (! (name.equals("null") && adr.equals("null") && numbr.equals("null") && emal.equals("null")&& uid.equals("null"))){ // if they have entered gp details
//                    set the textviews to display gp info
                    nameFB.setText(name);
                    addrFB.setText(adr);
                    numFB.setText(numbr);
                    emailFB.setText(emal);
                    uuid.setText(uid);
                }
                else {
                    new AlertDialog.Builder(GP_Details_View.this) // no details - set an alert dialog prompting to enter details
                            .setTitle("No GP details found")
                            .setMessage("You will need to provide us with your GP's details before proceeding")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("Enter GP details", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent i = new Intent(GP_Details_View.this, GP_Details.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    CustomToast.createToast(Insurance_details_view.this,"Insurance details Submitted", false);
                                    startActivity(i);
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent x = new Intent(GP_Details_View.this, HomepageActivity.class);
                                    x.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    CustomToast.createToast(Insurance_details_view.this,"Insurance details Submitted", false);
                                    startActivity(x);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                CustomToast.createToast(GP_Details_View.this,"Error",true);
            }


        });

        amend.setOnClickListener(new View.OnClickListener() { // allow user to change their GP, takes them to the enter GP details class
            @Override
            public void onClick(View view) {
                Intent x = new Intent(GP_Details_View.this, GP_Details.class);
                x.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                CustomToast.createToast(GP_Details_View.this,"Enter your new GP details", false);
                startActivity(x);
            }
        });

        ok.setOnClickListener(new View.OnClickListener() { // ok button to leave the page back to the homepage
            @Override
            public void onClick(View view) {
                Intent x = new Intent(GP_Details_View.this, HomepageActivity.class);
                x.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    CustomToast.createToast(Insurance_details_view.this,"Insurance details Submitted", false);
                startActivity(x);
            }
        });
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(GP_Details_View.this,HomepageActivity.class);
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
                Intent intent = new Intent(GP_Details_View.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }
}