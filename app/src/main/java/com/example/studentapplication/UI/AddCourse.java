package com.example.studentapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.studentapplication.Database.Repository;
import com.example.studentapplication.Entity.Course;
import com.example.studentapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddCourse extends AppCompatActivity {
    EditText startDateEdit;
    EditText editEndDate;
    int termID;
    SimpleDateFormat sdf =  new SimpleDateFormat("MM/dd/yy", Locale.US);
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance(); //course start date
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarEnd = Calendar.getInstance(); //course end
    Repository repo = new Repository(getApplication());
    String AddStartDate;
    String AddEndDate;
    String selectedStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        setTitle("Add Course");
        editEndDate = findViewById(R.id.editEndDate);
        startDateEdit = findViewById(R.id.startDateEdit);
        termID = getIntent().getIntExtra("id",-1);
        //course start date listener
        startDateEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date date;
                String startCourse = startDateEdit.getText().toString();
                if(startCourse.equals("")){
                    startCourse = "01/01/22";
                }
                try{
                    myCalendarStart.setTime(sdf.parse(startCourse));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(AddCourse.this, startDate, myCalendarStart.get(Calendar.YEAR),
                        myCalendarStart.get(Calendar.MONTH),myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //get data back from the date picker
        startDate = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH,month);
                myCalendarStart.set(Calendar.DAY_OF_MONTH,day);
                updateLabelStart();
            }
        };

        //creating listener for end date
        editEndDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date date;
                String endCourse = editEndDate.getText().toString();
                if(endCourse.equals("")){
                    endCourse = "01/01/22";
                }
                try{
                    myCalendarEnd.setTime(sdf.parse(endCourse));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(AddCourse.this, endDate, myCalendarEnd.get(Calendar.YEAR),
                        myCalendarEnd.get(Calendar.MONTH),myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //get data back from the date picker
        endDate = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH,month);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH,day);
                updateLabelEnd();
            }
        };
    }


//course start
    private void updateLabelStart(){
        startDateEdit.setText(sdf.format(myCalendarStart.getTime()));
        AddStartDate = sdf.format(myCalendarStart.getTime());
    }
    //course end
    private void updateLabelEnd(){
        editEndDate.setText(sdf.format(myCalendarEnd.getTime()));
        AddEndDate = sdf.format(myCalendarEnd.getTime());
    }



    public void onSaveAddCourse(View view) {
        //creating course
        int termID = getIntent().getIntExtra("id",-1);
        //creates new course and inserts into database
        Course addCourse;
        int courseID = 1;
        EditText titleText = findViewById(R.id.courseTitleText);
        String title = titleText.getText().toString();
        if(repo.getAllCourses().size()==0) {//if there are no courses in the list prevent loop out of bounds error
            courseID = 1;
        }else{
            courseID = repo.getAllCourses().get(repo.getAllCourses().size()-1).getCourseID() + 1;
        }
        addCourse = new Course(courseID,title,AddStartDate,AddEndDate,selectedStatus,termID);
        repo.insert(addCourse);
        Intent i = new Intent(this, Courses_List.class);
        i.putExtra("id", termID);
        startActivity(i);

    }



    public void OnCancel(View view) {
        //saves state of last activity (Course List)
        Intent i = new Intent(this, Courses_List.class);
        i.putExtra("id", termID);
        startActivity(i);
    }

    public void onCourseStatusClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.inProgress:
                if(checked)
                    selectedStatus = "In Progress";
                break;
            case R.id.dropped:
                if(checked)
                    selectedStatus = "Dropped";
                break;
            case R.id.planToTake:
                if(checked)
                    selectedStatus = "Planning to take";
                break;
            case R.id.completed:
                if(checked)
                    selectedStatus = "Completed";
                break;
        }
    }


}