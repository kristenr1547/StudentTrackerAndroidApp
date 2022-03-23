package com.example.studentapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.studentapplication.Database.Repository;
import com.example.studentapplication.Entity.ClassNotes;
import com.example.studentapplication.R;

public class AddNote extends AppCompatActivity {
    EditText addNoteTitle;
    EditText noteBodyEdit;
    Repository repo;
    int courseID;
    int termID;
    String courseTitle;
    String courseStart;
    String courseEnd;
    String courseStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        setTitle("ADD NOTE");
        addNoteTitle = findViewById(R.id.detailNoteBody);
        noteBodyEdit = findViewById(R.id.detailNoteBody);
        repo = new Repository(getApplication());
        courseID = getIntent().getIntExtra("courseID", -1);
        termID = getIntent().getIntExtra("termID", -1);
        courseTitle = getIntent().getStringExtra("courseTitle");
        courseStart = getIntent().getStringExtra("courseStart");
        courseEnd = getIntent().getStringExtra("courseEnd");
        courseStatus = getIntent().getStringExtra("courseStatus");
    }

    public void onAddNoteBack(View view) {
        Intent i = new Intent(this, Notes_List.class);
        i.putExtra("courseID", courseID);
        i.putExtra("termID", termID);
        i.putExtra("courseTitle", courseTitle);
        i.putExtra("courseStart", courseStart);
        i.putExtra("courseEnd", courseEnd);
        i.putExtra("courseStatus", courseStatus);
        startActivity(i);

    }

    public void onSaveAddNote(View view) {
        int noteID;
        if(repo.getAllNotes().size()==0) {//if there are no courses in the list prevent loop out of bounds error
            noteID = 1;
        }else{
            noteID = repo.getAllNotes().get(repo.getAllNotes().size()-1).getNoteID() + 1;
        }


        if(noteBodyEdit.getText().toString().equals("")||noteBodyEdit.getText().toString().equals("")){
            //we dont want them to be empty
        }else{
            ClassNotes newNote = new ClassNotes(noteID,addNoteTitle.getText().toString(),noteBodyEdit.getText().toString(),courseID);
            repo.insert(newNote);
        }
        Intent i = new Intent(this, Notes_List.class);
        i.putExtra("courseID", courseID);
        i.putExtra("termID", termID);
        i.putExtra("courseTitle", courseTitle);
        i.putExtra("courseStart", courseStart);
        i.putExtra("courseEnd", courseEnd);
        i.putExtra("courseStatus", courseStatus);
        startActivity(i);
    }


}