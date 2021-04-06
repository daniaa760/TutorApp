package com.example.tutorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ChatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.search_tutors){
            Intent intent = new Intent (ChatsActivity.this, SearchTutorsActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.user_profile){
            Intent intent = new Intent(ChatsActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.logout){
            Intent intent = new Intent(ChatsActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.chat){

            Toast.makeText(ChatsActivity.this, "Error", Toast.LENGTH_LONG).show();
        }
        return true;
    }
}