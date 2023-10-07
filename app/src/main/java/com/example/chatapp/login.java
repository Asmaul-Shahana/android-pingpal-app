package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    TextView logsignup;
    Button button;
    EditText email, password;
    FirebaseAuth auth;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    android.app.ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logbutton);
        email = findViewById(R.id.editTexLogEmail);
        password = findViewById(R.id.editTextLogPassword);
        logsignup = findViewById(R.id.logsignup);

        logsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this,registration.class);
                startActivity(intent);
                finish();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString();
                String Password = password.getText().toString();

//                checking for correct email and password format
                if((TextUtils.isEmpty(Email))){
                    progressDialog.dismiss();
                    Toast.makeText(login.this, "Enter Your Email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(Password))
                {
                    progressDialog.dismiss();
                    Toast.makeText(login.this, "Enter Your Password", Toast.LENGTH_SHORT).show();
                } else if(!Email.matches(emailPattern))
                {
                    progressDialog.dismiss();
                    email.setError("Enter Proper Email Address!");
                }  else if(password.length()<6)
                {
                    progressDialog.dismiss();
                    password.setError("More than six characters!");
                    Toast.makeText(login.this, "Password should be longer than six characters", Toast.LENGTH_SHORT).show();
                } else{
                    auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressDialog.show();
                                try{
                                    // if login successful, go to mainactivity
                                    Intent intent = new Intent(login.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                catch(Exception e){
                                    Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else{
                                Toast.makeText(login.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}