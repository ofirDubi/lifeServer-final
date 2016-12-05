package com.example.user.navigatelifesaver;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import at.markushi.ui.CircleButton;

public class PatientPick extends AppCompatActivity {
    ImageView patient_picture;
    TextView patient_name;
    TextView patient_age;
    TextView diagnosis;
    CircleButton accept_patient;
    CircleButton switch_patient;
    Button chat_button;
    int patient_num = 0;
    boolean add_chat = false;
    private ProgressDialog progressDialog;
    ServerRequest srequest = new ServerRequest();
    boolean bool = true;
    String USERNAME;
    boolean load_complite = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_pick);
        USERNAME = ((GlobalVars) this.getApplication()).getUSERNAME();


        patient_picture = (ImageView) findViewById(R.id.patient_picture);


        patient_name = (TextView) findViewById(R.id.patient_name);
        patient_age= (TextView) findViewById(R.id.patient_age);
        diagnosis = (TextView) findViewById(R.id.diagnosis);

        switch_patient = (CircleButton) findViewById(R.id.switch_patient);
        accept_patient = (CircleButton) findViewById(R.id.accept_patient);
        chat_button = (Button) findViewById(R.id.chat_button);
        new LoadViewTask().execute();






        chat_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){

                startActivity(new Intent(PatientPick.this, ChatView.class).putExtra("userID", USERNAME));
            }
        });

        switch_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // patient cahnges
                Log.d("Doing things", "Doing things");
                setPatient();

            }
        });

        accept_patient.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Thread accept_thread =  new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String res = null;
                        try {
                            res = "";
                            if(srequest.patient_list.size() !=0){

                                res = srequest.add_to_chat(  USERNAME, srequest.patient_list.get((patient_num+1)%srequest.patient_list.size()).getName());
                                Log.d("Patient added to chat", "patient '" + srequest.patient_list.get((patient_num+1)%srequest.patient_list.size()).getName() +"' was added");
                                add_chat = true;
                                srequest.patient_list.remove((patient_num+1)%srequest.patient_list.size());
                                if(srequest.patient_list.size() != 0){
                                    patient_num = patient_num%srequest.patient_list.size();

                                }else{
                                    patient_num = 0;
                                    Log.d("No more patients", "last patient added");
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setPatient();
                                    }
                                });
                            }else{
                                Log.d("Cant add patients: ", "no more patients left");
//                                res = srequest.add_to_chat(  USERNAME, srequest.patient_list.get((patient_num-1)%(srequest.patient_list.size()+1)).getName());
//                                 Log.d("Patient added to chat", "patient '" + patient_name.getText().toString() +"' was added");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setPatient();
                                    }
                                });
                            }

                        } catch (IOException e) {
                            Log.d("ERROR ACCORED", "error accored on thread");
                            e.printStackTrace();
                        }
                        Log.d("Add was made", res.toString());
                        if(srequest.patient_list.size() != 0 ){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(PatientPick.this, "patient: " + srequest.patient_list.get((patient_num-1)%srequest.patient_list.size()).getName() + " was added to chat",
                                            Toast.LENGTH_LONG).show();
                                    return;
                                }
                            });
                        }

                    }
                });
                accept_thread.start();


            }
        });
    }
    void setPatient (){
        if(srequest.patient_list !=null && srequest.patient_list.size() !=0 ){

                patient_name.setText("Patient's name: " + srequest.patient_list.get(patient_num).getName());
                patient_age.setText("Patient's age: " + srequest.patient_list.get(patient_num).getAge());
                diagnosis.setText("Diagnosis is : " + srequest.patient_list.get(patient_num).getDiagnosis());
                if (!srequest.patient_list.get(patient_num).getImageBitmap().equals(null)) {
                    byte[] decodedString = Base64.decode(srequest.patient_list.get(patient_num).getImageBitmap(), Base64.URL_SAFE);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    patient_picture.setImageBitmap(decodedByte);
                }
               patient_num = (patient_num + 1) % srequest.patient_list.size();
        
        }else{
            Log.d("No More Patients", "No More Patients");
            patient_picture.setVisibility(View.INVISIBLE);
            patient_name.setText("No patients to show");
            patient_age.setText("");
            diagnosis.setText("");

        }




    }





    //To use the AsyncTask, it must be subclassed
    private class LoadViewTask extends AsyncTask<Void, Integer, Void> {

        //Before running code in separate thread
        @Override
        protected void onPreExecute() {
            //Create a new progress dialog
            progressDialog = new ProgressDialog(PatientPick.this);
            //Set the progress dialog to display a horizontal progress bar
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            //Set the dialog title to 'Loading...'
            progressDialog.setTitle("Loading...");
            //Set the dialog message to 'Loading application View, please wait...'
            progressDialog.setMessage("Loading data, please wait...");
            //This dialog can't be canceled by pressing the back key
            progressDialog.setCancelable(false);
            //This dialog isn't indeterminate
            progressDialog.setIndeterminate(false);
            //The maximum number of items is 100
            progressDialog.setMax(100);
            //Set the current progress to zero
            progressDialog.setProgress(0);
            //Display the progress dialog
            progressDialog.show();

            //to change the way of showing the progress
            //progressDialog = ProgressDialog.show(LoadingScreenActivity.this,"Loading...",
            //        "Loading application View, please wait...", false, false);
        }

        //The code to be executed in a background thread.

        protected Void doInBackground(Void... params) {
            /*This is just a code that delays the thread execution 4 times,
             during 850 milliseconds and updates the current progress. This
            is where the code that is going to be executed on a background
            thread must be placed.
            */
            try
            {
                srequest.doctor_view_setup(USERNAME);




            }

            catch (Exception e)
            {
                Log.e("ERROR IN VIEW_SETUP", e.getMessage());
                return null;

            }


            return null;
        }


        //Update the progress
        @Override
        protected void onProgressUpdate(Integer... values) {
            //set the current progress of the progress dialog
            progressDialog.setProgress(values[0]);
        }




        //after executing the code in the thread
        @Override
        protected void onPostExecute(Void result) {
            //close the progress dialog
            setPatient();
            progressDialog.dismiss();
            //initialize the View

        }
    }

}