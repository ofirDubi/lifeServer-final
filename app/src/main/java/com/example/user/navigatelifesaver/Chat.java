package com.example.user.navigatelifesaver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Chat extends AppCompatActivity {
    String USERNAME;
    String diagnosis = "";
    String Category = "head";
    int num = 0 ;
    int checkButtonOne = 0 ;
    int checkButtonTwo = 0 ;
    final ServerRequest serverRequest = new ServerRequest();
    GlobalVars globalVars;
    boolean is_sent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
          //  Category = extras.getString("buttonPressed");
        }
        USERNAME = ((GlobalVars) this.getApplication()).getUSERNAME();
        globalVars = ((GlobalVars) getApplicationContext());

        final LinearLayout firstLin = (LinearLayout)findViewById(R.id.layout2);
        final LinearLayout secLin = (LinearLayout)findViewById(R.id.layout3);
        final LinearLayout thirdLay = (LinearLayout)findViewById(R.id.layout4);
        final LinearLayout forthLay = (LinearLayout)findViewById(R.id.layout5);
        is_sent = false;
        final TextView firstTV = (TextView)findViewById(R.id.firstTV);
        final TextView secondTV = (TextView)findViewById(R.id.secondTV);
        final TextView thirdTV = (TextView)findViewById(R.id.thirdTV);
        final TextView forthTV = (TextView)findViewById(R.id.forthTV);
        final TextView fifthTV = (TextView)findViewById(R.id.fifthTV);


        secLin.setVisibility(View.INVISIBLE);
        firstLin.setVisibility(View.INVISIBLE);
        thirdLay.setVisibility(View.INVISIBLE);
        forthLay.setVisibility(View.INVISIBLE);


        final Button btnFirst = (Button)findViewById(R.id.button);
        final Button btnSec = (Button)findViewById(R.id.button2);

        Log.d("Category", Category);

        try {
            synchronized(this){
                wait(1000);
            }
        }
        catch(InterruptedException ex){
        }

        if(Category.equals("head"))
        {
            firstLin.setVisibility(View.VISIBLE);
            btnFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((checkButtonOne == 3 && checkButtonTwo == 1) || (checkButtonOne == 4 && checkButtonOne == 0) || !is_sent)
                    {
                        //send the message to the server
                        Category = "general";
                        diagnosis = "tinnitus";
                        Log.d("USERNAME", USERNAME);
                        globalVars.setIsDiagnosed(true);
                        is_sent = true;
                        thread.start();

                    }
                    if (checkButtonOne == 0) {
                        btnFirst.setText("Yes, I am over 55");
                        btnSec.setText("No, I am Not over 55");

                        try {
                            synchronized (this) {
                                wait(400);
                            }
                        } catch (InterruptedException ex) {
                        }
                        secLin.setVisibility(View.VISIBLE);
                        checkButtonOne++;
                    } else if (checkButtonOne == 1) {
                        btnFirst.setText("Yes, I was exposed to very loud noises");
                        btnSec.setText("No, I don't remember I was exposed to loud noise");

                        try {
                            synchronized (this) {
                                wait(400);
                            }
                        } catch (InterruptedException ex) {
                        }
                        thirdLay.setVisibility(View.VISIBLE);
                        checkButtonOne++;
                    } else if (checkButtonOne == 2) {
                        btnFirst.setText("Go out to see results");
                        btnSec.setVisibility(View.INVISIBLE);

                        try {
                            synchronized (this) {
                                wait(400);
                            }
                        } catch (InterruptedException ex) {
                        }
                        forthLay.setVisibility(View.VISIBLE);
                        checkButtonOne++;
                    }
                }
            });

            btnSec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkButtonTwo == 0)
                    {
                        btnFirst.setText("Yes, I am over 55");
                        btnSec.setText("No, I am Not over 55");

                        try {
                            synchronized(this){
                                wait(400);
                            }
                        }
                        catch(InterruptedException ex){
                        }
                        thirdLay.setVisibility(View.VISIBLE);
                        checkButtonTwo++;
                    }


                    else if(checkButtonTwo == 1)
                    {
                        btnFirst.setText("Yes, I am over 55");
                        btnSec.setText("No, I am Not over 55");
                        checkButtonTwo++;
                    }
                }
            });
        }

        else if(Category.equals("chest"))
        {
            btnFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkButtonOne == 0)
                    {
                        btnFirst.setText("Yes, I am over 55");
                        btnSec.setText("No, I am Not over 55");

                        firstTV.setText("We are sorry to hear that you are suffering from chest pain.\n" +
                                "Appearance of chest pain always requires physician assessment. " +
                                "If the pain is severe, you should seek immediate medical response.");

                        try {
                            synchronized(this){
                                wait(400);
                            }
                        }
                        catch(InterruptedException ex){
                        }
                        secLin.setVisibility(View.VISIBLE);
                        checkButtonOne++;
                    }


                    else if(checkButtonOne == 1)
                    {
                        btnFirst.setText("Yes, I was exposed to very loud noises");
                        btnSec.setText("No, I don't remember I was exposed to loud noise");

                        try {
                            synchronized(this){
                                wait(400);
                            }
                        }
                        catch(InterruptedException ex){
                        }
                        thirdLay.setVisibility(View.VISIBLE);
                        checkButtonOne++;
                    }

                    else if(checkButtonOne == 2)
                    {
                        btnFirst.setText("Go out to see results");
                        btnSec.setVisibility(View.INVISIBLE);

                        try {
                            synchronized(this){
                                wait(400);
                            }
                        }
                        catch(InterruptedException ex){
                        }
                        forthLay.setVisibility(View.VISIBLE);
                        checkButtonOne++;
                    }
                }
            });

            btnSec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkButtonTwo == 0)
                    {
                        btnFirst.setText("Yes, I am over 55");
                        btnSec.setText("No, I am Not over 55");

                        try {
                            synchronized(this){
                                wait(400);
                            }
                        }
                        catch(InterruptedException ex){
                        }
                        thirdLay.setVisibility(View.VISIBLE);
                        checkButtonTwo++;
                    }


                    else if(checkButtonTwo == 1)
                    {
                        btnFirst.setText("Yes, I am over 55");
                        btnSec.setText("No, I am Not over 55");
                        checkButtonTwo++;
                    }
                }
            });
        }

        else if(Category.equals("Other"))
        {
            firstTV.setText("Some symptoms of dry skin include cracks, nicks," +
                    " redness, skin feel tight and rough texture. In most cases, a " +
                    "simple skin dryness, you can go undiagnosed for yourself. Let's start by " +
                    "looking at your skin care routine.\n" +
                    "Do you suffer from itching associated with many skin dryness?");

            firstLin.setVisibility(View.VISIBLE);
            secLin.setVisibility(View.INVISIBLE);
            btnFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkButtonOne == 0)
                    {
                        firstLin.setVisibility(View.VISIBLE);

                        btnFirst.setText("Yes, my skin dry and itchy");
                        btnSec.setText("No, my skin is just dry");

                        try {
                            synchronized(this){
                                wait(800);
                            }
                        }
                        catch(InterruptedException ex){
                        }
                        startActivity(new Intent(Chat.this , Pop.class));
                        checkButtonOne++;
                    }

                    else if(checkButtonOne == 1)
                    {
                        btnFirst.setText("Yes, one or more are currect");
                        btnSec.setText("No, I dont do anything from that list");

                        thirdTV.setText("First try using moisturizer. \n" +
                                "If there are persistent itching and tingling, you may be suffering from eczema.\n" +
                                "The term eczema refers to a number of conditions, including changes in the pattern of the skin. \n" +
                                " Eczema appears first episode of itching, redness and small bumps or blisters. When it develops over time (chronic eczema)\n" +
                                " skin becomes thick, scaly, rough, dry and changes color \n"  +
                                "\nDo you often, \n" +
                                "• Editor and long hot showers or \n" +
                                "• scrubbing your skin soaps without moisture?");

                        try {
                            synchronized(this){
                                wait(400);
                            }
                        }
                        catch(InterruptedException ex){
                        }
                        secLin.setVisibility(View.VISIBLE);
                        checkButtonOne++;
                    }

                    else if(checkButtonOne == 2)
                    {
                        btnFirst.setText("Yes, I wash my hands often");
                        btnSec.setText("No, I did not wash my hands often");

                        forthTV.setText("It is important to avoid hot showers or very long. \n" +
                                "Also, there really is a need to scrub the skin or soap and lather each centimeter of the body each day. \n" +
                                "Scrubbing and beating the rest of the arms, legs and body will help increase skin dryness and irritation. \n" +
                                "It's okay to be a little more thorough and intensive cleaning under the arms, in the groin and feet. \n" +
                                "During the immersion bath, use a moisture-enriched detergent.\n" +
                                "Are you washing the hands frequently?");

                        try {
                            synchronized(this){
                                wait(400);
                            }
                        }
                        catch(InterruptedException ex){
                        }

                        thirdLay.setVisibility(View.VISIBLE);
                        checkButtonOne++;
                    }

                    else if (checkButtonOne == 3)
                    {
                        btnFirst.setText("Go to see results");
                        btnSec.setVisibility(View.INVISIBLE);

                        try {
                            synchronized(this){
                                wait(400);
                            }
                        }
                        catch(InterruptedException ex){
                        }
                        forthLay.setVisibility(View.VISIBLE);
                        checkButtonOne++;
                    }
                }
            });
        }
    }
    Thread thread = new Thread(new Runnable()
    {
        @Override
        public void run()
        {
            try {
                Log.d("adding diagnosis", USERNAME);

                serverRequest.add_diagnosis(USERNAME, Category, diagnosis);
                startActivity(new Intent(Chat.this, ChatView.class));
                Log.d("diagnosis added", USERNAME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    });

}
