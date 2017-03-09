package com.example.user.navigatelifesaver;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatView extends AppCompatActivity {
    ListView chat_list;
    ServerRequest sereverRequest = new ServerRequest();
    String USERNAME;
    String list_unfilltered ="";
    boolean load_complite = false;
    TextView text[];
    String Type;
    ChatViewAdapter adapter;
      ArrayList<String> chat_names = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        thread.start();
        USERNAME = GlobalVars.getUSERNAME();
        Type = GlobalVars.getType();
        while(!load_complite){

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_view);
        chat_list = (ListView)findViewById(R.id.usersList);
        adapter = new ChatViewAdapter(this, R.layout.user_list_item, chat_names);
        chat_list.setAdapter(adapter);


        String temp_name="";
        for(int i=0; i<list_unfilltered.length(); i++){
            if(list_unfilltered.charAt(i) != ','){
                temp_name +=list_unfilltered.charAt(i);
            }else{
                if(i!=0){


                   adapter.add(temp_name);

                }

                temp_name ="";
            }
        }


        Log.d("Chat_list last", Integer.toString(chat_list.getLastVisiblePosition()));
        Log.d("Names From Server" ,chat_names.toString());

        chat_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d("StartedActivity", "windowPresed");
                Intent intent = new Intent(ChatView.this, PatientChat.class);

                intent.putExtra("receiverID",chat_names.get(position));
                startActivity(intent);
            }
        });




    }

    Thread thread = new Thread(new Runnable()
    {
        @Override
        public void run()
        {
            try {
                if(Type.equals("doctor")){
                    list_unfilltered =  sereverRequest.get_doctor_chat(USERNAME);

                }else{
                    list_unfilltered =  sereverRequest.get_patient_chat(USERNAME);

                }
                load_complite = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });
}
