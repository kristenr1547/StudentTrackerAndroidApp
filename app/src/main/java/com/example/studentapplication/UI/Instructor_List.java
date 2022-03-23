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
import com.example.studentapplication.Entity.Instructor;
import com.example.studentapplication.R;

import java.util.List;

public class Instructor_List extends AppCompatActivity {
    int courseID;
    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_list);
        courseID = getIntent().getIntExtra("courseID",-1);
        setTitle("Instructor List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        RecyclerView recyclerView = findViewById(R.id.instructorRecyclerView);
        repo = new Repository(getApplication());
        List<Instructor> instructors = repo.getInstructorsByCourse(courseID);
        final InstructorAdapter adapter = new InstructorAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setInstructors(instructors);
    }

    public void backFromInstructorList(View view) {
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

    public void addInstructor(View view) {
        Intent i = new Intent(this, AddInstructor.class);
        int termID = getIntent().getIntExtra("termID", -1);
        String courseTitle = getIntent().getStringExtra("courseTitle");
        String courseStart = getIntent().getStringExtra("courseStart");
        String courseEnd = getIntent().getStringExtra("courseEnd");
        String courseStatus = getIntent().getStringExtra("courseStatus");
        i.putExtra("courseID", courseID);
        i.putExtra("termID", termID);
        i.putExtra("courseTitle", courseTitle);
        i.putExtra("courseStart", courseStart);
        i.putExtra("courseEnd", courseEnd);
        i.putExtra("courseStatus", courseStatus);
        startActivity(i);
    }
}