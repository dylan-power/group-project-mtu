package com.example.medi_app.MediPredict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medi_app.R;
import com.example.medi_app.ml.DiabetesModel;

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
        }
