package com.example.medi_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.medi_app.util.CustomToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Request_expert extends AppCompatActivity {  // request an expert class
    TextView inname;
    TextView innumber;
    TextView inemail;
    TextView gpname;
    TextView gpnumber;                          // set up viewa
    TextView gpemailtext;
    ImageView callgp;
    ImageView callinsurance;
    ImageView emailinsurance;
    ImageView emailgpbutton;
    String numselected;
    Button cancel;
    private static final int REQUEST_CALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_expert);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDb = mDatabase.getReference();                   //firebase
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userKey = user.getUid();

        inname = findViewById(R.id.call_insurance_name);
         innumber = findViewById(R.id.call_insurance_number);
         inemail = findViewById(R.id.expert_in_emailTV);
         emailinsurance = findViewById(R.id.insurance_email);
         emailgpbutton = findViewById(R.id.email_gp_img);
         gpname = findViewById(R.id.call_gpname);                   // initialise views
         gpnumber = findViewById(R.id.call_gps_number);
         gpemailtext = findViewById(R.id.expert_gp_emailTV);
         callgp = findViewById(R.id.gp_call);
         callinsurance = findViewById(R.id.insurance_call);
         cancel = findViewById(R.id.request_expertbutton);

        mDb.child("Patients").child(userKey).addValueEventListener(new ValueEventListener() { // get gp and insurance data from firebase
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String provider = String.valueOf(snapshot.child("Insurance Company Details").child("insurance_company_On_File").getValue());
                String providerno = String.valueOf(snapshot.child("Insurance Company Details").child("contact_number").getValue());
                String providereml = String.valueOf(snapshot.child("Insurance Company Details").child("email").getValue());
                String gp = String.valueOf(snapshot.child("Associated GP").child("_Name").getValue());
                String gpno = String.valueOf(snapshot.child("Associated GP").child("contact_num").getValue());
                String gpeml = String.valueOf(snapshot.child("Associated GP").child("_Email").getValue());






                if (! (provider.equals("null") && providerno.equals("null") && gp.equals("null") && providereml.equals("null")&& gpeml.equals("null"))){ // if all info there set all textviews

                    inname.setText(provider);
                    innumber.setText(providerno);
                    inemail.setText(providereml);
                    gpname.setText(gp);
                    gpnumber.setText(gpno);
                    gpemailtext.setText(gpeml);

                }

                 if (gp.equals("null") && gpno.equals("null")){
                    gpname.setText("No details entered.. Tap here to enter details");           // if no gp entered
                    gpname.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent =  new Intent(Request_expert.this, GP_Details.class);
                            Request_expert.this.startActivity(intent);
                        }
                    });
                    gpnumber.setText("No details entered..");
                    gpemailtext.setText("No details entered..");

                    inname.setText(provider);
                    innumber.setText(providerno);
                    inemail.setText(providereml);

                }

                 if (provider.equals("null") && providerno.equals("null")){                 // if no insurance enteed
                    gpname.setText(gp);
                    gpnumber.setText(gpno);

                    inname.setText("No details entered.. Tap to enter details");
                     inname.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View view) {
                             Intent intent =  new Intent(Request_expert.this, insurance_details.class);
                             Request_expert.this.startActivity(intent);
                         }
                     });
                    innumber.setText("No details entered..");
                     inemail.setText("No details entered..");

                }
                 if (provider.equals("null") && providerno.equals("null")&& providereml.equals("null") && gp.equals("null") && gpno.equals("null")&& gpeml.equals("null")){ // if no gp or no insurance details entered
                    new AlertDialog.Builder(Request_expert.this)
                            .setTitle("No  details found")
                            .setMessage("You will need to provide us with your details before proceeding")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("Enter details", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent i = new Intent(Request_expert.this, My_Medical_Information.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
                                    startActivity(i);
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent x = new Intent(Request_expert.this, HomepageActivity.class);
                                    x.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(x);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                CustomToast.createToast(Request_expert.this,"Error",true);
            }


        });

        callinsurance.setOnClickListener(new View.OnClickListener() { // button to call insurance company
            @Override
            public void onClick(View view) {
                numselected = "353"+innumber.getText().toString();
                makePhoneCall();
            }
        });

        emailinsurance.setOnClickListener(new View.OnClickListener() {          // button to email insurance company
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { inemail.getText().toString() });




                startActivity(Intent.createChooser(intent, "Choose an email client"));
            }
        });

        callgp.setOnClickListener(new View.OnClickListener() {          // button to call gp
            @Override
            public void onClick(View view) {
                numselected = gpnumber.getText().toString();
                makePhoneCall();
            }
        });

        emailgpbutton.setOnClickListener(new View.OnClickListener() {          // button to email gp
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { gpemailtext.getText().toString() });




                startActivity(Intent.createChooser(intent, "Choose an email client"));
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Request_expert.this,HomepageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });



    }




    private void makePhoneCall() {  // make a phone call method

        if (!numselected.trim().equals("No details entered..")) {

            if (ContextCompat.checkSelfPermission(Request_expert.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Request_expert.this,              // ask for permission to use phone
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + numselected;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));  // go to phone app
            }

        } else {
            CustomToast.createToast(this,"You need to enter these details!", true);      // error checking
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {      // user selects allow - make the call
                makePhoneCall();
            } else {
                CustomToast.createToast(this,"Permission DENIED", true);
            }
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Request_expert.this,HomepageActivity.class);
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
                Intent intent = new Intent(Request_expert.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }



}