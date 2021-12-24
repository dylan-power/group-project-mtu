package com.example.medi_app.util;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medi_app.R;

import java.net.ContentHandler;

public class CustomToast { // custom toast class
    public static void createToast(Context context, String message, boolean error)
    {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.customtoast,null);
        TextView toastTextView = view.findViewById(R.id.textViewToast);
        SpannableString spannableString = new SpannableString(message);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 0,spannableString.length(), 0);
        toastTextView.setText(spannableString);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);



        toast.setGravity(Gravity.BOTTOM, 32, 32);
        toast.show();
    }
}
