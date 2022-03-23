package com.example.studentapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.studentapplication.Database.Repository;
import com.example.studentapplication.Entity.Course;
import com.example.studentapplication.Entity.Term;
import com.example.studentapplication.R;

import java.util.List;

public class Courses_List extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        setTitle("Course List");
        //get intent info sent from term details to search for courses that belong to the term
        Repository repo = new Repository(getApplication());
        int termID = getIntent().getIntExtra("id",-1);
        RecyclerView recyclerView = findViewById(R.id.courseRecyclerView);
        List<Course> courses = repo.getCoursesByTerm(termID); //returns list of courses that belongs to term selected
        final CourseAdapter adapter = new CourseAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCourses(courses);
    }


public void onBack(View view){
    Repository repo = new Repository(getApplication());
    int termID = getIntent().getIntExtra("id",-1);
        Term term = repo.getTermByID(termID).get(0);
        String TermTitle = term.getTermTitle();
        String TermStart = term.getStartDate();
        String TermEnd = term.getEndDate();
        //send back information for the term detail form
        Intent i = new Intent(this, TermDetails.class);
        i.putExtra("id",termID);
        i.putExtra("title", TermTitle);
        i.putExtra("start", TermStart);
        i.putExtra("end", TermEnd);
        startActivity(i);

}


    public void onAddCourse(View view) {
        Intent i = new Intent(this, AddCourse.class);
        int termID = getIntent().getIntExtra("id",-1);
        i.putExtra("id", termID);
        startActivity(i);

    }
}