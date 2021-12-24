package com.example.medi_app.MediPredict;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medi_app.GP_Details;
import com.example.medi_app.MainActivity;
import com.example.medi_app.My_Medical_Information;
import com.example.medi_app.R;
import com.example.medi_app.ml.DiabetesModel;
import com.google.firebase.auth.FirebaseAuth;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class DiabetesResult extends AppCompatActivity {
    TextView textDiabResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes_result);

        textDiabResult = findViewById(R.id.textDiabetesResult);

                String probability = test(com.example.medi_app.MediPredict.DiabetesResult.this);
        textDiabResult.setText(probability);
            }

            public String test(Context context) {
                try {
                    DiabetesModel model = DiabetesModel.newInstance(context);

                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 8}, DataType.FLOAT32);
                    //inputFeature0.loadBuffer(byteBuffer);
                    ByteBuffer buffer = ByteBuffer.allocate(8 * 4);
                    buffer.putFloat(1);
                    buffer.putFloat(199);
                    buffer.putFloat(76);
                    buffer.putFloat(43);
                    buffer.putFloat(0);
                    buffer.putFloat((float) 42.9);
                    buffer.putFloat((float) 1.394);
                    buffer.putFloat(22);

                    inputFeature0.loadBuffer(buffer);

                    // Runs model inference and gets result
                    DiabetesModel.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    String result = Arrays.toString(outputFeature0.getFloatArray());

                    // release model resources if no longer used
                    model.close();

                    return result;

                } catch (IOException e) {
                    Toast.makeText(com.example.medi_app.MediPredict.DiabetesResult.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                return "probability";

            }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DiabetesResult.this, MediPredictEntry.class);
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
                Intent intent = new Intent(DiabetesResult.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }
        }
