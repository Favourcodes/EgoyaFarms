package com.example.egoyafarms;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ForgotPassword extends AppCompatActivity {

    TextView forgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        forgot =findViewById(R.id.button2);

        forgot.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

                startActivity(new Intent(ForgotPassword.this, Sumbit.class));

            }
        });
    }
}
