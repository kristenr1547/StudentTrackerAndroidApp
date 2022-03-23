package com.example.studentapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.studentapplication.Database.Repository;
import com.example.studentapplication.Entity.Assessment;
import com.example.studentapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddAssessment extends AppCompatActivity {
    int courseID;
    EditText addAssessmentTitle;
    EditText addAssessmentEndDateField;
    EditText addAssessmentStartDateField;
    final Calendar myAssessmentEndCal = Calendar.getInstance(); //assessment end date
    final Calendar myAssessmentStartCal = Calendar.getInstance(); //assessment start date
    DatePickerDialog.OnDateSetListener assessmentEndDate;
    DatePickerDialog.OnDateSetListener assessmentStartDate;
    String AddAssessmentEndDate;
    String AddAssessmentStartDate;
    SimpleDateFormat sdf =  new SimpleDateFormat("MM/dd/yy", Locale.US);
    Repository repo = new Repository(getApplication());
    String assessmentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assessment);
        courseID = getIntent().getIntExtra("courseID", -1);
        addAssessmentTitle = findViewById(R.id.addAssessmentTitle);
        addAssessmentEndDateField = findViewById(R.id.assessmentAddEndDateField);
        addAssessmentStartDateField = findViewById(R.id.assessmentAddStartDateField);
        setTitle("ADD ASSESSMENT");
        //start date listener

        addAssessmentStartDateField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date date;
                String startAssessment = addAssessmentStartDateField.getText().toString();
                if(startAssessment.equals("")){
                    startAssessment = "01/01/22";
                }
                try{
                    myAssessmentStartCal.setTime(sdf.parse(startAssessment));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(AddAssessment.this, assessmentStartDate, myAssessmentStartCal.get(Calendar.YEAR),
                        myAssessmentStartCal.get(Calendar.MONTH),myAssessmentStartCal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //get data back from the date picker
        assessmentStartDate = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myAssessmentStartCal.set(Calendar.YEAR, year);
                myAssessmentStartCal.set(Calendar.MONTH,month);
                myAssessmentStartCal.set(Calendar.DAY_OF_MONTH,day);
                addAssessmentStartUpdateLabel();
            }
        };


        //end date listener
        addAssessmentEndDateField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date date;
                String endAssessment = addAssessmentEndDateField.getText().toString();
                if(endAssessment.equals("")){
                    endAssessment = "01/01/22";
                }
                try{
                    myAssessmentEndCal.setTime(sdf.parse(endAssessment));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(AddAssessment.this, assessmentEndDate, myAssessmentEndCal.get(Calendar.YEAR),
                        myAssessmentEndCal.get(Calendar.MONTH),myAssessmentEndCal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //get data back from the date picker
        assessmentEndDate = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myAssessmentEndCal.set(Calendar.YEAR, year);
                myAssessmentEndCal.set(Calendar.MONTH,month);
                myAssessmentEndCal.set(Calendar.DAY_OF_MONTH,day);
                addAssessmentEndUpdateLabel();
            }
        };

    }

    public void addAssessmentEndUpdateLabel(){
        addAssessmentEndDateField.setText(sdf.format(myAssessmentEndCal.getTime()));
        AddAssessmentEndDate = sdf.format(myAssessmentEndCal.getTime());
    }

    public void addAssessmentStartUpdateLabel(){
        addAssessmentStartDateField.setText(sdf.format(myAssessmentStartCal.getTime()));
        AddAssessmentStartDate = sdf.format(myAssessmentStartCal.getTime());
    }


    public void onAddAssessmentType(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.performanceAdd:
                if(checked)
                    assessmentType = "Performance";
                break;
            case R.id.objectiveAdd:
                if(checked)
                    assessmentType = "Objective";
                break;
        }}


    public void onBackToAssessmentList(View view) {
        Intent i = new Intent(this, Assessments_List.class);
        i.putExtra("courseID", courseID);
        startActivity(i);

    }

    public void onSaveAddAssessment(View view){
        int assessmentListSize = repo.getAssessmentsByCourse(courseID).size();
        if(assessmentListSize < 5){ //only allows user to create up to 5 assessments per course
            Assessment newAssessment;
            int addAssessmentId;
            if(repo.getAllAssessments().size()==0) {//if there are no courses in the list prevent loop out of bounds error
                addAssessmentId = 1;
            }else{
                addAssessmentId = repo.getAllAssessments().get(repo.getAllAssessments().size()-1).getAssessmentID() + 1;
            }
            newAssessment = new Assessment(addAssessmentId,addAssessmentTitle.getText().toString(),AddAssessmentStartDate,AddAssessmentEndDate,assessmentType,courseID);
            repo.insert(newAssessment);
            Intent i = new Intent(this, Assessments_List.class);
            i.putExtra("courseID", courseID);
            startActivity(i);
        }else{
            Context context = getApplicationContext();
            Toast.makeText(context, "Course must have 5 or less assessments.", Toast.LENGTH_LONG).show();
            System.out.println("should have shown toast message");
        }

    }

}
