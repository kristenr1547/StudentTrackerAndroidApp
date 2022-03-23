package com.example.studentapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.studentapplication.Database.Repository;
import com.example.studentapplication.Entity.Instructor;
import com.example.studentapplication.R;

public class InstructorDetails extends AppCompatActivity {
    EditText instructorEmailDetails;
    EditText instructorPhoneDetails;
    EditText instructorNameDetails;
    String email;
    String name;
    String phone;
    int courseID;
    int instructorID;
    Repository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_details);
        setTitle("Instructor Details");
        instructorEmailDetails = findViewById(R.id.instructorEmailDetails);
        instructorPhoneDetails = findViewById(R.id.instructorPhoneDetails);
        instructorNameDetails = findViewById(R.id.instructorNameDetails);
        email = getIntent().getStringExtra("instructorEmail");
        name = getIntent().getStringExtra("instructorName");
        phone = getIntent().getStringExtra("instructorPhone");
        instructorID = getIntent().getIntExtra("instructorID", -1);
        courseID = getIntent().getIntExtra("courseID", -1);
        repo= new Repository(getApplication());
        instructorNameDetails.setText(name);
        instructorPhoneDetails.setText(phone);
        instructorEmailDetails.setText(email);
    }



    public void saveInstructorDetails(View view) {
        Instructor changedInstructor = new Instructor(instructorID,instructorNameDetails.getText().toString(),
                instructorEmailDetails.getText().toString(),instructorPhoneDetails.getText().toString(),courseID);
                repo.update(changedInstructor);
        Intent i = new Intent(this, Instructor_List.class);
        i.putExtra("courseID", courseID);
        startActivity(i);
    }

    public void backToInstructorList(View view) {
        Intent i = new Intent(this, Instructor_List.class);
        i.putExtra("courseID", courseID);
        startActivity(i);
    }

    public void onDeleteInstructor(View view) {
        Instructor instructor = new Instructor(instructorID,name,email,phone,courseID);
        repo.delete(instructor);
        Intent i = new Intent(this, Instructor_List.class);
        i.putExtra("courseID", courseID);
        startActivity(i);
    }
}