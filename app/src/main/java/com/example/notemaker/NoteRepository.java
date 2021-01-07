package com.example.notemaker;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> notes;

    NoteRepository(Application application) {
        NoteDatabase db = NoteDatabase.getDatabase(application);
        noteDao = db.noteDao();
        notes = noteDao.getAll();
    }

    LiveData<List<Note>> getNotes() {return notes;}
}
