package com.example.notemaker;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Delete
    void delete(Note note);

    @Query("Select * from note")
    LiveData<List<Note>> getAll();

    @Query("SELECT count(*) FROM note")
    int count();
}
