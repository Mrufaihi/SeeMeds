package com.example.seemeds2;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {


    TextInputEditText fullName,phoneNum,emailSignUp,password;
    Button signUpBtn , signInBtn;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullName = findViewById(R.id.fullName);
        phoneNum = findViewById(R.id.numberPhone);
        emailSignUp = findViewById(R.id.email);
        password = findViewById(R.id.password);
        signUpBtn = findViewById(R.id.signUp_Button);
        signInBtn = findViewById(R.id.signIn);
        mAuth = FirebaseAuth.getInstance();


        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();

            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = String.valueOf(fullName.getText()).trim();
                String num = String.valueOf(phoneNum.getText()).trim();
                String email = String.valueOf( emailSignUp.getText()).trim();
                String pass = String.valueOf( password.getText()).trim();

                boolean validName = validateName(name);
                boolean validNum = validateNum(num);
                boolean validEmail = validateEmail(email);




                mAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if (task.isSuccessful()) {

                                    FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                                    HashMap<String, String> user = new HashMap<>();
                                    user.put("Name", name);
                                    user.put("Email", email);
                                    user.put("Phone Number", num);


                                    mDatabase.child(user1.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            Toast.makeText(Register.this, "Account created.",
                                                    Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Register.this, Login.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    task.getException().printStackTrace();

                                }
                            }
                        });


            }
        });
    }

    public boolean validateName(String name){

        if (name.isEmpty()){
            fullName.setError("Please enter a name");
            return false;
        }
        return true;
    }

    public boolean validateNum(String num){

        if (!Patterns.PHONE.matcher(num).matches()){
            phoneNum.setError("Please enter a valid mobile number");
            return false;
        }
        return true;
    }

    public boolean validateEmail(String email){

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailSignUp.setError("Please enter a valid email");
            return false;
        }
        return true;
    }



}