package com.example.tutorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChoiceActivity extends AppCompatActivity {
    private Button bnStudent, bnTutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

        bnStudent = (Button) findViewById(R.id.bnStudent);
        bnTutor = (Button) findViewById(R.id.bnTutor);

        bnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent intent = new Intent(this, MainActivity.class);
              //  startActivity(intent);
            }
        });

        bnTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Intent intent = new Intent(this,)
            }
        });
    }
}