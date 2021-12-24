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

import com.example.medi_app.model.Dialog;
import com.example.medi_app.util.CustomToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Insurance_details_view extends AppCompatActivity { // class for viewing insurance details

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_details_view);


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();        //firebase
        DatabaseReference mDb = mDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userKey = user.getUid();


        TextView providerFB = findViewById(R.id.provider_input_tv);
        TextView policynoFB = findViewById(R.id.policyno_input_tv);
        TextView startdateFB = findViewById(R.id.input_startdate_tv);
        TextView enddateFB = findViewById(R.id.input_enddate_tv);           // set up views
        TextView contactnumFB = findViewById(R.id.contact_num);
        Button amend = findViewById(R.id.Amend_details_button);
        Button ok = findViewById(R.id.details_ok_btn);

        mDb.child("Patients").child(userKey).child("Insurance Company Details").addValueEventListener(new ValueEventListener() {  // get details from firebase
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String provider = String.valueOf(snapshot.child("insurance_company_On_File").getValue());
                String polno = String.valueOf(snapshot.child("policy_Number").getValue());
                String start = String.valueOf(snapshot.child("policy_Start").getValue());
                String end = String.valueOf(snapshot.child("policy_End").getValue());
                String num = String.valueOf(snapshot.child("contact_number").getValue());

                if (! (provider.equals("null") && polno.equals("null") && start.equals("null") && end.equals("null"))){ // if user has entered insurance details

                providerFB.setText(provider);                                                       // display the details
                policynoFB.setText(polno);
                startdateFB.setText(start);
                enddateFB.setText(end);
                contactnumFB.setText(num);
            }
                else {
                    new AlertDialog.Builder(Insurance_details_view.this)                    // otherwise show a dialog asking to enter details
                            .setTitle("No Insurance details found")
                            .setMessage("You will need to provide us with your Insurance details before proceeding")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("Enter Insurance details", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent i = new Intent(Insurance_details_view.this, insurance_details.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    CustomToast.createToast(Insurance_details_view.this,"Insurance details Submitted", false);
                                    startActivity(i);
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent x = new Intent(Insurance_details_view.this, HomepageActivity.class);
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
                CustomToast.createToast(Insurance_details_view.this,"Error",true);
            }


        });

        amend.setOnClickListener(new View.OnClickListener() {                   // allow user to change their details
            @Override
            public void onClick(View view) {
                Intent x = new Intent(Insurance_details_view.this, insurance_details.class);
                x.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                CustomToast.createToast(Insurance_details_view.this,"Enter your new insurance details", false);
                startActivity(x);
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {                      // go back to homepage
            @Override
            public void onClick(View view) {
                Intent x = new Intent(Insurance_details_view.this, HomepageActivity.class);
                x.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(x);
            }
        });
    }





    public void openDialog() {
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(),"Insurance Company");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Insurance_details_view.this,HomepageActivity.class);
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
                Intent intent = new Intent(Insurance_details_view.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }
}