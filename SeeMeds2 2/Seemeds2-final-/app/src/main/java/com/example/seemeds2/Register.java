package com.example.seemeds2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText mFulLName, mNumberPhone, mEmail, mPassword;
    Button mResisterBtn , mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFulLName = findViewById(R.id.fullName);
        mNumberPhone = findViewById(R.id.numberPhone);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mResisterBtn = findViewById(R.id.nextBtn);
        mLoginBtn = findViewById(R.id.loginBtn);


        fAuth = FirebaseAuth.getInstance();
        // progressBar =

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));

            }
        });

        mResisterBtn.setOnClickListener(new View.OnClickListener() {
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

                // progressBar

                // Register user in firebase
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        } else{
                            Log.e("Register", "Registration error: ", task.getException());
                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT ).show();
                        }
                    }
                });
            }
        });



    }
}
