package com.example.notemaker;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private LiveData<List<Note>> notes;

    //Note selection
    private MutableLiveData<Integer> selected = new MutableLiveData<>();
    public void select(int position) {
        selected.setValue(position);
    }
    public LiveData<Integer> getSelected(){
        return selected;
    }

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        notes = noteRepository.getNotes();
    }

    LiveData<List<Note>> getNotes() {return notes;}
}
