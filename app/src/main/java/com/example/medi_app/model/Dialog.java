package com.example.medi_app.model;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.medi_app.R;

public class Dialog extends AppCompatDialogFragment {

    private EditText editTextinsurancecompany;
    private EditText edittextphone;
    private DialogListener listener;
    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);
        builder.setView(view).setTitle("Enter your Insurance Company's Name")
                .setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String company = editTextinsurancecompany.getText().toString();
                        String phoneString = edittextphone.getText().toString();
                        int phone = Integer.parseInt(phoneString.replaceAll("[\\D]", ""));
                        listener.applyText(company,phone);

                    }
                });
        editTextinsurancecompany = view.findViewById(R.id.dialog_edit_insurancecompany);
        edittextphone = view.findViewById(R.id.dialog_edit_insurancenumber);


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
        void applyText(String company,int phone);
    }
}
