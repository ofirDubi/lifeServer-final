package com.example.user.navigatelifesaver;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class Chat extends AppCompatActivity {
    String USERNAME;
    String diagnosis = "";
    String Category = "ENT";
    int checkYes = 0 ;
    int checkNo = 0 ;
    int phase = 0;
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


        final Button yes_button = (Button)findViewById(R.id.yes_button);
        final Button no_button = (Button)findViewById(R.id.no_button);

         Log.d("Category", Category);

        try {
            synchronized(this){
                wait(1000);
            }
        }
        catch(InterruptedException ex){
        }

        if(Category.equals("ENT"))
        {
            firstLin.setVisibility(View.VISIBLE);
            assert yes_button != null;
            yes_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (phase == 3 && !is_sent)
                    {
                        //send the message to the server
                        Category = "ENT";
                        diagnosis = "tinnitus" + diagnosis;
//                        Log.d("USERNAME", USERNAME);
                        globalVars.setIsDiagnosed(true);
                        globalVars.setCategory(Category);
                        is_sent = true;
                        thread.start();

                    }
                    if (phase == 0) {
                        yes_button.setText("Yes, I am over 55");
                        no_button.setText("No, I am Not over 55");

                        try {
                            synchronized (this) {
                                wait(400);
                            }
                        } catch (InterruptedException ex) {
                        }
                        secLin.setVisibility(View.VISIBLE);
                        diagnosis += ", had significant injury";
                        checkYes++;
                        phase++;
                    } else if (phase == 1) {
                        yes_button.setText("Yes, I was exposed to very loud noises");
                        no_button.setText("No, I don't remember I was exposed to loud noise");

                        try {
                            synchronized (this) {
                                wait(400);
                            }
                        } catch (InterruptedException ex) {
                        }
                        thirdLay.setVisibility(View.VISIBLE);
                        diagnosis += ", patient is over 55 years old";
                        checkYes++;
                        phase++;
                    } else if (phase == 2) {
                        yes_button.setText("Go out to see results");
                        no_button.setVisibility(View.INVISIBLE);

                        try {
                            synchronized (this) {
                                wait(400);
                            }
                        } catch (InterruptedException ex) {
                        }
                        forthLay.setVisibility(View.VISIBLE);
                        diagnosis += "patient was exposed to very loud noises";
                        checkYes++;
                        phase++;
                    }
                }
            });

            no_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(phase == 0)
                    {
                        yes_button.setText("Yes, I am over 55");
                        no_button.setText("No, I am Not over 55");

                        try {
                            synchronized(this){
                                wait(400);
                            }
                        }
                        catch(InterruptedException ex){
                        }
                        thirdLay.setVisibility(View.VISIBLE);
                        diagnosis+= "patient didn't had any significant injury";
                        checkNo++;
                        phase++;
                    }


                    else if(checkNo == 1)
                    {
                        yes_button.setText("Yes, I am over 55");
                        no_button.setText("No, I am Not over 55");
                        checkNo++;
                        phase++;
                    }
                }
            });
        }

        else if(Category.equals("chest"))
        {
            yes_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(phase == 0)
                    {
                        no_button.setText("Yes, I am over 55");
                        yes_button.setText("No, I am Not over 55");

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
                        checkYes++;
                        phase++;
                        diagnosis+= " patient had significant injury.";
                    }


                    else if(phase == 1)
                    {
                        yes_button.setText("Yes, I was exposed to very loud noises");
                        no_button.setText("No, I don't remember I was exposed to loud noise");

                        try {
                            synchronized(this){
                                wait(400);
                            }
                        }
                        catch(InterruptedException ex){
                        }
                        thirdLay.setVisibility(View.VISIBLE);
                        checkYes++;
                        phase++;
                        diagnosis+= " patient had this symptoms before.";
                    }

                    else if(checkYes == 2)
                    {
                        yes_button.setText("Go out to see results");
                        no_button.setVisibility(View.INVISIBLE);

                        try {
                            synchronized(this){
                                wait(400);
                            }
                        }
                        catch(InterruptedException ex){
                        }
                        forthLay.setVisibility(View.VISIBLE);
                        checkYes++;
                    }
                }
            });

            yes_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkNo == 0)
                    {
                        yes_button.setText("Yes, I am over 55");
                        no_button.setText("No, I am Not over 55");

                        try {
                            synchronized(this){
                                wait(400);
                            }
                        }
                        catch(InterruptedException ex){
                        }
                        thirdLay.setVisibility(View.VISIBLE);
                        checkNo++;
                    }


                    else if(checkNo == 1)
                    {
                        yes_button.setText("Yes, I am over 55");
                        no_button.setText("No, I am Not over 55");
                        checkNo++;
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
            yes_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkYes == 0)
                    {
                        firstLin.setVisibility(View.VISIBLE);

                        yes_button.setText("Yes, my skin dry and itchy");
                        no_button.setText("No, my skin is just dry");

                        try {
                            synchronized(this){
                                wait(800);
                            }
                        }
                        catch(InterruptedException ex){
                        }
                        startActivity(new Intent(Chat.this , Pop.class));
                        checkYes++;
                    }

                    else if(checkYes == 1)
                    {
                        yes_button.setText("Yes, one or more are currect");
                        no_button.setText("No, I dont do anything from that list");

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
                        checkYes++;
                    }

                    else if(checkYes == 2)
                    {
                        yes_button.setText("Yes, I wash my hands often");
                        no_button.setText("No, I did not wash my hands often");

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
                        checkYes++;
                    }

                    else if (checkYes == 3)
                    {
                        yes_button.setText("Go to see results");
                        no_button.setVisibility(View.INVISIBLE);

                        try {
                            synchronized(this){
                                wait(400);
                            }
                        }
                        catch(InterruptedException ex){
                        }
                        forthLay.setVisibility(View.VISIBLE);
                        checkYes++;
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
                Location location = new Location(getLastKnownLocation());
               serverRequest.add_diagnosis(USERNAME, Category, diagnosis,  String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                Log.d("diagnosis added", USERNAME);
                startActivity(new Intent(Chat.this, MapsActivity.class));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    });
    // Private method return last known location
    private Location getLastKnownLocation() {
        LocationManager mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

}
