/*
Edited by s3821179 sang yeob,Han
*/

package com.example.registeration_a1_s3821179;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.registeration_a1_s3821179.Adapters.NotesListAdapter;
import com.example.registeration_a1_s3821179.Database.RoomDB;
import com.example.registeration_a1_s3821179.Models.Notes;
import com.example.registeration_a1_s3821179.Models.NotesClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {


    //create object for recyclerview, notelistadpater
    RecyclerView recyclerView;
    NotesListAdapter notesListAdapter;
    //dummy list for storing notes
    List<Notes> notes = new ArrayList<>();
    //object for room db
    RoomDB database;
    //adding new note btn
    FloatingActionButton fab_add;
    //for search bar
    SearchView searchView_home;
    //for longclick
    Notes selectedNote;


    Handler handler;
    Runnable runnable;
    ImageView splashImg;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        splashImg = findViewById(R.id.splash_img);
//        splashImg.animate().alpha(0).setDuration(0);
//
//        handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                finish();
//            }
//        },4000);

        recyclerView = findViewById(R.id.recycler_home);
        fab_add = findViewById(R.id.fab_add);
        searchView_home = findViewById(R.id.searchView_home);

        //enable to access all the database
        database = RoomDB.getInstance(this);
        notes = database.mainDAO().getAllNotes();

        updateRecycler(notes);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View view) {
                //enter thing, and it will show on recyclerview
                Intent intent = new Intent(MainActivity.this,NotesTakerActivity.class);
                startActivityForResult(intent,101);
            }
        });

        searchView_home.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

    }

    private void filter(String newText) {
        //create new list
        List<Notes> filteredList = new ArrayList<>();
        for(Notes singleNote : notes ){
            if(singleNote.getTitle().toLowerCase().contains(newText.toLowerCase())
                    || singleNote.getNotes().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(singleNote);
            }
        }
        notesListAdapter.filterList(filteredList);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //check for request call(create note)
        if(requestCode==101){
            Notes new_notes = (Notes) data.getSerializableExtra("note");
            database.mainDAO().insertNotes(new_notes);
            notes.clear();
            notes.addAll(database.mainDAO().getAllNotes());
            notesListAdapter.notifyDataSetChanged();
        }
        //request(102) editing the note
        else if(requestCode==102){
            Notes new_notes = (Notes) data.getSerializableExtra("note");
            database.mainDAO().updateNotes(new_notes.getID(),new_notes.getTitle(),new_notes.getNotes());
            notes.clear();
            notes.addAll(database.mainDAO().getAllNotes());
            notesListAdapter.notifyDataSetChanged();
        }
    }

    private void updateRecycler(List<Notes> notes) {
        recyclerView.setHasFixedSize(true);
        //we will have different size of notes on the screen -> staggeredgridlayoutmanager
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        notesListAdapter = new NotesListAdapter(MainActivity.this,notes,notesClickListener);
        recyclerView.setAdapter(notesListAdapter);
    }

    private final NotesClickListener notesClickListener = new NotesClickListener() {
        @Override
        public void onClick(Notes notes) {
            Intent intent = new Intent(MainActivity.this,NotesTakerActivity.class);
            //passing item from homescreen to edit page
            intent.putExtra("old_note",notes);
            //passing different request code from privious
            startActivityForResult(intent,102);

        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {
            selectedNote = new Notes();
            selectedNote = notes;
            showPopup(cardView);
        }
    };

    //popup for long click listener
    private void showPopup(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(this,cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            //if it is pinned/unpiined
            case R.id.pin:
                if(selectedNote.isPin()){
                    database.mainDAO().pin(selectedNote.getID(),false);
                    Toast.makeText(MainActivity.this, "Note has been Unpinned.", Toast.LENGTH_SHORT).show();
                }
                else{
                    database.mainDAO().pin(selectedNote.getID(),true);
                    Toast.makeText(MainActivity.this, "Note has been Pinned.", Toast.LENGTH_SHORT).show();
                }

                notes.clear();
                notes.addAll(database.mainDAO().getAllNotes());
                notesListAdapter.notifyDataSetChanged();

                return true;

            //if it's deleted
            case R.id.delete:
                database.mainDAO().deleteNotes(selectedNote);
                notes.remove(selectedNote);
                notesListAdapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this, "Note has been deleted.", Toast.LENGTH_SHORT).show();

                return true;

            default:
                return false;
        }
    }
}

