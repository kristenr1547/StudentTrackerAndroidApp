package com.example.studentapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studentapplication.Database.Repository;
import com.example.studentapplication.Entity.ClassNotes;
import com.example.studentapplication.Entity.Course;
import com.example.studentapplication.Entity.Term;
import com.example.studentapplication.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static int numAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("STUDENT APPLICATION");
    }
    public void enterProgram(View v){
        Intent i = new Intent(this, Terms_List.class);
        startActivity(i);
        Repository repo = new Repository(getApplication());
    }







}