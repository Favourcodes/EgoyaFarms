package com.example.egoyafarms;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Sumbit extends AppCompatActivity {

    Button btnss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sumbit);

        btnss =findViewById(R.id.sum_btn);

        btnss.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                startActivity(new Intent(Sumbit.this, MainActivity.class));

            }
        });
    }
}
