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

public class AlzheimersTest extends AppCompatActivity {

    Button btnSubmit;
    EditText age, sex, education, ses, mmse, etiv, nwbv, asf;
    //FirebaseAuth mFirebaseAuth;
    //FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alzheimers_test);
        EditText  age = findViewById(R.id.editAnsAge);
        EditText  sex = findViewById(R.id.editAnsSex);
        EditText  education = findViewById(R.id.editAnsEducation);
        EditText  ses = findViewById(R.id.editAnsSes);
        EditText mmse = findViewById(R.id.editAnsMmse);
        EditText etiv = findViewById(R.id.editAnsEtiv);
        EditText nwbv = findViewById(R.id.editAnsNwbv);
        EditText asf = findViewById(R.id.editAnsAsf);
        Button btnSubmit = findViewById(R.id.buttonSubmit);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AlzheimersTest.this, MediPredictEntry.class);
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
                Intent intent = new Intent(AlzheimersTest.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }
}

