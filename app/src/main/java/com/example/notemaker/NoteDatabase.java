package com.example.notemaker;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 2)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();

    private static NoteDatabase INSTANCE;
    private static final String DB_NAME = "note.db";

    public static NoteDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            NoteDatabase.class, DB_NAME)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
