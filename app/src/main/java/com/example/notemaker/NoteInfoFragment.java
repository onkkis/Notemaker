package com.example.notemaker;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NoteInfoFragment extends Fragment {

    private NoteViewModel noteViewModel;
    private EditText note_text;

    private NoteDatabase db;
    private NoteDao noteDao;

    boolean saved = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event

                if(!saved){
                    new AlertDialog.Builder(getContext())
                            .setTitle("Note might be unsaved!")
                            .setMessage("Do you still wish to exit?")
                            .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                                NavHostFragment.findNavController(NoteInfoFragment.this)
                                        .navigate(R.id.action_noteInfoFragment_to_HomeFragment);

                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.cancel, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else {
                    NavHostFragment.findNavController(NoteInfoFragment.this)
                            .navigate(R.id.action_noteInfoFragment_to_HomeFragment);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_note_info, container, false);
        note_text = v.findViewById(R.id.note_info_text);

        note_text.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                saved = false;
            } else {

            }
        });

        db = NoteDatabase.getDatabase(getContext());
        noteDao = db.noteDao();

        noteViewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);
        noteViewModel.getSelected().observe(getViewLifecycleOwner(), selected ->{
            noteViewModel.getNotes().observe(getViewLifecycleOwner(), notes1 ->{
                note_text.setText(notes1.get(selected).getText(), TextView.BufferType.EDITABLE);
            });
        });

        v.findViewById(R.id.btn_save).setOnClickListener(v1 -> {
            noteViewModel.getSelected().observe(getViewLifecycleOwner(), selected ->{
                noteViewModel.getNotes().observe(getViewLifecycleOwner(), notes1 ->{
                    Note prevNote = notes1.get(selected);
                    Note newNote = new Note(prevNote.getTitle(), note_text.getText().toString());
                    noteDao.delete(prevNote);
                    noteDao.insert(newNote);
                });
            });
            Toast.makeText(getActivity(), "Note successfully saved!",
                    Toast.LENGTH_LONG).show();
            saved = true;
        });


        return v;
    }
}