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

import com.example.medi_app.model.SupportGPdetail;
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

public class Support_GP extends AppCompatActivity implements AdapterView.OnItemSelectedListener { // class for support form for GP's
    Spinner queryspinner;
    List<String> queries;
    RadioGroup radioGroup;
    RadioButton radioButton;
    RadioButton radioButton1;
    RadioButton radioButton2;                       // set up viewa
    RadioButton radioButton3;
    EditText editText;
    Button cancel;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_gp);
        queryspinner = findViewById(R.id.supportgpspinner);
        radioGroup = findViewById(R.id.GPradioGroup);
        editText = findViewById(R.id.gp_querytext);
        cancel = findViewById(R.id.gpquerycancelbutton);                        // initialise views
        submit = findViewById(R.id.gpquerysubmitbutton);
        radioButton1 = findViewById(R.id.GPradioButton);
        radioButton2 = findViewById(R.id.GPradioButton2);
        radioButton3 = findViewById(R.id.GPradioButton3);
        int radioid = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioid);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();            // firebase
        DatabaseReference mDb = mDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userKey = user.getUid();


        mDb.child("Patients").child(userKey).child("Associated GP").addValueEventListener(new ValueEventListener() {        // get patients associated gp from DB
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = String.valueOf(snapshot.child("_Name").getValue());
                String adr = String.valueOf(snapshot.child("office_address").getValue());
                String numbr = String.valueOf(snapshot.child("contact_num").getValue());
                String emal = String.valueOf(snapshot.child("_Email").getValue());
                String uid = String.valueOf(snapshot.child("uuid").getValue());

                if ( name.equals("null") && adr.equals("null") && numbr.equals("null") && emal.equals("null")&& uid.equals("null")){        // if no details entered show a dialog asking to enter details





                    new AlertDialog.Builder(Support_GP.this)
                            .setTitle("No GP details found")
                            .setMessage("You will need to provide us with your GP's details before proceeding")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("Enter GP details", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent i = new Intent(Support_GP.this, GP_Details.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    CustomToast.createToast(Insurance_details_view.this,"Insurance details Submitted", false);
                                    startActivity(i);
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent x = new Intent(Support_GP.this, HomepageActivity.class);
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
                CustomToast.createToast(Support_GP.this,"Error",true);
            }


        });
//        if user has GP registered continue
        queries = new ArrayList<>();
        queries.add("An upcoming appointment");
        queries.add("My current medication");       // add queries to arraylist for dropdown menu
        queries.add("My Medi-Predict result");
        queries.add("Other");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Support_GP.this,android.R.layout.simple_spinner_item,queries);  // set queries to dropdown
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        queryspinner.setAdapter(arrayAdapter);
        queryspinner.setOnItemSelectedListener(this);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Support_GP.this, Support.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(i);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() { // send query to database
            @Override
            public void onClick(View view) {
                SupportGPdetail supportGPdetail = new SupportGPdetail(queryspinner.getSelectedItem().toString(),radioButton.getText().toString(),editText.getText().toString());
                mDatabase.getReference().child("Patients").child(userKey).child("Associated GP").child("Support Query").setValue(supportGPdetail);
                Intent i = new Intent(Support_GP.this, HomepageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                CustomToast.createToast(Support_GP.this,"Support request Submitted", false);
                startActivity(i);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = queryspinner.getSelectedItem().toString();

        if ( item.equals("An upcoming appointment") ){
            radioGroup.setVisibility(View.VISIBLE);
            editText.setHint("Please enter the date and time you would like to reschedule to");
            radioButton1.setText("Reschedule my appointment");
            radioButton2.setText("Cancel my appointment");
            radioButton3.setText("General query about my appointment");

        }
        if ( item.equals("My current medication") ){
            radioGroup.setVisibility(View.VISIBLE);                                                                 //error checking
            editText.setHint("Please provide us with the details of your query");
            radioButton1.setText("Current dosage");
            radioButton2.setText("New perscription needed");
            radioButton3.setText("General query about my medications");
        }
        if ( item.equals("My Medi-Predict result") ){
            radioGroup.setVisibility(View.VISIBLE);
            editText.setHint("Please provide us with the details of your query");
            radioButton1.setText("My result seems incorrect");
            radioButton2.setText("I cannot see my result");
            radioButton3.setText("General query about Medi-Predict");
        }
        if ( item.equals("Other") ){
            radioGroup.setVisibility(View.INVISIBLE);
            editText.setHint("Please provide us with the details of your query");
            radioButton.setText("Other");
            radioButton2.setText("Other");
            radioButton3.setText("Other");
            radioButton.toggle();

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void checkButton(View v) {

        int radioid = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioid);
        if (radioButton.getText().equals("Reschedule my appointment")){
            editText.setHint("Please enter the date and time you would like to reschedule to");
        }
        if (radioButton.getText().equals("Cancel my appointment")){                                     // changing hint of textfield as required
            editText.setHint("Please state the reason for cancelling");
        }
        if (!(radioButton.getText().equals("Cancel my appointment")) && ! (radioButton.getText().equals("Reschedule my appointment"))){
            editText.setHint("Please provide us with the details of your query");
        }

//        CustomToast.createToast(this,"selected" + radioButton.getText(),false);




    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Support_GP.this,Support.class);
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
                Intent intent = new Intent(Support_GP.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }
}