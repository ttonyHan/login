//data access objects
package com.example.registeration_a1_s3821179.Database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

import com.example.registeration_a1_s3821179.Models.Notes;

@Dao
public interface MainDAO {

    //annotation that this is inser operation
    @Insert(onConflict = REPLACE)
    void insertNotes(Notes notes);

    //get all the notes in descending orders
    @Query("SELECT * FROM notesdb ORDER BY id DESC")
    List<Notes> getAllNotes();

    //update notes
    @Query("UPDATE notesdb SET title = :title, notes = :notes WHERE ID = :id")
    void updateNotes(int id, String title, String notes);

    @Delete
    void deleteNotes(Notes notes);

    @Query("UPDATE notesdb SET pin = :pin WHERE ID= :id")
    void pin(int id,boolean pin);

}
