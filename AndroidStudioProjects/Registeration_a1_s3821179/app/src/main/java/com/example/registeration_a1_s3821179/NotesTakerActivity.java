package com.example.registeration_a1_s3821179;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.registeration_a1_s3821179.Models.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesTakerActivity extends AppCompatActivity {

    EditText editText_title, editText_notes;
    ImageView imageView_save;
    Notes notes;
    boolean isOldnote = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);

        imageView_save = findViewById(R.id.imageView_save);
        editText_title = findViewById(R.id.editText_title);
        editText_notes = findViewById(R.id.editText_notes);

        notes = new Notes();

        //casting issue
        try {
            notes = (Notes) getIntent().getSerializableExtra("old_note");
            editText_title.setText(notes.getTitle());
            editText_notes.setText(notes.getNotes());
            isOldnote = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editText_title.getText().toString();
                String description = editText_notes.getText().toString();

                if(title.isEmpty()){
                    Toast.makeText(NotesTakerActivity.this, "Please add Title for your note.",Toast.LENGTH_SHORT).show();
                    return ;
                }


                if(description.isEmpty()){
                    Toast.makeText(NotesTakerActivity.this, "Please add description for your note.",Toast.LENGTH_SHORT).show();
                            return ;
                }

                //create date
                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MM yyyy HH:mm a");
                Date date = new Date();

                if(!isOldnote){
                    //create and set object for notes if it is not old note
                    notes = new Notes();
                }

                notes.setTitle(title);
                notes.setNotes(description);
                notes.setCreatedDate(formatter.format(date));

                // to send these data to mainActivity, we are using Intent
                Intent intent = new Intent();
                intent.putExtra("note",notes);
                setResult(Activity.RESULT_OK,intent);
                finish();


            }
        });
    }


}