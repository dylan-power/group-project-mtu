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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.medi_app.model.SupportInsurancedetail;
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

public class Support_Insurance extends AppCompatActivity implements AdapterView.OnItemSelectedListener { // class for support form for insurance company
    Spinner queryspinner;
    List<String> queries;
    RadioGroup radioGroup;
    RadioButton radioButton;
    RadioButton radioButton1;
    RadioButton radioButton2;                   // set up views
    RadioButton radioButton3;
    EditText editText;
    Button cancel;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_insurance);
        queryspinner = findViewById(R.id.supportInsurancespinner);
        radioGroup = findViewById(R.id.InsuranceradioGroup);
        editText = findViewById(R.id.Insurance_querytext);
        cancel = findViewById(R.id.Insurancequerycancelbutton);
        submit = findViewById(R.id.Insurancequerysubmitbutton);         // initialise views
        radioButton1 = findViewById(R.id.InsuranceradioButton);
        radioButton2 = findViewById(R.id.InsuranceradioButton2);
        radioButton3 = findViewById(R.id.InsuranceradioButton3);

        int radioid = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioid);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDb = mDatabase.getReference();                   // firebase
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userKey = user.getUid();

        mDb.child("Patients").child(userKey).child("Insurance Company Details").addValueEventListener(new ValueEventListener() {        //find insurance details from firebase
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String provider = String.valueOf(snapshot.child("insurance_company_On_File").getValue());
                String polno = String.valueOf(snapshot.child("policy_Number").getValue());
                String start = String.valueOf(snapshot.child("policy_Start").getValue());
                String end = String.valueOf(snapshot.child("policy_End").getValue());
                String num = String.valueOf(snapshot.child("contact_number").getValue());

                if (provider.equals("null") && polno.equals("null") && start.equals("null") && end.equals("null")){             // if nothing entered ask user to enter details



                    new AlertDialog.Builder(Support_Insurance.this)
                            .setTitle("No Insurance details found")
                            .setMessage("You will need to provide us with your Insurance details before proceeding")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("Enter Insurance details", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent i = new Intent(Support_Insurance.this, insurance_details.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    CustomToast.createToast(Insurance_details_view.this,"Insurance details Submitted", false);
                                    startActivity(i);
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent x = new Intent(Support_Insurance.this, HomepageActivity.class);
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
                CustomToast.createToast(Support_Insurance.this,"Error",true);
            }


        });
//        if user has insurance details submitted continue

        queries = new ArrayList<>();
        queries.add("Request a document");           // add queries to arraylist for dropdown menu
        queries.add("Make a policy change");
        queries.add("Make a payment");
        queries.add("Other");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Support_Insurance.this,android.R.layout.simple_spinner_item,queries); // set queries to dropdown
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        queryspinner.setAdapter(arrayAdapter);
        queryspinner.setOnItemSelectedListener(this);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Support_Insurance.this, Support.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(i);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() { // send query to database
            @Override
            public void onClick(View view) {
                SupportInsurancedetail supportInsurancedetail = new SupportInsurancedetail(queryspinner.getSelectedItem().toString(),radioButton.getText().toString(),editText.getText().toString());
                mDatabase.getReference().child("Patients").child(userKey).child("Insurance Company Details").child("Support Query").setValue(supportInsurancedetail);
                Intent i = new Intent(Support_Insurance.this, HomepageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                CustomToast.createToast(Support_Insurance.this,"Support request Submitted", false);
                startActivity(i);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = queryspinner.getSelectedItem().toString();
        if ( item.equals("Request a document") ){
            radioGroup.setVisibility(View.VISIBLE);
            radioButton1.setText("Payment documents");
            radioButton2.setText("Policy overview documents");              // set radio button values
            radioButton3.setText("Other documents (please specify)");


        }
        if ( item.equals("Make a policy change") ){ // user is prompted to amend insurance details in insurance details class
            radioGroup.setVisibility(View.INVISIBLE);
            radioButton1.setText("Other");
            radioButton2.setText("Other");
            radioButton3.setText("Other");
            new AlertDialog.Builder(Support_Insurance.this)
                    .setTitle("Make a policy change")
                    .setMessage("You can amend your policy within Medi-App!\nClick 'Update policy' , or press 'Cancel' to submit a different query")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("Update policy", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent i = new Intent(Support_Insurance.this, insurance_details.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
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
        if ( item.equals("Make a payment") ){// user is prompted to pay in medi coin class
            radioGroup.setVisibility(View.INVISIBLE);
            radioButton1.setText("Other");
            radioButton2.setText("Other");
            radioButton3.setText("Other");
            new AlertDialog.Builder(Support_Insurance.this)
                    .setTitle("Make a payment")
                    .setMessage("You can use the Medi-Coin service to pay for your premium\nClick 'Use Medi-Coin' , or press 'Cancel' to submit a different query")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("Use Medi-Coin", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent i = new Intent(Support_Insurance.this, MediCoin.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
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
        if ( item.equals("Other") ){       // other queries
            radioGroup.setVisibility(View.INVISIBLE);
            radioButton1.setText("Other");
            radioButton2.setText("Other");
            radioButton3.setText("Other");

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void checkButton(View v) {

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Support_Insurance.this,Support.class);
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
                Intent intent = new Intent(Support_Insurance.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }
}