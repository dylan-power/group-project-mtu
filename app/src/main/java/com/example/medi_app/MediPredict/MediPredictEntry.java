package com.example.medi_app.MediPredict;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import com.example.medi_app.HomepageActivity;
import com.example.medi_app.MainActivity;
import com.example.medi_app.R;
import com.google.firebase.auth.FirebaseAuth;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


public class MediPredictEntry extends AppCompatActivity{

    Button btnAlzheimers;
    Button btnDiabetes;
    Button btnHeartDisease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medi_predict_entry);

        btnAlzheimers = findViewById(R.id.AlzheimersButton);
        btnDiabetes = findViewById(R.id.DiabetesButton);
        btnHeartDisease = findViewById(R.id.HeartDiseaseButton);


        btnAlzheimers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediPredictEntry.this, AlzheimersTest.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        btnDiabetes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediPredictEntry.this, DiabetesTest.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        btnHeartDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MediPredictEntry.this, HeartDiseaseTest.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MediPredictEntry.this,HomepageActivity.class);
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
                Intent intent = new Intent(MediPredictEntry.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }
}

