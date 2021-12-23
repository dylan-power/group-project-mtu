package com.example.medi_app.MediPredict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.medi_app.R;

public class AlzheimersTest extends AppCompatActivity {

    Button btnSubmit;
    EditText age, sex, education, ses, mmse, etiv, nwbv, asf;
    //FirebaseAuth mFirebaseAuth;
    //FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alzheimers_test);
        age = findViewById(R.id.editAnsAge);
        sex = findViewById(R.id.editAnsSex);
        education = findViewById(R.id.editAnsEducation);
        ses = findViewById(R.id.editAnsSes);
        mmse = findViewById(R.id.editAnsMmse);
        etiv = findViewById(R.id.editAnsEtiv);
        nwbv = findViewById(R.id.editAnsNwbv);
        asf = findViewById(R.id.editAnsAsf);

        //mFirebaseAuth = FirebaseAuth.getInstance();
        //firebaseDatabase = FirebaseDatabase.getInstance();
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String Age = age.getText().toString();
                final String Sex = sex.getText().toString();
                final String Education = education.getText().toString();
                final String Ses = ses.getText().toString();
                final String Mmse = mmse.getText().toString();
                final String Etiv = etiv.getText().toString();
                final String Nwbv = nwbv.getText().toString();
                final String Asf = asf.getText().toString();


                if (Age.isEmpty()) {
                    age.setError("Please provide the age");
                    age.requestFocus();
                } else if (Sex.isEmpty()) {
                    sex.setError("Please provide the sex:" +
                            " 0 = female\n" +
                            " 1 = male");
                    sex.requestFocus();
                } else if (Education.isEmpty()) {
                    education.setError("Please provide the age of final year of formal education");
                    education.requestFocus();
                } else if (Ses.isEmpty()) {
                    ses.setError("Please provide the socio-economic status");
                    ses.requestFocus();
                } else if (Mmse.isEmpty()) {
                    mmse.setError("Please provide the Mini Mental State Exam results");
                    mmse.requestFocus();
                } else if (Etiv.isEmpty()) {
                    etiv.setError("Please provide the estimated total intracranial volume");
                    etiv.requestFocus();
                } else if (Nwbv.isEmpty()) {
                    nwbv.setError("Please provide the normalised whole brain volume");
                    nwbv.requestFocus();
                } else if (Asf.isEmpty()) {
                    asf.setError("Please provide the atlas scaling factor");
                    asf.requestFocus();
                }

                Intent i = new Intent(AlzheimersTest.this, AlzheimersResult.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);

            }
        });
    }
}

