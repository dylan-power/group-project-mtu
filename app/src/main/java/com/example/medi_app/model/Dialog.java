package com.example.medi_app.model;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.medi_app.R;
import com.example.medi_app.util.CustomToast;

public class Dialog extends AppCompatDialogFragment { // class for Dialog that shows when patients insurance company is not on file

    private EditText editTextinsurancecompany;
    private EditText edittextphone;
    private EditText edittextemail;
    private DialogListener listener;
    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(view).setTitle("Enter your Insurance Company's Details")
                .setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                        String company = editTextinsurancecompany.getText().toString(); // get values from the textfields
                        String phoneString = edittextphone.getText().toString();
                        String emailstring = edittextemail.getText().toString();



                        int phone = Integer.parseInt(phoneString.replaceAll("[\\D]", ""));

                        if(company.equals("")){
                            editTextinsurancecompany.setError("Please enter your Insurance company's name"); // error checking
                            editTextinsurancecompany.requestFocus();
                        }
                            if(phoneString.isEmpty()){
                                edittextphone.setError("Please enter your Insurance company's name");
                                edittextphone.requestFocus();
                            }
                            if(emailstring.isEmpty()){
                                edittextemail.setError("Please enter your Insurance company's name");
                                edittextemail.requestFocus();
                            }

                        listener.applyText(company,phone,emailstring);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            CustomToast.createToast(getContext(),"Please enter a valid Phone Number!",true);
                        }

                    }
                });
        editTextinsurancecompany = view.findViewById(R.id.dialog_edit_insurancecompany);
        edittextphone = view.findViewById(R.id.dialog_edit_insurancenumber);
        edittextemail = view.findViewById(R.id.dialog_edit_insuranceemail);


        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+ "Must implement dialog listener");
        }
    }

    public interface DialogListener{
        void applyText(String company,int phone, String email);
    }
}
