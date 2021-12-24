package com.example.medi_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.medi_app.model.Dialog;
import com.example.medi_app.model.insuranceDetail;
import com.example.medi_app.util.CustomToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class insurance_details extends AppCompatActivity implements AdapterView.OnItemSelectedListener, Dialog.DialogListener { // class for entering insurance details
    private static final String TAG = "insurance_details";
    Spinner insurancecomp_spinner;                              // set up views
    EditText price;
    DatePickerDialog.OnDateSetListener mdatesetlistener;
    DatePickerDialog.OnDateSetListener enddatesetlistener;
    List<String> Insurance_companies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_details);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();             //firebase
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDb = mDatabase.getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userKey = user.getUid();

        insurancecomp_spinner = findViewById(R.id.insurance_spinner);           //initialise views
        price = findViewById(R.id.costinsurance);
        Button cancelbutton = findViewById(R.id.insurance_cancelbutton);
        Button submit = findViewById(R.id.submit_insurance_dets);
        Insurance_companies = new ArrayList<>();
        EditText policyNumber = findViewById(R.id.policy_number_et);
        TextView start_date = findViewById(R.id.DP_insurance_startdate_tv);
        TextView end_date = findViewById(R.id.DP_insurance_enddate_tv);
        TextView a = findViewById(R.id.dialog_edit_insurancecompany);
        BufferedReader reader;
        Insurance_companies.add(0,"Choose your Insurance Company");     // add to arraylist so user can enter a different insurance company if required
        Insurance_companies.add("My Insurance Company is not listed here");

        mDb.child("Patients").child(userKey).child("Insurance Company Details").addValueEventListener(new ValueEventListener() { // find users insurance details.
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String provider = String.valueOf(snapshot.child("insurance_company_On_File").getValue());       // set the values
                String polno = String.valueOf(snapshot.child("policy_Number").getValue());
                String start = String.valueOf(snapshot.child("policy_Start").getValue());
                String end = String.valueOf(snapshot.child("policy_End").getValue());

                if ( !(provider.equals("null") && polno.equals("null") && start.equals("null") && end.equals("null"))){  // if user has already entered Insurance details, show dialog to say that they have already entered them


                    new AlertDialog.Builder(insurance_details.this)
                            .setTitle("Insurance details found")
                            .setMessage("You have already entered your insurance details!\nYou can click 'View' to see them, or press 'Cancel' to submit your details again")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("View", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent i = new Intent(insurance_details.this, Insurance_details_view.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    CustomToast.createToast(Insurance_details_view.this,"Insurance details Submitted", false);
                                    startActivity(i);
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                CustomToast.createToast(insurance_details.this,"Error",true);
            }


        });



        try{
            final InputStream file = getAssets().open("insurancefile.txt");  // insurance companys are stored in a file along with their email and phone number
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();                                // read the lines of the file
            while(line != null){
                Insurance_companies.add(line);                      // add each line to the insurance company arraylist
                line = reader.readLine();
            }
        } catch(IOException ioe){
            ioe.printStackTrace();
        }



        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(insurance_details.this,android.R.layout.simple_spinner_item,Insurance_companies); // set the spinner dropdown values to insurance companys arraylist
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        insurancecomp_spinner.setAdapter(arrayAdapter);
        insurancecomp_spinner.setOnItemSelectedListener(this);


        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();
                int syear = cal.get(Calendar.YEAR);
                int smonth = cal.get(Calendar.MONTH);
                int sday = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(insurance_details.this,          // datepicker dialog to enter start date
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mdatesetlistener,
                        syear,smonth,sday);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mdatesetlistener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int syear, int smonth, int sday) {
                smonth = smonth +1; //month starts at 0 so add 1
                String startdate = sday + "/" + smonth + '/' + syear;
                start_date.setText(startdate);

            }
        };


     end_date.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Calendar cal = Calendar.getInstance();
             int eyear = cal.get(Calendar.YEAR);
             int emonth = cal.get(Calendar.MONTH);                                                      // datepicker dialog to enter end date
             int eday = cal.get(Calendar.DAY_OF_MONTH);
             DatePickerDialog d = new DatePickerDialog(insurance_details.this,
                     android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                     enddatesetlistener,
                     eyear,emonth,eday);
             d.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
             d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
             d.show();
         }
     });
     enddatesetlistener = new DatePickerDialog.OnDateSetListener() {
         @Override
         public void onDateSet(DatePicker enddatePicker, int eyear, int emonth, int eday) {
             emonth = emonth +1;
             String enddate = eday + "/" + emonth + '/' + eyear;
             end_date.setText(enddate);

         }
     };

     submit.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

         String insurance_email = null;
         String insurance_company = insurancecomp_spinner.getSelectedItem().toString();             //get selected insurance company from spinner
         final String insurance_company_phone = insurancecomp_spinner.getSelectedItem().toString();
         try {


             int phone = Integer.parseInt(insurance_company_phone.replaceAll("[\\D]", "")); // extract phone number
             String temp = insurance_company.replaceAll("\\d", "").replace("-", "");
             ;
             String EMAIL_PATTERN = "([^.@\\s]+)(\\.[^.@\\s]+)*@([^.@\\s]+\\.)+([^.@\\s]+)";                    // extract email address with email pattern
             String thename = temp.replaceAll(EMAIL_PATTERN, "").replace("-", "");
             ;
             Matcher m = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+").matcher(insurancecomp_spinner.getSelectedItem().toString());
             while (m.find()) {
                 insurance_email = m.group();

             final String policyNo = policyNumber.getText().toString();
         final String startdate = start_date.getText().toString();                          //get all values and store them to string
         final String endddate = end_date.getText().toString();
         final String cos = price.getText().toString();


                if (insurance_company.equals("Choose your Insurance Company")) {
                    insurancecomp_spinner.requestFocus();
                    TextView errorText = (TextView) insurancecomp_spinner.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    


         } else if (policyNo.isEmpty()) {                                                               //error checking
                    policyNumber.setError("Please provide your Policy Number");
                    policyNumber.requestFocus();
         } else if (startdate.isEmpty()) {
                    start_date.setError("Please enter the start date of your policy");
                    start_date.requestFocus();
         } else if (endddate.isEmpty()) {
                    end_date.setError("Please enter the end date of your policy");
                    end_date.requestFocus();
                } else if (cos.isEmpty()) {
                    price.setError("Please enter the amount you are paying");
                    price.requestFocus();

         }

                else if (!(insurance_company.isEmpty() && insurance_company.equals("Choose your Insurance Company"))) {     // add to firebase

                    // firebase here
                    insuranceDetail insuranceDetail = new insuranceDetail(thename.trim(),policyNo,startdate,endddate, phone,insurance_email,cos);
                    mDatabase.getReference().child("Patients").child(userKey).child("Insurance Company Details").setValue(insuranceDetail);
                    Intent i = new Intent(insurance_details.this, HomepageActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    CustomToast.createToast(insurance_details.this,"Insurance details Submitted", false);
                    startActivity(i);

                }
             }
         } catch (NumberFormatException e) {
             CustomToast.createToast(insurance_details.this,"Please select an insurance company!",true);
         }
         }

     });


        cancelbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(insurance_details.this, My_Medical_Information.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

         }

















    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(insurance_details.this,My_Medical_Information.class);
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
                Intent intent = new Intent(insurance_details.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }
// for dropdown spinner
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String item = insurancecomp_spinner.getSelectedItem().toString();
                if (item.equals("My Insurance Company is not listed here")) {
                    openDialog();
                    CustomToast.createToast(this,item,false);
                }


    }

    public void openDialog() {
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(),"Insurance Company");
    }

    @Override
    public void applyText(String company,int phone,String email) {
        if (company.isEmpty()) {
            CustomToast.createToast(this, "Please enter your Insurance company's name", false);
        }
        if (String.valueOf(phone).isEmpty()) {
            CustomToast.createToast(this, "Please enter a valid Phone Number!", false);
        }
        if (email.isEmpty()) {
            CustomToast.createToast(this, "Please enter your Insurance company's Email", false);
        }
        if (!(company.isEmpty()&& String.valueOf(phone).isEmpty() && email.isEmpty())) {
            Insurance_companies.add(company + "                                                                                 - " + email + "   " + phone);
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}