package com.example.user.navigatelifesaver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Asus on 25/08/2016.
 */
public class ChatViewAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final  ArrayList<String > names;


    public ChatViewAdapter(Context context, int resource, ArrayList<String> names) {
        super(context, -1, names);
        this.context = context;
        this.names = names;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View user_list_item = inflater.inflate(R.layout.user_list_item, parent, false);
        TextView name = (TextView) user_list_item.findViewById(R.id.name);
        name.setText(names.get(position));




        return user_list_item;
    }
}
