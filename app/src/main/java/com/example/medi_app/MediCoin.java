package com.example.medi_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

import java.util.Calendar;
import java.util.Date;

public class MediCoin extends AppCompatActivity { // medi-coin class allow user to pay for insurance and or appointments









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medi_coin);


       showdialog(); // welcome dialog shows


        TextView welcome = findViewById(R.id.coin_welcome_tv);
        TextView insurancemonth=findViewById(R.id.month_tv);
        TextView insuranceprice=findViewById(R.id.insurancecost_tv);
        TextView gpappointment=findViewById(R.id.appointment_time_tv);          // set up views
        TextView gpprice =findViewById(R.id.gppricetv);
        Button insurancepay=findViewById(R.id.payInsurancebutton);
        Button gppay=findViewById(R.id.payGPbutton);
        Button cancel= findViewById(R.id.cancelbuttonCoin);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDb = mDatabase.getReference();               //firebase
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userKey = user.getUid();


        String monthname=(String)android.text.format.DateFormat.format("MMMM - yyyy", new Date());      // show month for current insurance premium
        Calendar calendar = Calendar.getInstance();
        insurancemonth.setText("Premium to be payed for "+ monthname );

        mDb.child("Patients").child(userKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String username = String.valueOf(dataSnapshot.child("firstName").getValue());
                Resources res = getResources();
                String text = String.format(res.getString(R.string.coin_welcome_textview), username); // display name in the welcome textview
                welcome.setText(text);

                String appointdate = String.valueOf(dataSnapshot.child("Upcoming Appointment").child("appointment_date").getValue());   // get values from firebase
                String appointtime = String.valueOf(dataSnapshot.child("Upcoming Appointment").child("appointment_time").getValue());
                String appointprice = String.valueOf(dataSnapshot.child("Upcoming Appointment").child("price").getValue());
                String insurancecost = String.valueOf(dataSnapshot.child("Insurance Company Details").child("monthly_Payments").getValue());

                if (! (appointdate.equals("null") && appointtime.equals("null") && appointprice.equals("null") && insurancecost.equals("null"))){
                    gpappointment.setText("Appointment on: "+appointdate+" at "+appointtime);
                    gpprice.setText(appointprice);
                    insuranceprice.setText("€"+insurancecost);
                }

                if (appointdate.equals("null") && appointtime.equals("null") && appointprice.equals("null")&& insurancecost.equals("null")){
                    gpappointment.setText("No details entered");
                    gpprice.setText("");
                    insuranceprice.setText("No details entered");
                }                                                                                                                                                       //error checking
                                                                                                                                                                // different fields display if some data is not entered to firebase
                if (insurancecost.equals("null")){

                    insuranceprice.setText("No details entered");
                    insurancepay.setVisibility(View.INVISIBLE);
                }

                if (appointdate.equals("null")){

                    gpappointment.setText("No upcoming appointments");
                    gpprice.setText("");
                    gppay.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                CustomToast.createToast(MediCoin.this,"Database error",true);
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediCoin.this, HomepageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MediCoin.this,HomepageActivity.class);
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
                Intent intent = new Intent(MediCoin.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }
    public void showdialog(){           // welcome dialog
        new AlertDialog.Builder(MediCoin.this)
                .setTitle("Welcome to Medi-Coin")
                .setMessage("With Medi-Coin you can pay for an upcoming appointment with your GP or Pay for your monthly insurance premium.\nClick 'Ok' to get started or 'Cancel' To go back to the homepage")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MediCoin.this, HomepageActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
                        startActivity(intent);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}