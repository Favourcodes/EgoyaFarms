package com.example.egoyafarms;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    private Button CreateAccountButton;
    private EditText InputName, InputPhoneNumber, InputPassword;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        CreateAccountButton = findViewById(R.id.SubmitBtn);
        InputName = findViewById(R.id.register_username_input);
        InputPhoneNumber = findViewById(R.id.register_phone_number_input);
        InputPassword = findViewById(R.id.register_password_input);
        loadingBar = new ProgressDialog(this);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAccount();
            }
        });
    }
    private void CreateAccount() {
        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please write your Name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Please write your PhoneNumber", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please write your Password", Toast.LENGTH_SHORT).show();
        }else{
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking your credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            Validatephonenumber(name, phone, password);
        }

    }
    private void Validatephonenumber(final String name, final String phone, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child("Users").child(phone).exists()))
                {
                    HashMap<String, Object> userdatamap = new HashMap<>();
                    userdatamap.put("phone", phone);
                    userdatamap.put("password", password);
                    userdatamap.put("name", name);

                    RootRef.child("Users").child(phone).updateChildren(userdatamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignUp.this, "Congratulations, your account has been created.", Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(SignUp.this, MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                loadingBar.dismiss();
                                Toast.makeText(SignUp.this, "Network Error: Please try again after some seconds.... ", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
                else{
                    Toast.makeText(SignUp.this, "This" + phone + "alrerady exists",Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                    Toast.makeText(SignUp.this,"Please try again using another phone Number", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(SignUp.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
