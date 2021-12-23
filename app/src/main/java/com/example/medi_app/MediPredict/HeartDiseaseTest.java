package com.example.medi_app.MediPredict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.medi_app.HomepageActivity;
import com.example.medi_app.MainActivity;
import com.example.medi_app.R;
import com.example.medi_app.Signup;
import com.example.medi_app.Support;
import com.example.medi_app.Support_Insurance;

public class HeartDiseaseTest extends AppCompatActivity {


    Button btnSubmit;
    EditText age, sex, chestPain, restingBP, cholesterol, fastingBloodSugar, restEcg, maxHR, exAngina,
            stDepression, peakExST, numVessels, thal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_disease_test);

        age = findViewById(R.id.editAnsAge);
        sex = findViewById(R.id.editAnsSex);
        chestPain = findViewById(R.id.editAnsChestPain);
        restingBP = findViewById(R.id.editAnsRestingBP);
        cholesterol = findViewById(R.id.editAnsCholesterol);
        fastingBloodSugar = findViewById(R.id.editAnsFastingBloodSugar);
        restEcg = findViewById(R.id.editAnsRestingECG);
        maxHR = findViewById(R.id.editAnsMaxHR);
        exAngina = findViewById(R.id.editAnsExAngina);
        stDepression = findViewById(R.id.editAnsSTdepression);
        peakExST = findViewById(R.id.editAnsPeakExST);
        numVessels = findViewById(R.id.editAnsNumVessels);
        thal = findViewById(R.id.editAnsThal);
        btnSubmit = findViewById(R.id.buttonSubmit);

        //mFirebaseAuth = FirebaseAuth.getInstance();
        //firebaseDatabase = FirebaseDatabase.getInstance();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String Age = age.getText().toString();
                final String Sex = sex.getText().toString();
                final String ChestPain = chestPain.getText().toString();
                final String RestingBP = restingBP.getText().toString();
                final String Cholesterol = cholesterol.getText().toString();
                final String FastingBloodSugar = fastingBloodSugar.getText().toString();
                final String RestEcg = restEcg.getText().toString();
                final String MaxHR = maxHR.getText().toString();
                final String ExAngina = exAngina.getText().toString();
                final String StDepression = stDepression.getText().toString();
                final String PeakExST = peakExST.getText().toString();
                final String NumVessels = numVessels.getText().toString();
                final String Thal = thal.getText().toString();


                if (Age.isEmpty()) {
                    age.setError("Please provide the age");
                    age.requestFocus();
                } else if (Sex.isEmpty()) {
                    sex.setError("Please provide the sex:" +
                            " 0 = female\n" +
                            " 1 = male");
                    sex.requestFocus();
                } else if (ChestPain.isEmpty()) {
                    chestPain.setError("Please provide the chest pain type:" +
                            "1 = typical angina\n" +
                            " 2 = atypical angina\n" +
                            " 3 = non-anginal pain\n" +
                            " 4 = asymptotic");
                    chestPain.requestFocus();
                } else if (RestingBP.isEmpty()) {
                    restingBP.setError("Please provide the resting blood pressure value of an individual in mmHg (unit)");
                    restingBP.requestFocus();
                } else if (Cholesterol.isEmpty()) {
                    cholesterol.setError("Please provide the serum cholesterol in mg/dl (unit)");
                    cholesterol.requestFocus();
                } else if (FastingBloodSugar.isEmpty()) {
                    fastingBloodSugar.setError("Please provide the fasting blood sugar");
                    fastingBloodSugar.requestFocus();
                } else if (RestEcg.isEmpty()) {
                    restEcg.setError("Please provide the resting ECG: \n" +
                            " 0 = normal\n" +
                            " 1 = having ST-T wave abnormality\n" +
                            " 2 = left ventricular hypertrophy");
                    restEcg.requestFocus();
                } else if (MaxHR.isEmpty()) {
                    maxHR.setError("Please provide the max heart rate achieved");
                    maxHR.requestFocus();
                } else if (ExAngina.isEmpty()) {
                    exAngina.setError("Please provide for exercise induced angina: \n" +
                            " 0 = no\n" +
                            " 1 = yes");
                    exAngina.requestFocus();
                } else if (StDepression.isEmpty()) {
                    stDepression.setError("Please provide the value for ST depression induced by exercise relative to rest");
                    stDepression.requestFocus();
                } else if (PeakExST.isEmpty()) {
                    peakExST.setError("Please provide the eak exercise ST segment : \n" +
                            "              1 = up-sloping\n" +
                            "              2 = flat\n" +
                            "              3 = down-sloping");
                    peakExST.requestFocus();
                } else if (NumVessels.isEmpty()) {
                    numVessels.setError("Please provide the number of major vessels (0-3) colored by flourosopy");
                    numVessels.requestFocus();
                } else if (Thal.isEmpty()) {
                    thal.setError("Please provide the thalassemia:\n" +
                            "3 = normal\n" +
                            "6 = fixed defect\n" +
                            "7 = reversible defect");
                    thal.requestFocus();
                }

                Intent i = new Intent(HeartDiseaseTest.this, HeartDiseaseResult.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }
        });
    }
}
