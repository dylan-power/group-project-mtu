package com.example.medi_app;

import static android.content.ContentValues.TAG;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medi_app.util.CustomToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.BreakIterator;

public class Request_expert extends AppCompatActivity {
    TextView inname;
    TextView innumber;
    TextView gpname;
    TextView gpnumber;
    ImageView callgp;
    ImageView callinsurance;
    String numselected;
    Button cancel;
    private static final int REQUEST_CALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_expert);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDb = mDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userKey = user.getUid();

        inname = findViewById(R.id.call_insurance_name);
         innumber = findViewById(R.id.call_insurance_number);
         gpname = findViewById(R.id.call_gpname);
         gpnumber = findViewById(R.id.call_gps_number);
         callgp = findViewById(R.id.gp_call);
         callinsurance = findViewById(R.id.insurance_call);
         cancel = findViewById(R.id.request_expertbutton);

        mDb.child("Patients").child(userKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String provider = String.valueOf(snapshot.child("Insurance Company Details").child("insurance_company_On_File").getValue());
                String providerno = String.valueOf(snapshot.child("Insurance Company Details").child("contact_number").getValue());
                String gp = String.valueOf(snapshot.child("Associated GP").child("_Name").getValue());
                String gpno = String.valueOf(snapshot.child("Associated GP").child("contact_num").getValue());





                if (! (provider.equals("null") && providerno.equals("null") && gp.equals("null") && gpno.equals("null"))){

                    inname.setText(provider);
                    innumber.setText(providerno);
                    gpname.setText(gp);
                    gpnumber.setText(gpno);

                }

                 if (gp.equals("null") && gpno.equals("null")){
                    gpname.setText("No details entered.. Tap here to enter details");
                    gpname.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent =  new Intent(Request_expert.this, GP_Details.class);
                            Request_expert.this.startActivity(intent);
                        }
                    });
                    gpnumber.setText("No details entered..");

                    inname.setText(provider);
                    innumber.setText(providerno);

                }

                 if (provider.equals("null") && providerno.equals("null")){
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

                }
                 if (provider.equals("null") && providerno.equals("null") && gp.equals("null") && gpno.equals("null")){
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
                CustomToast.createToast(Request_expert.this,"Error",true);
            }


        });

        callinsurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numselected = "353"+innumber.getText().toString();
                makePhoneCall();
            }
        });
        callgp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numselected = gpnumber.getText().toString();
                makePhoneCall();
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




    private void makePhoneCall() {

        if (!numselected.trim().equals("No details entered..")) {

            if (ContextCompat.checkSelfPermission(Request_expert.this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Request_expert.this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + numselected;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            CustomToast.createToast(this,"You need to enter these details!", true);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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