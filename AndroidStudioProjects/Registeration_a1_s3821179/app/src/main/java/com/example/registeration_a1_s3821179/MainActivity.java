//s3821179 sang yeob,han

package com.example.registeration_a1_s3821179;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.registeration_a1_s3821179.Adapters.NotesListAdapter;
import com.example.registeration_a1_s3821179.Database.RoomDB;
import com.example.registeration_a1_s3821179.Models.Notes;
import com.example.registeration_a1_s3821179.Models.NotesClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //create object for recyclerview, notelistadpater
    RecyclerView recyclerView;
    NotesListAdapter notesListAdapter;
    //dummy list for storing notes
    List<Notes> notes = new ArrayList<>();
    //object for room db
    RoomDB database;
    FloatingActionButton fab_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_home);
        fab_add = findViewById(R.id.fab_add);

        //enable to access all the database
        database = RoomDB.getInstance(this);
        notes = database.mainDAO().getAllNotes();

        updateRecycler(notes);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //enter thing, and it will show on recyclerview
            }
        });

    }

    private void updateRecycler(List<Notes> notes) {
        recyclerView.setHasFixedSize(true);
        //we will have different size of notes on the screen -> staggeredgridlayoutmanager
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL));
        notesListAdapter = new NotesListAdapter(MainActivity.this,notes,notesClickListener);
        recyclerView.setAdapter(notesListAdapter);
    }

    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClick(Notes notes) {

        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {

        }
    };


}

