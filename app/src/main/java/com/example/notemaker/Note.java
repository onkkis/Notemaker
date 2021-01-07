package com.example.notemaker;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Note {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "note_text")
    public String noteText;

    @ColumnInfo(name = "note_title")
    public String noteTitle;

    public Note(String noteTitle, String noteText){
        this.noteTitle = noteTitle;
        this.noteText = noteText;
    }

    public String getTitle(){ return this.noteTitle; }

    public String getText(){ return this.noteText; }
}
