package com.example.studentapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.studentapplication.Database.Repository;
import com.example.studentapplication.Entity.ClassNotes;
import com.example.studentapplication.R;

public class NoteDetails extends AppCompatActivity {
    EditText detailNoteTitle;
    EditText detailNoteBody;
    int noteID;
    String noteTitle;
    String noteBody;
    int courseID;
    Repository repo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        setTitle("NOTE DETAILS");
        detailNoteBody = findViewById(R.id.detailNoteBody);
        detailNoteTitle = findViewById(R.id.detailNoteTitle);
        noteID = getIntent().getIntExtra("id",-1);
        noteTitle = getIntent().getStringExtra("title");
        noteBody = getIntent().getStringExtra("body");
        courseID = getIntent().getIntExtra("courseID", -1);
        detailNoteTitle.setText(noteTitle);
        detailNoteBody.setText(noteBody);
        repo = new Repository(getApplication());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notedetail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
                    case R.id.shareNote:
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, detailNoteBody.getText().toString());
                        sendIntent.putExtra(Intent.EXTRA_TITLE, detailNoteBody.getText().toString());
                        sendIntent.setType("text/plain");
                        Intent shareIntent = Intent.createChooser(sendIntent,null);
                        startActivity(shareIntent);
                    return true;
        }
    return super.onOptionsItemSelected(item);
    }




    public void saveChangesNote(View view) {
        ClassNotes changedNote;
        String changedTitle = detailNoteTitle.getText().toString();
        String changedBody = detailNoteBody.getText().toString();
        if(noteTitle.equals(changedTitle) && noteBody.equals(changedBody)){
            //do nothing because there were no changes in note details.
            System.out.println("There were no changes in note");
        }else{
            changedNote = new ClassNotes(noteID, changedTitle,changedBody,courseID);
            repo.update(changedNote);
            System.out.println("note would have been updated");
        }
        Intent i = new Intent(this, Notes_List.class);
        i.putExtra("courseID", courseID);
        startActivity(i);
    }

    public void onDetailNoteBack(View view) {
        Intent i = new Intent(this, Notes_List.class);
        i.putExtra("courseID", courseID);
        startActivity(i);
    }

    public void onDeleteNote(View view) {
        ClassNotes note = new ClassNotes(noteID,noteTitle,noteBody,courseID);
        repo.delete(note);
        Intent i = new Intent(this, Notes_List.class);
        i.putExtra("courseID", courseID);
        startActivity(i);
    }
}