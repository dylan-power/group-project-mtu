package com.example.medi_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Support_GP extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
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
        setContentView(R.layout.activity_support_gp);
        queryspinner = findViewById(R.id.supportgpspinner);
        radioGroup = findViewById(R.id.GPradioGroup);
        editText = findViewById(R.id.gp_querytext);
        cancel = findViewById(R.id.gpquerycancelbutton);
        submit = findViewById(R.id.gpquerysubmitbutton);
        radioButton1 = findViewById(R.id.GPradioButton);
        radioButton2 = findViewById(R.id.GPradioButton2);
        radioButton3 = findViewById(R.id.GPradioButton3);
        int radioid = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioid);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDb = mDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userKey = user.getUid();

        queries = new ArrayList<>();
        queries.add("An upcoming appointment");
        queries.add("My current medication");
        queries.add("My Medi-Predict result");
        queries.add("Other");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Support_GP.this,android.R.layout.simple_spinner_item,queries);
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

        submit.setOnClickListener(new View.OnClickListener() {
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
            radioGroup.setVisibility(View.VISIBLE);
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
        if (radioButton.getText().equals("Cancel my appointment")){
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