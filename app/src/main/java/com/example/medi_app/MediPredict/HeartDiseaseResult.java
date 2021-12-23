package com.example.medi_app.MediPredict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medi_app.R;
import com.example.medi_app.ml.AlzheimersModel;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class HeartDiseaseResult extends AppCompatActivity {
    TextView textHeartResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_disease_result);

               textHeartResult = findViewById(R.id.textHeartDiseaseResult);

                String probability = test(com.example.medi_app.MediPredict.HeartDiseaseResult.this);
                textHeartResult.setText(probability);
            }

            public String test(Context context) {
                try {
                    AlzheimersModel model = AlzheimersModel.newInstance(context);

                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 8}, DataType.FLOAT32);
                    //inputFeature0.loadBuffer(byteBuffer);
                    ByteBuffer buffer = ByteBuffer.allocate(8 * 4);

                    buffer.putFloat(67);
                    buffer.putFloat(0);
                    buffer.putFloat(3);
                    buffer.putFloat(115);
                    buffer.putFloat(564);
                    buffer.putFloat(0);
                    buffer.putFloat(3);
                    buffer.putFloat(160);
                    buffer.putFloat(0);
                    buffer.putFloat((float) 1.6);
                    buffer.putFloat(2);
                    buffer.putFloat(0);
                    buffer.putFloat(7);

                    inputFeature0.loadBuffer(buffer);

                    // Runs model inference and gets result
                    AlzheimersModel.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    String result = Arrays.toString(outputFeature0.getFloatArray());

                    // release model resources if no longer used
                    model.close();

                    return result;

                } catch (IOException e) {
                    Toast.makeText(com.example.medi_app.MediPredict.HeartDiseaseResult.this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                return "probability";

            }
        }
