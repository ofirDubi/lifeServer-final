package com.example.user.navigatelifesaver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    ServerRequest srequest = new ServerRequest();
    boolean bool = true;
    String USERNAME;
    boolean load_complite = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_pick);
        USERNAME = ((GlobalVars) this.getApplication()).getUSERNAME();

        thread.start();
        patient_picture = (ImageView) findViewById(R.id.patient_picture);


        patient_name = (TextView) findViewById(R.id.patient_name);
        patient_age= (TextView) findViewById(R.id.patient_age);
        diagnosis = (TextView) findViewById(R.id.diagnosis);

        switch_patient = (CircleButton) findViewById(R.id.switch_patient);
        accept_patient = (CircleButton) findViewById(R.id.accept_patient);
        chat_button = (Button) findViewById(R.id.chat_button);


        while(true){

            if(load_complite){
                setPatient();
                break;
            }

        }

        chat_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){

                startActivity(new Intent(PatientPick.this, ChatView.class).putExtra("userID", USERNAME));
            }
        });

        switch_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // patient cahnges
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
                                Log.d("Patient added to chat", "patient '" + patient_name.getText().toString() +"' was added");
                                add_chat = true;
                                srequest.patient_list.remove((patient_num+1)%srequest.patient_list.size());
                                if(srequest.patient_list.size() != 0){
                                    patient_num = patient_num%srequest.patient_list.size();

                                }else{
                                    patient_num = 0;
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setPatient();
                                    }
                                });
                            }else{
//                                res = srequest.add_to_chat(  USERNAME, srequest.patient_list.get((patient_num-1)%(srequest.patient_list.size()+1)).getName());
//                                 Log.d("Patient added to chat", "patient '" + patient_name.getText().toString() +"' was added");
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
        if(srequest.patient_list.size() !=0 && srequest.patient_list !=null){
            patient_name.setText("Patient's name: "+ srequest.patient_list.get(patient_num).getName());
            patient_age.setText("Patient's age: " + srequest.patient_list.get(patient_num).getAge());
            diagnosis.setText("Diagnosis is :" + srequest.patient_list.get(patient_num).getDiagnosis());
            if(!srequest.patient_list.get(patient_num).getImageBitmap().equals(null)){
                byte[] decodedString = Base64.decode(srequest.patient_list.get(patient_num).getImageBitmap(), Base64.URL_SAFE );
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                patient_picture.setImageBitmap(decodedByte);
            }
            patient_num = (patient_num+1)%srequest.patient_list.size();

        }else{
            patient_picture.setVisibility(View.INVISIBLE);
            patient_name.setText("No patients to show");
            patient_age.setText("");
            diagnosis.setText("");

        }




    }

    Thread thread = new Thread(new Runnable()
    {
        @Override
        public void run()
        {
            try
            {
                srequest.doctor_view_setup(USERNAME);
                load_complite = true;




            }

            catch (Exception e)
            {
                return;

            }
        }
    });


}