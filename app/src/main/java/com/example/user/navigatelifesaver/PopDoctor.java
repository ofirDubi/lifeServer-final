package com.example.user.navigatelifesaver;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by ofir on 10/24/2016.
 */
public class PopDoctor extends Activity{

    String Type;
    ListView type_list;
    ChatViewAdapter adapter;
    ArrayList<String> type_names = new ArrayList<String>();
    Button btnCamera;
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
        type_list = (ListView)findViewById(R.id.typeList);

        adapter = new ChatViewAdapter(this, R.layout.user_list_item, type_names);
        type_list.setAdapter(adapter);

        adapter.add("general");
        adapter.add("head");
        adapter.add("skin");
        adapter.add("otolaryngologist");
        adapter.add("Internal medicine");
        adapter.add("pediatrician");

        type_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("DOCTOR TYPE SELECTED: ", type_names.get(position));
                Intent returnIntent = new Intent();
                returnIntent.putExtra("DoctorType", type_names.get(position));
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });



    }

}
