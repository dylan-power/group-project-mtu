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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.medi_app.model.gp_detail;
import com.example.medi_app.util.CustomToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GP_Details extends AppCompatActivity { // entering GP details class
    Spinner spinner;
    DatabaseReference databaseReference;
    List<String> gps;
    TextView address;
    TextView email;
    TextView number;
    TextView gpID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gp_details);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(); // firebase
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDb = mDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userKey = user.getUid();

        spinner = findViewById(R.id.insurance_spinner); //find views
        gps = new ArrayList<>(); // arraylist to store gps names
        address = findViewById(R.id.gpaddr_tv);
        email = findViewById(R.id.gpemail_tv);
        number = findViewById(R.id.gpnumb_tv);
        gpID = findViewById(R.id.gp_uuid_tv);
        Button submitbtn = findViewById(R.id.gpdets_submit);
        Button cancelbtn = findViewById(R.id.gpdets_cancel);

        ArrayList<String> address_al = new ArrayList<>(); //arraylists for other gp details
        ArrayList<String> email_al = new ArrayList<>();
        ArrayList<String> phonenumber = new ArrayList<>();
        ArrayList<String> uuids = new ArrayList<>();

        mDb.child("Patients").child(userKey).child("Associated GP").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = String.valueOf(snapshot.child("_Name").getValue()); // get values from firebase
                String adr = String.valueOf(snapshot.child("office_address").getValue());
                String numbr = String.valueOf(snapshot.child("contact_num").getValue());
                String emal = String.valueOf(snapshot.child("_Email").getValue());
                String uid = String.valueOf(snapshot.child("uuid").getValue());

                if ( !(name.equals("null") && adr.equals("null") && numbr.equals("null") && emal.equals("null")&& uid.equals("null"))){ // if data is already entered to the DB set an error message


                    new AlertDialog.Builder(GP_Details.this)
                            .setTitle("GP details found")
                            .setMessage("You have already entered your GP details!\nYou can click 'View' to see them, or press 'Cancel' to submit your details again")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("View", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent i = new Intent(GP_Details.this, GP_Details_View.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    CustomToast.createToast(Insurance_details_view.this,"Insurance details Submitted", false);
                                    startActivity(i);
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                CustomToast.createToast(GP_Details.this,"Error",true);
            }


        });

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Medical Professionals").addValueEventListener(new ValueEventListener() { // Find all Doctors registered and add them to the arraylists

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childsnapshot : snapshot.getChildren()) {

                    String spinnerGPfirstname = childsnapshot.child("firstName").getValue(String.class);
                    String spinnerGPlastname = childsnapshot.child("surname").getValue(String.class);
                    uuids.add(childsnapshot.getKey());
                    address_al.add(childsnapshot.child("address").getValue(String.class));
                    email_al.add(childsnapshot.child("email").getValue(String.class));
                    phonenumber.add(childsnapshot.child("phone").getValue(String.class));
                    gps.add(spinnerGPfirstname + " " + spinnerGPlastname);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(GP_Details.this, android.R.layout.simple_spinner_item, gps); // set the gps name arraylist to the spinner dropdown so the user can select their GP
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spinner.setAdapter(arrayAdapter);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) { // listener for when a user selects a name
                String add = address_al.get(position).toString();
                String eml = email_al.get(position).toString();
                String numb = phonenumber.get(position).toString();
                String id = uuids.get(position).toString();
                address.setText(add);
                email.setText(eml);
                number.setText(numb);
                gpID.setText(id);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    submitbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            gp_detail gp = new gp_detail(gpID.getText().toString(),spinner.getSelectedItem().toString(),address.getText().toString(),number.getText().toString(),email.getText().toString()); // when user clicks submit get all values and send to firebase
            mDatabase.getReference().child("Patients").child(userKey).child("Associated GP").setValue(gp);
            Intent i = new Intent(GP_Details.this, HomepageActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            CustomToast.createToast(GP_Details.this,"Insurance details Submitted", false);
            startActivity(i);

        }
    });

    cancelbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(GP_Details.this, HomepageActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            CustomToast.createToast(GP_Details.this,"Insurance details Submitted", false);
            startActivity(i);
        }
    });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(GP_Details.this,My_Medical_Information.class);
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
                Intent intent = new Intent(GP_Details.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }
}