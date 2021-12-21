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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Support_Insurance extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner queryspinner;
    List<String> queries;
    RadioGroup radioGroup;
    RadioButton radioButton;
    RadioButton radioButton1;
    RadioButton radioButton2;
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
        submit = findViewById(R.id.Insurancequerysubmitbutton);
        radioButton1 = findViewById(R.id.InsuranceradioButton);
        radioButton2 = findViewById(R.id.InsuranceradioButton2);
        radioButton3 = findViewById(R.id.InsuranceradioButton3);

        int radioid = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioid);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDb = mDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userKey = user.getUid();


        queries = new ArrayList<>();
        queries.add("Request a document");
        queries.add("Make a policy change");
        queries.add("Make a payment");
        queries.add("Other");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Support_Insurance.this,android.R.layout.simple_spinner_item,queries);
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
        submit.setOnClickListener(new View.OnClickListener() {
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
            radioButton2.setText("Policy overview documents");
            radioButton3.setText("Other documents (please specify)");

        }
        if ( item.equals("Make a policy change") ){
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
        if ( item.equals("Make a payment") ){
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
        if ( item.equals("Other") ){
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