package com.example.notesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        notes = new ArrayList<>();
        notes.add(makeNoteFromMessage("Once upon a time in a very very cold place"));

        // specify an adapter
        mAdapter = new MyAdapter(notes);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public void onResume(){
        super.onResume();
        // Get the Intent from OpenNoteActivity and extract the string
        Intent intent = getIntent();

        String message = intent.getStringExtra(OpenNoteActivity.EXTRA_MESSAGE);
        if (message != null){
            notes.add(makeNoteFromMessage(message));
            mAdapter.notifyDataSetChanged();
        }

    }


    /** Called when the user taps the New Note button */
    public void newNote(View view) {
        Intent intent = new Intent(this, OpenNoteActivity.class);
        startActivity(intent);

    }

    public Note makeNoteFromMessage(String message){

        //Add if statement if string is shorter than 10
        String title = message.substring(0,10);
        Note note = new Note(title, message);

        return note;
    }

}
