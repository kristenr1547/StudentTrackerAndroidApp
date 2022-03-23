package com.example.studentapplication.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studentapplication.Database.Repository;
import com.example.studentapplication.Entity.Assessment;
import com.example.studentapplication.Entity.Course;
import com.example.studentapplication.R;

import java.util.List;

public class Assessments_List extends AppCompatActivity {
    int courseID;
    Repository repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        setTitle("Assessment List");
        //get intent info sent from term details to search for courses that belong to the term
        repo = new Repository(getApplication());
        courseID = getIntent().getIntExtra("courseID",-1);
        RecyclerView recyclerView = findViewById(R.id.assessmentRecyclerView);
        List<Assessment> assessments = repo.getAssessmentsByCourse(courseID); //returns list of assessments by courseID
        final AssessmentAdapter adapter = new AssessmentAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setAssesssments(assessments);
    }


    public void onBackToCourseDetails(View view) {
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

    public void onAddAssessment(View view) {
        Intent i = new Intent(this, AddAssessment.class);
        i.putExtra("courseID",courseID);
        startActivity(i);
    }
}