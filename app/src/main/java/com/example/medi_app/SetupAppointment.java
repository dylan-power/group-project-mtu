package com.example.medi_app;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;

import com.example.medi_app.model.appointmentDetail;
import com.example.medi_app.util.CustomToast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SetupAppointment extends AppCompatActivity { // class for setting up appointments
    Spinner spinner;
    Spinner visitspinner;
    DatabaseReference databaseReference;
    List<String> gps;
    List<String> visit;
    EditText info;
    String mygpsname;                                               // set up views
    DatePickerDialog.OnDateSetListener mdatesetlistener;
    TimePickerDialog.OnTimeSetListener mtimesetlistener;
    TextView apointmentdate;
    TextView apointmenttime;
    TextView price;
    int hour, minute;
    Button cancel;
    Button submit;
    int cost = 55;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_appointment);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mDb = mDatabase.getReference();                   //firebase
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String userKey = user.getUid();

        spinner = findViewById(R.id.appointment_mygpspinner);
        apointmentdate = findViewById(R.id.appointment_input_date);
        apointmenttime = findViewById(R.id.appointment_input_time);
        visitspinner = findViewById(R.id.appointment_visit_spinner);
        info = findViewById(R.id.appointment_query_text);               // initialise views
        gps = new ArrayList<>();
        visit = new ArrayList<>();
        visit.add("General Checkup");
        visit.add("Blood Test + €50");
        visit.add("Blood pressure test +€50");              // add types of visits to arraylist
        visit.add("Flu Vaccine +€20");
        visit.add("Covid Booster Shot (Free)");
        visit.add("ECG test +€80");
        cancel = findViewById(R.id.appointmentcancelbutton2);
        submit = findViewById(R.id.appointmentsubmitbutton2);
        price = findViewById(R.id.price_TV);



        databaseReference = FirebaseDatabase.getInstance().getReference();
        mDb.child("Patients").child(userKey).child("Associated GP").addValueEventListener(new ValueEventListener() { // find users registered GP

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                mygpsname = snapshot.child("_Name").getValue(String.class);
                if(mygpsname==null){
                    mygpsname = "No GP";                                                // if gp not entered yet, allow user to select from other available doctors
                    gps.add(mygpsname + " Please select a doctor to visit");
                    gps.add("Select a different doctor");
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SetupAppointment.this, android.R.layout.simple_spinner_item, gps);       // set the dropdown to the gp list
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinner.setAdapter(arrayAdapter);
                }
                    else {                                                                      // if gp details entered this will display on the dropdown with (My GP) next to it
                    gps.add(mygpsname + " (My GP)");
                    gps.add("Select a different doctor");                                               //allow user to select a different doctor also

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SetupAppointment.this, android.R.layout.simple_spinner_item, gps);   // set the dropdown to the gp list
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinner.setAdapter(arrayAdapter);

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if (spinner.getSelectedItem().toString().equals("Select a different doctor")) {             // if user wants to select a different doctor
                    mDb.child("Medical Professionals").addValueEventListener(new ValueEventListener() {        // find all doctors in Firebase

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for (DataSnapshot childsnapshot : snapshot.getChildren()) {

                                String spinnerGPfirstname = childsnapshot.child("firstName").getValue(String.class);
                                String spinnerGPlastname = childsnapshot.child("surname").getValue(String.class);           // set their names
                                String name = spinnerGPfirstname + " " + spinnerGPlastname;
                                gps.remove(1);                                                                      // remove "select a different doctor" from dropdown
                                gps.add(1,"Select a doctor");                                                   // change this to "select a doctor"
                                gps.add(name);                                                                        // add the doctors names to the arraylist
                            }
                            int i;

                            // iterating over list to find gps name so its not duplicated -- if the user has selected a different doctor make sure it doesnt display their registered GP twice
                            for (i = 1; i < gps.size(); i++) {

                                if (gps.get(i).toString().contains(mygpsname)){
                                    gps.remove(i); //remove that name
                                }
                                else {
                                    Log.d(TAG, "onDataChange: ");
                                }
                                
                            }

                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(SetupAppointment.this, android.R.layout.simple_spinner_item, gps);         // set all the gps to the dropdown
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                            spinner.setAdapter(arrayAdapter);
                            spinner.setSelection(1);


                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        apointmentdate.setOnClickListener(new View.OnClickListener() {      // date picker
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int syear = cal.get(Calendar.YEAR);
                int smonth = cal.get(Calendar.MONTH);
                int sday = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(SetupAppointment.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mdatesetlistener,
                        syear,smonth,sday);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mdatesetlistener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int syear, int smonth, int sday) {
                smonth = smonth +1; //month starts at 0 so add 1
                String startdate = sday + "/" + smonth + '/' + syear;
                apointmentdate.setText(startdate);

            }
        };

        ArrayAdapter<String> arrayAdapterr = new ArrayAdapter<>(SetupAppointment.this, android.R.layout.simple_spinner_item, visit);    // set the types of visits array to another dropdown
        arrayAdapterr.setDropDownViewResource(android.R.layout.simple_spinner_item);
        visitspinner.setAdapter(arrayAdapterr);




        visitspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = visitspinner.getSelectedItem().toString();

                if (item.equals("General Checkup")) {
                    cost =55;
                }

                if (item.equals("Blood Test + €50")) {
                    cost =55+50;
                }

                if (item.equals("Blood pressure test +€50")) {
                    cost =55+50;
                }
                                                                                            // there is a general charge of 55 for a visit
                if (item.equals("Flu Vaccine +€20")) {
                    cost =55+20;
                }

                if (item.equals("Covid Booster Shot (Free)")) {
                    cost =55;
                }

                if (item.equals("ECG test +€80")) {
                    cost = 55+80;
                }

               price.setText("€" + cost);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (spinner.getSelectedItem().toString().equals("No GP Please select a doctor to visit")) {
                    spinner.requestFocus();
                    TextView errorText = (TextView) spinner.getSelectedView();
                    errorText.setError("Select a doctor");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error

                }
                if (spinner.getSelectedItem().toString().equals("Select a doctor")) {
                    spinner.requestFocus();
                    TextView errorText = (TextView) spinner.getSelectedView();
                    errorText.setError("Select a doctor");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                                                                                                                                        // error checking
                }

                if(apointmentdate.getText().toString().equals("Enter Date")){
                    apointmentdate.setError("Please enter a date");
                    apointmentdate.requestFocus();
                    CustomToast.createToast(SetupAppointment.this,"Please select a Date",true);
                } else if (apointmenttime.getText().toString().equals("Enter Time")){
                    apointmenttime.setError("Please enter a time");
                    apointmenttime.requestFocus();
                    CustomToast.createToast(SetupAppointment.this,"Please select a Time",true);

                } else if (!(apointmentdate.getText().toString().equals("Enter Date") && apointmenttime.getText().toString().equals("Enter Time"))){ // if everthing entered, send to firebase


                    appointmentDetail appointmentDetail = new appointmentDetail(spinner.getSelectedItem().toString(), apointmentdate.getText().toString(), apointmenttime.getText().toString(), visitspinner.getSelectedItem().toString(), info.getText().toString(),price.getText().toString());
                    mDatabase.getReference().child("Patients").child(userKey).child("Upcoming Appointment").setValue(appointmentDetail);
                    Intent i = new Intent(SetupAppointment.this, HomepageActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    CustomToast.createToast(SetupAppointment.this, "Appointment details Submitted", false);
                    CustomToast.createToast(SetupAppointment.this, "Your GP will contact you as soon as possible regarding this appointment", false);
                    startActivity(i);
                }}
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SetupAppointment.this, HomepageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SetupAppointment.this,HomepageActivity.class);
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
                Intent intent = new Intent(SetupAppointment.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }
    public void popTimePicker(View view) // time picker dialog
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                apointmenttime.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };

         int style = AlertDialog.THEME_HOLO_LIGHT;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}