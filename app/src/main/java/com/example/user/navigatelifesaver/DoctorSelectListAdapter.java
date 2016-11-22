package com.example.user.navigatelifesaver;

/**
 * Created by ofir on 25/10/2016.
 */

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DoctorSelectListAdapter extends ArrayAdapter<String>{

private final Activity context;
private final String[] ocupation;
private boolean kaki = false;
private final int[] imageId;
 boolean[] is_added;

    public DoctorSelectListAdapter(Activity context,
        String[] ocupation, int[] imageId) {
        super(context, R.layout.list_single, ocupation);
        this.context = context;
        this.ocupation = ocupation;
        this.imageId = imageId;
        this.is_added = new boolean[ocupation.length-1];

        for(int i=0; i<is_added.length; i++){
            is_added[i] = false;
        }
        Log.d("DoctorSelectListAdapter", "CLASS INITIADED");
        }
@Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        rowView.setBackgroundColor(Color.WHITE);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(ocupation[position]);
        Log.d("INITIATE LIST VIEW", ocupation[position]);
        imageView.setImageResource(imageId[position]);

        if(!ocupation[position].equals("Done") && !is_added[position]) {
            imageView.setVisibility(View.INVISIBLE);
            rowView.getBackground().setColorFilter(Color.parseColor("#99ff99"), PorterDuff.Mode.LIGHTEN);
            Log.d("MADE INVISIBLE", ocupation[position]);
        }
        return rowView;
    }
}
