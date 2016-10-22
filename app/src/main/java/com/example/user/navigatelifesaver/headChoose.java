package com.example.user.navigatelifesaver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class headChoose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head_choose);

        Button btnChoos = (Button)findViewById(R.id.button10);

        btnChoos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(headChoose.this, Chat.class).putExtra("buttonPressed", "Tinnitus"));
            }
        });
    }
}
