package com.example.seemeds2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Reminder extends Navigaion_bar {
    private CalendarView calendarView;
    private EditText editText;
    private TextView tvDates;
    private String stringDateSelected;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        //call navigation method to manage navigation
        setupBottomNavigationView();


        calendarView = findViewById(R.id.calendarView);
        editText = findViewById(R.id.editText);
        tvDates = findViewById(R.id.tvDates);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                stringDateSelected = i + "-" + (i1+1) + "-" + i2;
                calendarClicked();
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference("Calendar");
        displayDates();
    }

    private void calendarClicked(){
        databaseReference.child(stringDateSelected).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null){
                    editText.setText(snapshot.getValue().toString());
                }else {
                    editText.setText("null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void buttonSaveEvent(View view){
        databaseReference.child(stringDateSelected).setValue(editText.getText().toString());
    }


    public void buttonDeleteEvent(View view){
        if (stringDateSelected != null) {
            databaseReference.child(stringDateSelected).removeValue();
        } else {
            Toast.makeText(this, "No date selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayDates() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                StringBuilder dates = new StringBuilder();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    dates.append(snapshot.getKey()).append(": ").append(snapshot.getValue().toString()).append("\n");
                }
                tvDates.setText(dates.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}