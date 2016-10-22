package com.example.user.navigatelifesaver;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by User on 8/27/2016.
 */
public class simptome extends android.app.Fragment{
    int howMuchTimePressed = 0 ;
    View myView;
    public simptome() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_simptome, container, false);

        Button btnMale = (Button)myView.findViewById(R.id.male);
        Button btnFemale = (Button)myView.findViewById(R.id.female);
        Button btnSub = (Button)myView.findViewById(R.id.btnSub);
        final ImageView male = (ImageView)myView.findViewById(R.id.ivMan);
        final ImageView female = (ImageView)myView.findViewById(R.id.ivWeman);

        final EditText age = (EditText)myView.findViewById(R.id.age);
        male.setVisibility(View.INVISIBLE);
        female.setVisibility(View.INVISIBLE);

        btnMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male.setVisibility(View.VISIBLE);
                female.setVisibility(View.INVISIBLE);
                howMuchTimePressed++;
            }
        });

        btnFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                female.setVisibility(View.VISIBLE);
                male.setVisibility(View.INVISIBLE);
                howMuchTimePressed++;
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String returnedAge = age.getText().toString();
                if(returnedAge.matches(""))
                {
                    Toast.makeText(getActivity(), "Please insert appropriate age",
                            Toast.LENGTH_LONG).show();
                }
                else if(howMuchTimePressed == 0)
                {
                    Toast.makeText(getActivity(), "Please choose gender",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    startActivity(new Intent(simptome.this.getActivity(), Category.class));
                }
            }
        });

        return myView;
    }
}
