package com.example.medi_app.MediPredict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.medi_app.R;

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
}

