package com.example.studentapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.studentapplication.Database.Repository;
import com.example.studentapplication.Entity.Instructor;
import com.example.studentapplication.R;

public class AddInstructor extends AppCompatActivity {
    EditText editAddInstructorName;
    EditText editAddInstructorPhone;
    EditText editAddInstructorEmail;
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
        setContentView(R.layout.activity_add_instructor);
        setTitle("ADD INSTRUCTOR");
        editAddInstructorEmail = findViewById(R.id.instructorEmailDetails);
        editAddInstructorPhone = findViewById(R.id.instructorPhoneDetails);
        editAddInstructorName = findViewById(R.id.instructorNameDetails);
        repo = new Repository(getApplication());
        courseID = getIntent().getIntExtra("courseID", -1);
        termID = getIntent().getIntExtra("termID", -1);
        courseTitle = getIntent().getStringExtra("courseTitle");
        courseStart = getIntent().getStringExtra("courseStart");
        courseEnd = getIntent().getStringExtra("courseEnd");
        courseStatus = getIntent().getStringExtra("courseStatus");
    }

    public void onBackToInstructorList(View view) {
        Intent i = new Intent(this, Instructor_List.class);
        i.putExtra("courseID", courseID);
        i.putExtra("termID", termID);
        i.putExtra("courseTitle", courseTitle);
        i.putExtra("courseStart", courseStart);
        i.putExtra("courseEnd", courseEnd);
        i.putExtra("courseStatus", courseStatus);
        startActivity(i);
    }

    public void saveAddInstructor(View view) {
        Instructor newInstructor;
        int instructorID;
        if(repo.getAllInstructors().size()==0) {//if there are no courses in the list prevent loop out of bounds error
            instructorID = 1;
        }else{
            instructorID = repo.getAllInstructors().get(repo.getAllInstructors().size()-1).getInstructorID() + 1;
        }
        newInstructor=new Instructor(instructorID,editAddInstructorName.getText().toString(),
                editAddInstructorEmail.getText().toString(),editAddInstructorPhone.getText().toString(),courseID);
        repo.insert(newInstructor);
        //return to list after saving new instructor
        Intent i = new Intent(this, Instructor_List.class);
        i.putExtra("courseID", courseID);
        i.putExtra("termID", termID);
        i.putExtra("courseTitle", courseTitle);
        i.putExtra("courseStart", courseStart);
        i.putExtra("courseEnd", courseEnd);
        i.putExtra("courseStatus", courseStatus);
        startActivity(i);
    }
}