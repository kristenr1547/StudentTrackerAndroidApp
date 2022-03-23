package com.example.studentapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.Locale;

public class CourseDetails extends AppCompatActivity {
    int courseID;
    int termID;
    String courseTitle;
    String courseStart;
    String courseEnd;
    String courseStatus;
    String detailEditStatus;
    EditText editCourseDetailsTitle;
    EditText editCourseDetailsStart;
    EditText editCourseDetailsEnd;
    RadioButton detailsInProgress;
    RadioButton detailsDropped;
    RadioButton detailsCompleted;
    RadioButton detailsPlanningToTake;
    boolean statusChange;
    Repository repo = new Repository(getApplication());

    SimpleDateFormat sdf =  new SimpleDateFormat("MM/dd/yy", Locale.US);
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarDetailStart = Calendar.getInstance(); //course start date
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarDetailEnd = Calendar.getInstance(); //course end
    String detailStart;
    String detailEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        setTitle("Course Details");
        //getting data passed from course list activity
        courseID = getIntent().getIntExtra("courseID", -1);
        termID = getIntent().getIntExtra("termID", -1);
        courseTitle = getIntent().getStringExtra("courseTitle");
        courseStart = getIntent().getStringExtra("courseStart");
        courseEnd = getIntent().getStringExtra("courseEnd");
        courseStatus = getIntent().getStringExtra("courseStatus");
        //binding values in xml file
        editCourseDetailsTitle = findViewById(R.id.editCourseDetailsTitle);
        editCourseDetailsStart = findViewById(R.id.editCourseDetailsStart);
        editCourseDetailsEnd = findViewById(R.id.editCourseDetailsEnd);
        detailsInProgress = findViewById(R.id.detailsInProgress);
        detailsDropped = findViewById(R.id.detailsDropped);
        detailsCompleted = findViewById(R.id.detailsCompleted);
        detailsPlanningToTake = findViewById(R.id.detailsPlanningToTake);
        //setting fields
        editCourseDetailsTitle.setText(courseTitle);
        editCourseDetailsStart.setText(courseStart);
        editCourseDetailsEnd.setText(courseEnd);
        if(courseStatus.equals("In Progress")){
            detailsInProgress.setChecked(true);
        }else if(courseStatus.equals("Dropped")){
            detailsDropped.setChecked(true);
        }else if(courseStatus.equals("Planning to take")){
            detailsPlanningToTake.setChecked(true);
        }else if(courseStatus.equals("Completed")){
            detailsCompleted.setChecked(true);
        }
        editCourseDetailsStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date date;
                String startCourse = editCourseDetailsStart.getText().toString();
                if(startCourse.equals("")){
                    startCourse = "01/01/22";
                }
                try{
                    myCalendarDetailStart.setTime(sdf.parse(startCourse));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, startDate, myCalendarDetailStart.get(Calendar.YEAR),
                        myCalendarDetailStart.get(Calendar.MONTH), myCalendarDetailStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //get data back from the date picker
        startDate = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendarDetailStart.set(Calendar.YEAR, year);
                myCalendarDetailStart.set(Calendar.MONTH,month);
                myCalendarDetailStart.set(Calendar.DAY_OF_MONTH,day);
                updateLabelStart();
            }
        };

        //creating listener for end date
        editCourseDetailsEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date date;
                String endCourse = editCourseDetailsEnd.getText().toString();
                if(endCourse.equals("")){
                    endCourse = "01/01/22";
                }
                try{
                    myCalendarDetailEnd.setTime(sdf.parse(endCourse));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(CourseDetails.this, endDate, myCalendarDetailEnd.get(Calendar.YEAR),
                        myCalendarDetailEnd.get(Calendar.MONTH), myCalendarDetailEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //get data back from the date picker
        endDate = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendarDetailEnd.set(Calendar.YEAR, year);
                myCalendarDetailEnd.set(Calendar.MONTH,month);
                myCalendarDetailEnd.set(Calendar.DAY_OF_MONTH,day);
                updateLabelEnd();
            }
        };
    }


    //course start
    private void updateLabelStart(){
        editCourseDetailsStart.setText(sdf.format(myCalendarDetailStart.getTime()));
        detailStart = sdf.format(myCalendarDetailStart.getTime());
    }
    //course end
    private void updateLabelEnd(){
        editCourseDetailsEnd.setText(sdf.format(myCalendarDetailEnd.getTime()));
        detailEnd = sdf.format(myCalendarDetailEnd.getTime());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.coursemenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.courseNotification:
                //send start date notification
                String dataFromCourseStart = editCourseDetailsStart.getText().toString();
                Date start = null;
                try{
                    start= sdf.parse(dataFromCourseStart);
                }catch (ParseException e){
                    e.printStackTrace();
                }

                Long trigger = start.getTime();
                Intent i = new Intent(CourseDetails.this,MyReceiver.class);
                i.putExtra("key","Course " + courseTitle + " starts today!");
                PendingIntent sender = PendingIntent.getBroadcast(CourseDetails.this,MainActivity.numAlert++,i, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,trigger,sender);
                //send end date notification
            String dataFromCourseEnd = editCourseDetailsEnd.getText().toString();
            Date end = null;
            try{
                end= sdf.parse(dataFromCourseEnd);
            }catch (ParseException e){
                e.printStackTrace();
            }

            Long trigger1 = end.getTime();
            Intent i1 = new Intent(CourseDetails.this,MyReceiver.class);
            i1.putExtra("key","Course " + courseTitle + " ends today!");
            PendingIntent sender1 = PendingIntent.getBroadcast(CourseDetails.this,MainActivity.numAlert++,i1, 0);
            AlarmManager alarmManager1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager1.set(AlarmManager.RTC_WAKEUP,trigger1,sender1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void courseDetailStatusChange(View view) {
        statusChange = true;
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.detailsInProgress:
                if(checked)
                    detailEditStatus = "In Progress";
                break;
            case R.id.detailsDropped:
                if(checked)
                    detailEditStatus = "Dropped";
                break;
            case R.id.detailsPlanningToTake:
                if(checked)
                    detailEditStatus = "Planning to take";
                break;
            case R.id.detailsCompleted:
                if(checked)
                    detailEditStatus = "Completed";
                break;
        }
    }

    public void onBack(View view) {
    Intent i = new Intent(this, Courses_List.class);
    i.putExtra("id", termID);
    startActivity(i);



    }

    public void onSave(View view) {
        Course course;
        if(courseTitle.equals(editCourseDetailsTitle.getText().toString())
                && courseStart.equals(editCourseDetailsStart.getText().toString())
                && courseEnd.equals(editCourseDetailsEnd.getText().toString())
                && courseStatus.equals(detailEditStatus)){
                 //nothing was changed no need to call database
        }else if(statusChange){
            //if detailedEditStatus has a value
            course = new Course(courseID,editCourseDetailsTitle.getText().toString(),
                    editCourseDetailsStart.getText().toString(),editCourseDetailsEnd.getText().toString(),detailEditStatus, termID);
            System.out.println("Course Status: " + detailEditStatus + "Radio button was changed");
            repo.update(course);
        }else{
            //if radiobuttons were not changed but something else was
            course = new Course(courseID,editCourseDetailsTitle.getText().toString(),
                    editCourseDetailsStart.getText().toString(),editCourseDetailsEnd.getText().toString(),courseStatus,termID);
            System.out.println("Course Status: " + courseStatus);
            repo.update(course);
        }
        Intent i = new Intent(this, Courses_List.class);
        i.putExtra("id", termID);
        startActivity(i);


    }


    public void onViewNotes(View view) {
        Intent i = new Intent(this, Notes_List.class);
        i.putExtra("courseID", courseID);
        i.putExtra("termID", termID);
        i.putExtra("courseTitle", courseTitle);
        i.putExtra("courseStart", courseStart);
        i.putExtra("courseEnd", courseEnd);
        i.putExtra("courseStatus", courseStatus);
        startActivity(i);
    }


    public void onViewInstructors(View view) {
        Intent i = new Intent(this, Instructor_List.class);
        i.putExtra("courseID", courseID);
        i.putExtra("termID", termID);
        i.putExtra("courseTitle", courseTitle);
        i.putExtra("courseStart", courseStart);
        i.putExtra("courseEnd", courseEnd);
        i.putExtra("courseStatus", courseStatus);
        startActivity(i);
    }


    public void onViewAssessments(View view) {
        Intent i = new Intent(this, Assessments_List.class);
        i.putExtra("courseID", courseID);
        i.putExtra("termID", termID);
        i.putExtra("courseTitle", courseTitle);
        i.putExtra("courseStart", courseStart);
        i.putExtra("courseEnd", courseEnd);
        i.putExtra("courseStatus", courseStatus);
        startActivity(i);
    }


    public void onDeleteCourse(View view) {
        Course course = new Course(courseID,courseTitle,courseStart,courseEnd,courseStatus,termID);
        repo.delete(course);

    }
}