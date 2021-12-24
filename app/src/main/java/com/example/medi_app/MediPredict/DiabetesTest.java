package com.example.medi_app.MediPredict;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.medi_app.GP_Details;
import com.example.medi_app.MainActivity;
import com.example.medi_app.My_Medical_Information;
import com.example.medi_app.R;
import com.google.firebase.auth.FirebaseAuth;

public class DiabetesTest extends AppCompatActivity {

    Button btnSubmit;
    EditText age, pregnancy, glucose, bloodPressure, skinThickness, insulin, bmi, diabetesPedigreeFunction ;
    //FirebaseAuth mFirebaseAuth;
    //FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes_test);

        age = findViewById(R.id.editAnsAge);
        pregnancy = findViewById(R.id.editAnsPregnancy);
        glucose = findViewById(R.id.editAnsGlucose);
        bloodPressure = findViewById(R.id.editAnsBloodPressure);
        skinThickness = findViewById(R.id.editAnsSkinThickness);
        insulin= findViewById(R.id.editAnsInsulin);
        bmi= findViewById(R.id.editAnsBmi);
        diabetesPedigreeFunction = findViewById(R.id.editAnsDiabetesPedigreeFunction);

        btnSubmit = findViewById(R.id.buttonSubmit);

        //mFirebaseAuth = FirebaseAuth.getInstance();
        //firebaseDatabase = FirebaseDatabase.getInstance();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String Age = age.getText().toString();
                final String Pregnancy = pregnancy.getText().toString();
                final String Glucose = glucose.getText().toString();
                final String BloodPressure = bloodPressure.getText().toString();
                final String SkinThickness = skinThickness.getText().toString();
                final String Insulin = insulin.getText().toString();
                final String Bmi = bmi.getText().toString();
                final String DiabetesPedigreeFunction = diabetesPedigreeFunction.getText().toString();


                if (Age.isEmpty()) {
                    age.setError("Please provide the age");
                    age.requestFocus();

                } else if (Pregnancy.isEmpty()) {
                    pregnancy.setError("Please provide the number of times pregnant");
                    pregnancy.requestFocus();

                } else if (Glucose.isEmpty()) {
                    glucose.setError("Please provide the plasma glucose concentration");
                    glucose.requestFocus();

                } else if (BloodPressure.isEmpty()) {
                    bloodPressure.setError("Please provide the blood pressure in mm HG");
                    bloodPressure.requestFocus();

                } else if (SkinThickness.isEmpty()) {
                    skinThickness.setError("Please provide the Triceps skin fold thickness (mm)");
                    skinThickness.requestFocus();

                } else if (Insulin.isEmpty()) {
                    insulin.setError("2-Hour serum insulin (mu U/ml)");

                } else if (Bmi.isEmpty()) {
                    bmi.setError("Please provide the Body mass index (weight in kg/(height in m)^2)");
                    bmi.requestFocus();

                } else if (DiabetesPedigreeFunction.isEmpty()) {
                    diabetesPedigreeFunction.setError("Please provide the diabetes pedigree function");
                    diabetesPedigreeFunction.requestFocus();
                }

                Intent i = new Intent(DiabetesTest.this, DiabetesResult.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DiabetesTest.this, MediPredictEntry.class);
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
                Intent intent = new Intent(DiabetesTest.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }
}

