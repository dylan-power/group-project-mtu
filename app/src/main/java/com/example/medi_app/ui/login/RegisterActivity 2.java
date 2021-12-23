package com.example.medi_app.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.medi_app.R;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {
    TextView alreadyhasaccount;
    EditText inputemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}