package com.example.user.navigatelifesaver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;

public class Category extends AppCompatActivity {

    int what2Pass = 0 ;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        ImageButton btnArms = (ImageButton)findViewById(R.id.arms);
        ImageButton btnEyes = (ImageButton)findViewById(R.id.Eyes);
        ImageButton btnBeten = (ImageButton)findViewById(R.id.beten);
        ImageButton btnChest = (ImageButton)findViewById(R.id.chest);
        ImageButton btnHead = (ImageButton)findViewById(R.id.head);
        ImageButton btnLegs = (ImageButton)findViewById(R.id.legs);
        ImageButton btnNose = (ImageButton)findViewById(R.id.nose);
        ImageButton btnOther = (ImageButton)findViewById(R.id.other);


        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width), (int) (height));


        btnArms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadViewTask().execute();
                what2Pass = 1;
            }
        });

        btnEyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadViewTask().execute();
                what2Pass = 2;
            }
        });

        btnBeten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadViewTask().execute();
                what2Pass = 3;
            }
        });

        btnChest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadViewTask().execute();
                what2Pass = 4;
            }
        });

        btnHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadViewTask().execute();
                what2Pass = 5;
            }
        });

        btnLegs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadViewTask().execute();
                what2Pass = 6;
            }
        });

        btnNose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadViewTask().execute();
                what2Pass = 7 ;
            }
        });

        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadViewTask().execute();
                what2Pass = 8;
            }
        });
    }

    //To use the AsyncTask, it must be subclassed
    private class LoadViewTask extends AsyncTask<Void, Integer, Void> {
        //Before running code in separate thread
        @Override
        protected void onPreExecute() {
            //Create a new progress dialog
            progressDialog = new ProgressDialog(Category.this);
            //Set the progress dialog to display a horizontal progress bar
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            //Set the dialog title to 'Loading...'
            progressDialog.setTitle("Loading...");
            //Set the dialog message to 'Loading application View, please wait...'
            progressDialog.setMessage("Loading application, please wait...");
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
        @Override
        protected Void doInBackground(Void... params) {
            /* This is just a code that delays the thread execution 4 times,
             * during 850 milliseconds and updates the current progress. This
             * is where the code that is going to be executed on a background
             * thread must be placed.
             */
            try {
                //Get the current thread's token
                synchronized (this) {
                    //Initialize an integer (that will act as a counter) to zero
                    int counter = 0;
                    //While the counter is smaller than four
                    while (counter <= 4) {
                        //Wait 850 milliseconds
                        this.wait(850);
                        //Increment the counter
                        counter++;
                        //Set the current progress.
                        //This value is going to be passed to the onProgressUpdate() method.
                        publishProgress(counter * 25);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
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
            progressDialog.dismiss();
            //initialize the View
            if (what2Pass == 1)
            {
                startActivity(new Intent(Category.this, Chat.class).putExtra("buttonPressed","arms"));
            }
            else if(what2Pass == 2)
            {
                startActivity(new Intent(Category.this, Chat.class).putExtra("buttonPressed","eyes"));
            }

            else if(what2Pass == 3)
            {
                startActivity(new Intent(Category.this, Chat.class).putExtra("buttonPressed","beten"));
            }

            else if(what2Pass == 4)
            {
                startActivity(new Intent(Category.this, Chat.class).putExtra("buttonPressed","chest"));
            }

            else if(what2Pass == 5)
            {
                startActivity(new Intent(Category.this, headChoose.class).putExtra("buttonPressed","head"));
            }

            else if(what2Pass == 6)
            {
                startActivity(new Intent(Category.this, Chat.class).putExtra("buttonPressed","legs"));
            }

            else if (what2Pass == 7)
            {
                startActivity(new Intent(Category.this, Chat.class).putExtra("buttonPressed","nose"));
            }

            else
            {
                startActivity(new Intent(Category.this, Chat.class).putExtra("buttonPressed","other"));
            }
        }
    }
}
