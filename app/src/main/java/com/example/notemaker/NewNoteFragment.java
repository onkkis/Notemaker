package com.example.notemaker;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

public class NewNoteFragment extends Fragment {

    private NoteDatabase db;
    private NoteDao noteDao;
    private EditText note_text,note_title;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_note, container, false);
        note_text = v.findViewById(R.id.note_edittext);
        note_title = v.findViewById(R.id.note_title);

        db = NoteDatabase.getDatabase(getContext());
        noteDao = db.noteDao();

        return v;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btn_create).setOnClickListener(v-> {
            if(note_text.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), "Please write note text.",
                        Toast.LENGTH_LONG).show();
            }else if(note_title.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), "Please enter a title.",
                        Toast.LENGTH_LONG).show();
            }else {
                Note note = new Note(note_title.getText().toString(),
                        note_text.getText().toString());
                noteDao.insert(note);
                Toast.makeText(getActivity(), "Note successfully created!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }


}