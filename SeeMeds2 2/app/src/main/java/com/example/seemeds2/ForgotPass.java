package com.example.seemeds2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPass extends AppCompatActivity {

    Button btnReset, btnBack;
    EditText edtEmail;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        btnBack = findViewById(R.id.btnForgotPasswordBack);
        btnReset = findViewById(R.id.btnReset);
        edtEmail = findViewById(R.id.edtForgotPasswordEmail);
        progressBar = findViewById(R.id.forgetPasswordProgressbar);
        mAuth = FirebaseAuth.getInstance();


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = String.valueOf( edtEmail.getText()).trim();
                boolean validEmail = validateEmail(email);

                if(validEmail == true){

                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(ForgotPass.this, "Check Your Email",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent( ForgotPass.this,Login.class);
                                startActivity(intent);

                            }
                            else{
                                Toast.makeText(ForgotPass.this, "This Email is not Registered",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(ForgotPass.this, "Invalid Email",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent( ForgotPass.this,Login.class);
                startActivity(intent);

            }
        });
    }
    public boolean validateEmail(String email){

        if(email.isEmpty()){
            return false;
        }
        return true;
    }
}
