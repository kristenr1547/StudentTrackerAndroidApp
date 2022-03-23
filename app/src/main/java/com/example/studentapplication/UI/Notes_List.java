package com.example.studentapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studentapplication.Database.Repository;
import com.example.studentapplication.Entity.ClassNotes;
import com.example.studentapplication.Entity.Course;
import com.example.studentapplication.Entity.Term;
import com.example.studentapplication.R;

import java.util.List;

public class Notes_List extends AppCompatActivity {
    int courseID;
    int termID;
    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        courseID = getIntent().getIntExtra("courseID",-1);
        setTitle("Notes List");
        setContentView(R.layout.activity_notes_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        RecyclerView recyclerView = findViewById(R.id.noteRecyclerView);
        repo = new Repository(getApplication());
        List<ClassNotes> notes = repo.getNotesByCourse(courseID);
        System.out.println("NOTES LENGTH IS: " + notes.size());
        final NoteAdapter adapter = new NoteAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setNotes(notes);
        termID = getIntent().getIntExtra("termID", -1);
        System.out.println("courseID in onCreate Notes list: " + courseID);
    }

    public void onBackFromNotesList(View view) {
        Course course = repo.getCourseByID(courseID).get(0);
        Intent i = new Intent(this, CourseDetails.class);
        i.putExtra("courseID",course.getCourseID());
        i.putExtra("courseTitle", course.getCourseTitle());
        i.putExtra("courseStart", course.getCourseStart());
        i.putExtra("courseEnd", course.getCourseEnd());
        i.putExtra("courseStatus", course.getStatus());
        i.putExtra("termID", course.getTermID());
        startActivity(i);
    }

    public void addNotes(View view) {
        Intent i = new Intent(this, AddNote.class);
        i.putExtra("courseID", courseID);
        i.putExtra("termID", termID);
        startActivity(i);
    }
}