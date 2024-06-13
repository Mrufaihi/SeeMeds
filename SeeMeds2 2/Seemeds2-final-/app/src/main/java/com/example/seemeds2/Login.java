package com.example.seemeds2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity {


    private  Button mRegisterNowBtn , mLoginBtn;
    private EditText mEmail , mPassword;
    private FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mRegisterNowBtn = findViewById(R.id.createAccountBtn);
        mLoginBtn = findViewById(R.id.loginBtn);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        fAuth = FirebaseAuth.getInstance();

        //when we click on register now button, we will go to the register page
        mRegisterNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        //when we click on login button, we will go to the  camera page
//        mLoginBtn.setOnClickListener(new View.OnClickListener() {
//            @Overrideses
//            public void onClick(View v) {
//                startActivity(new Intent(Login.this, Camera.class));
//            }
//        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Requiered");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Requierd");
                    return;
                }

                if(password.length() < 6 ){
                    mPassword.setError("Password must be more than 6 Characters");
                    return;
                }


                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //when authentication is successful go to camera
                            startActivity(new Intent(Login.this, Camera.class));
                            Toast.makeText(Login.this, "Successful login.", Toast.LENGTH_SHORT).show();


                        } else{
                            Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT ).show();
                        }

                    }
                });
            }
        });




    }
}