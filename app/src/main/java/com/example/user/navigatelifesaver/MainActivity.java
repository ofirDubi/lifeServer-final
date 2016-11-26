package com.example.user.navigatelifesaver;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean user_exists = false;
    ServerRequest serverRequest = new ServerRequest();
    EditText username;
    EditText userpassword;
    RadioGroup type;
    String user_type = "p";
    GlobalVars globalVars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        type = (RadioGroup) findViewById(R.id.type);
        Button btnSignUp = (Button)findViewById(R.id.btnSignUp);
        Button btnPatient = (Button)findViewById(R.id.btnSignIn);
        username = (EditText)findViewById(R.id.etUser);
        userpassword = (EditText)findViewById(R.id.etPass);
         globalVars = ((GlobalVars) getApplicationContext());
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, signUp.class));
            }
        });

        btnPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().equals(null)){
                    Toast.makeText(getApplicationContext(), "enter username", Toast.LENGTH_LONG).show();
                }else if(userpassword.getText().equals(null)){
                        Toast.makeText(getApplicationContext(), "enter password", Toast.LENGTH_LONG).show();
                }else {
                    RadioButton type_selected = (RadioButton) findViewById(type.getCheckedRadioButtonId());
                    if(type_selected.getText().toString().equals("Patient")){
                        user_type = "p";
                        Log.d("type select", "PATIENT SELECTED");
                    }else {
                        user_type = "d";
                        Log.d("type select", "DOCTOR SELECTED");

                    }
                    if(thread.getState() == Thread.State.NEW){
                        thread.start();

                    }else{
                        Log.d("thread starting", "thread startig another time");
                        thread.interrupt();
                        thread = new Thread(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                try {
                                    String response;
                                    response = serverRequest.log_in(user_type, username.getText().toString(), userpassword.getText().toString());
                                    Log.d("Sended request", response);
                                    if(response.equals("true")){
                                        globalVars.setUSERNAME(username.getText().toString());
                                        if(user_type.equals("p")){
                                            globalVars.setType("patient");
                                            startActivity(new Intent(MainActivity.this, startApp.class).putExtra("userID", username.getText().toString()));
                                            return;
                                        }else{
                                            globalVars.setType("doctor");
                                            startActivity(new Intent(MainActivity.this, PatientPick.class).putExtra("userID", username.getText().toString()));
                                            return;
                                        }

                                    }
                                    else{
                                        Log.d("Wrong username", "Wrong usernam");
                                        username.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                                        userpassword.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                                        Toast.makeText(getApplicationContext(), "wrong username/password ", Toast.LENGTH_LONG).show();
                                        Log.d("Wrong username", "Wrong usernam");
                                        return;
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    return;
                                }
                            }

                        });
                        thread.start();
                    }
                    Toast.makeText(getApplicationContext(), "checking user ", Toast.LENGTH_LONG).show();




                }

            }
        });
    }
    Thread thread = new Thread(new Runnable()
    {
        @Override
        public void run()
        {
            try {
                String response;
                 response = serverRequest.log_in(user_type, username.getText().toString(), userpassword.getText().toString());
                Log.d("Response recived", response);
                if(!(response.equals("false"))){
                    Log.d("sign in good", "sign in good");
                    globalVars.setUSERNAME(username.getText().toString());
                    if(user_type.equals("p")){
                        globalVars.setType("patient");
                        globalVars.setCategory(response);
                        startActivity(new Intent(MainActivity.this, startApp.class).putExtra("userID", username.getText().toString()));
                        return;
                    }else{
                        globalVars.setType("doctor");
                        startActivity(new Intent(MainActivity.this, PatientPick.class).putExtra("userID", username.getText().toString()));
                        return;
                    }

                }
               else{
                    Log.d("Wrong username", "Wrong usernam");
                    username.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                   userpassword.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                   Toast.makeText(getApplicationContext(), "wrong username/password ", Toast.LENGTH_LONG).show();
                    Log.d("Wrong username", "Wrong usernam");
                    return;
               }

            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }

     });


}
