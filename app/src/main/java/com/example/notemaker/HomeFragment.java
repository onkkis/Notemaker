package com.example.notemaker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private NoteViewModel noteViewModel;

    private NoteDatabase db;
    private NoteDao noteDao;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        db = NoteDatabase.getDatabase(getContext());
        noteDao = db.noteDao();

        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listAdapter = new ListAdapter(inflater.getContext());

        noteViewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);
        noteViewModel.getNotes().observe(getViewLifecycleOwner(), notes1 ->{
            listAdapter.resetValues(notes1);
        });

        recyclerView.setAdapter(listAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                noteViewModel.select(position);
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_HomeFragment_to_noteInfoFragment);
            }


            @Override
            public void onLongClick(View view, int position) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete note")
                        .setMessage("Are you sure you want to delete this note?")
                        .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                            // Continue with delete operation
                            noteDao.delete(listAdapter.getNoteAt(position));
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.cancel, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }));

        return v;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.add_icon).setOnClickListener(v -> {
            NavHostFragment.findNavController(HomeFragment.this)
                    .navigate(R.id.action_HomeFragment_to_NewNoteFragment);
        });
    }
}