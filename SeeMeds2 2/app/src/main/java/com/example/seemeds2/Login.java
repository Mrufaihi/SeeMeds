package com.example.seemeds2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    Button loginBtn;
    TextView forgotPass, signUp;
    TextInputEditText emailLogin,password;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUp = findViewById(R.id.signUpClick);
        forgotPass = findViewById(R.id.forgotPass);
        loginBtn = findViewById(R.id.signIn_btn);
        emailLogin = findViewById(R.id.email_editText);
        password = findViewById(R.id.password_editText);
        mAuth = FirebaseAuth.getInstance();



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = String.valueOf( emailLogin.getText()).trim();
                String pass = String.valueOf( password.getText()).trim();


                boolean validEmail = validateEmail(email);
                boolean validPass = validatePass(pass);


                if (validEmail == true && validPass == true){

                    mAuth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {


                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        SharedPreferences sharedPreferences = getSharedPreferences("my_shared_preferences", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("user", user.getUid());
                                        editor.apply();

                                        Toast.makeText(Login.this, "Successful login.",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Login.this, Camera.class);
//                                        intent.putExtra("user",user);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        Toast.makeText(Login.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }else {
                    Toast.makeText(Login.this, "Please enter your email and password.",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, ForgotPass.class);
                startActivity(intent);

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });
    }
    public boolean validateEmail(String email){

        if (email.isEmpty()){
            return false;
        }
        return true;
    }

    public boolean validatePass(String pass){

        if (pass.isEmpty()){
            return false;
        }
        return true;
    }
}