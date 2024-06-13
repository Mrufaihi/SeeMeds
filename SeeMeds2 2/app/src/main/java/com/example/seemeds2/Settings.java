package com.example.seemeds2;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.IOException;



public class Settings extends Navigaion_bar {

    TextView textUsername1,textUsername2,textEmail,textPhone;
    BottomNavigationView bottomNav;
    Button editProfile;
    ImageView imageView;
    DatabaseReference databaseReference;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupBottomNavigationView();


        textUsername1 = findViewById(R.id.fullName1);
        textUsername2 = findViewById(R.id.fullName2);
        textEmail = findViewById(R.id.email);
        textPhone = findViewById(R.id.phone);
        editProfile = findViewById(R.id.edit_profile);
        imageView = findViewById(R.id.profileImage);

        final String[] email1 = new String[1];

        SharedPreferences sharedPreferences = getSharedPreferences("my_shared_preferences", Context.MODE_PRIVATE);
        String value = sharedPreferences.getString("user", "default_value");

        if (value != null){
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(value);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Retrieve user data
                        String username = snapshot.child("Name").getValue(String.class);
                        String email = snapshot.child("Email").getValue(String.class);
                        String phone = snapshot.child("Phone Number").getValue(String.class);
                        String image = snapshot.child("Image").getValue(String.class);

                        textUsername1.setText(username);
                        textUsername2.setText(username);
                        textEmail.setText(email);
                        textPhone.setText(phone);
                        email1[0] = email;

                        if (image != null){

                            storageReference = FirebaseStorage.getInstance().getReference("images/"+image);
                            try {
                                File  localFile = File.createTempFile("tempfile", ".jpg");
                                storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                        imageView.setImageBitmap(bitmap);
                                    }
                                });
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d(TAG, "Error getting user data", error.toException());
                }
            });
        }

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), profile.class).putExtra("Email",email1[0]));
                overridePendingTransition(R.anim.to_right1, R.anim.to_right2);
                finish();
            }
        });


    }

}

