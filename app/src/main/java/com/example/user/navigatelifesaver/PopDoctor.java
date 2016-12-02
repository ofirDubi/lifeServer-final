package com.example.user.navigatelifesaver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by ofir on 10/24/2016.
 */
public class PopDoctor extends Activity{

    String Type;
    ListView type_list;
    String[] ocupations = {
            "General",
            "ENT",
            "Dermatology",
            "General surgeon",
            "Internal medicine",
            "Pediatrician",
            "Done"
    }   ;
    int[] imageId;
    ArrayList<String> selected_types = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popdoctortype);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        Log.d("GOT HERE1", "GOT HERE1");

        getWindow().setLayout((int) (width * .6), (int) (height * .4));
        int[] imageId = new int[ocupations.length];
        for(int i=0; i<imageId.length; i++){
            if(i !=imageId.length-1){
                imageId[i] = R.drawable.check_icon;
            }else{
                imageId[i] = R.drawable.go;
            }
        }

        final DoctorSelectListAdapter adapter = new
                DoctorSelectListAdapter(PopDoctor.this, ocupations, imageId);
        type_list=(ListView)findViewById(R.id.typeList);
        type_list.setAdapter(adapter);

        type_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                if(ocupations[position].equals("Done")){

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("DoctorTypes", selected_types);
                    returnIntent.putExtra("Type", selected_types.get(0));
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }else{

                    ImageView imageView = (ImageView) view.findViewById(R.id.img);
                    if(adapter.is_added[position] == true){
                        imageView.setVisibility(View.INVISIBLE);
                        adapter.is_added[position] = false;
                        selected_types.remove(ocupations[position]);
                        Log.d("REMOVED TYPE", ocupations[position]);


                    }else if(adapter.is_added[position] == false){
                        imageView.setVisibility(View.VISIBLE);
                        adapter.is_added[position] = true;
                        selected_types.add(ocupations[position]);

                        Log.d("ADDED TYPE", ocupations[position]);
                    }


                }

            }
        });



    }

}
