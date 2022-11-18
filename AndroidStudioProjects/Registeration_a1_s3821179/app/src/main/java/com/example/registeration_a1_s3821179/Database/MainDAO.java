//data access objects (dao class)
package com.example.registeration_a1_s3821179.Database;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.registeration_a1_s3821179.Models.Notes;

import java.util.List;

@Dao
public interface MainDAO {

    @Insert(onConflict = REPLACE)
    void insertNotes(Notes notes);

    //get all the data in desending orders
    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Notes> getAllNotes();

    //update notes
    @Query("UPDATE notes SET title = :title, notes = :notes WHERE ID = :id")
    void updateNotes(int id, String title, String notes);

    @Delete
    void deleteNotes(Notes notes);

}
