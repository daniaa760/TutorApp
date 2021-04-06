package com.example.tutorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.Menu;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.tutorapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SearchTutorsActivity extends AppCompatActivity
{
    RecyclerView recview;
    myadapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tutors);
        setTitle("");

        recview=(RecyclerView)findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Tutor> options =
                new FirebaseRecyclerOptions.Builder<Tutor>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Tutors"), Tutor.class)
                        .build();

        adapter=new myadapter(options);
        recview.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.search_tutors){
            Toast.makeText(SearchTutorsActivity.this, "Error", Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId() == R.id.user_profile){
            Intent intent = new Intent(SearchTutorsActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.logout){
            Intent intent = new Intent(SearchTutorsActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.chat){
            Intent intent = new Intent (SearchTutorsActivity.this, ChatsActivity.class);
            startActivity(intent);
        }
        return true;
    }


/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        MenuItem item=menu.findItem(R.id.search);

        SearchView searchView=(SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                processsearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                processsearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }*/

    private void processsearch(String s)
    {
        FirebaseRecyclerOptions<Tutor> options =
                new FirebaseRecyclerOptions.Builder<Tutor>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Tutors").orderByChild("course").startAt(s).endAt(s+"\uf8ff"), Tutor.class)
                        .build();

        adapter=new myadapter(options);
        adapter.startListening();
        recview.setAdapter(adapter);

    }
}