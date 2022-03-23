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
import com.example.studentapplication.Entity.Assessment;
import com.example.studentapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AssessmentDetails extends AppCompatActivity {
    int courseID;
    int assessmentID;
    String assessmentTitle;
    String assessmentEnd;
    String assessmentStart;
    String assessmentType;
    RadioButton performanceDetails;
    RadioButton objectiveDetails;
    EditText assessmentTitleDetails;
    EditText assessmentDetailsEndDateField;
    EditText detailsAssessmentStartField;
    final Calendar myAssessmentEndCal = Calendar.getInstance(); //assessment end date
    final Calendar myAssessmentStartCal = Calendar.getInstance(); //assessment end date
    DatePickerDialog.OnDateSetListener assessmentEndDate;
    DatePickerDialog.OnDateSetListener assessmentStartDate;
    SimpleDateFormat sdf =  new SimpleDateFormat("MM/dd/yy", Locale.US);
    Repository repo = new Repository(getApplication());
    String assessmentUpdatedType;
    boolean typeChanged;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        setTitle("Assessment Details");
        //binding textfields
        detailsAssessmentStartField = findViewById(R.id.detailsAssessmentStartField);
        assessmentDetailsEndDateField = findViewById(R.id.assessmentDetailsEndDateField);
        assessmentTitleDetails = findViewById(R.id.detailsAssessmentTitle);
        objectiveDetails = findViewById(R.id.objectiveDetails);
        performanceDetails = findViewById(R.id.performanceDetails);
        //values received from assessment adapter (the selected assessment)
        courseID = getIntent().getIntExtra("assessmentCourseID", -1);
        assessmentID = getIntent().getIntExtra("assessmentID", -1);
        assessmentTitle = getIntent().getStringExtra("assessmentTitle");
        assessmentEnd = getIntent().getStringExtra("assessmentEnd");
        assessmentType = getIntent().getStringExtra("assessmentType");
        assessmentStart = getIntent().getStringExtra("assessmentStart");
        //setting text field values
        assessmentDetailsEndDateField.setText(assessmentEnd);
        detailsAssessmentStartField.setText(assessmentStart);
        assessmentTitleDetails.setText(assessmentTitle);
        if(assessmentType.equals("Performance")){
            performanceDetails.setChecked(true);
        }else{
            objectiveDetails.setChecked(true);
        }
        //start date listener
        detailsAssessmentStartField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date date;
                String startAssessment = detailsAssessmentStartField.getText().toString();
                if(startAssessment.equals("")){
                    startAssessment = "01/01/22";
                }
                try{
                    myAssessmentStartCal.setTime(sdf.parse(startAssessment));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetails.this, assessmentStartDate, myAssessmentStartCal.get(Calendar.YEAR),
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
                detailsAssessmentStartUpdateLabel();
            }
        };


        //end date listener
        assessmentDetailsEndDateField.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date date;
                String endAssessment = assessmentDetailsEndDateField.getText().toString();
                if(endAssessment.equals("")){
                    endAssessment = "01/01/22";
                }
                try{
                    myAssessmentEndCal.setTime(sdf.parse(endAssessment));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(AssessmentDetails.this, assessmentEndDate, myAssessmentEndCal.get(Calendar.YEAR),
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
                assessmentDetailsEndUpdateLabel();
            }
        };
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.assessmentmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.assessmentNotification:
//send start date notification
                String dataFromStart = detailsAssessmentStartField.getText().toString();
                Date start = null;
                try{
                    start= sdf.parse(dataFromStart);
                }catch (ParseException e){
                    e.printStackTrace();
                }

                Long trigger = start.getTime();
                Intent i = new Intent(AssessmentDetails.this,MyReceiver.class);
                i.putExtra("key","Assessment " + assessmentStart + " starts today!");
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetails.this,MainActivity.numAlert++,i, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,trigger,sender);
                //send end date notification
                String dataFromEnd = assessmentDetailsEndDateField.getText().toString();
                Date end = null;
                try{
                    end= sdf.parse(dataFromEnd);
                }catch (ParseException e){
                    e.printStackTrace();
                }

                Long trigger1 = end.getTime();
                Intent i1 = new Intent(AssessmentDetails.this,MyReceiver.class);
                i1.putExtra("key","Assessment " + assessmentTitle + " ends today!");
                PendingIntent sender1 = PendingIntent.getBroadcast(AssessmentDetails.this,MainActivity.numAlert++,i1, 0);
                AlarmManager alarmManager1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager1.set(AlarmManager.RTC_WAKEUP,trigger1,sender1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }




    public void detailsAssessmentStartUpdateLabel(){
        detailsAssessmentStartField.setText(sdf.format(myAssessmentStartCal.getTime()));
    }



    public void assessmentDetailsEndUpdateLabel(){
        assessmentDetailsEndDateField.setText(sdf.format(myAssessmentEndCal.getTime()));
    }

    public void onDetailsAssessmentTypeDetails(View view){
        boolean checked = ((RadioButton) view).isChecked();
        typeChanged = true;
        switch (view.getId()){
            case R.id.performanceDetails:
                if(checked)
                    assessmentUpdatedType = "Performance";
                break;
            case R.id.objectiveDetails:
                if(checked)
                    assessmentUpdatedType = "Objective";
                break;
        }}


    public void onBackToAssessmentList(View view) {
        Intent i = new Intent(this, Assessments_List.class);
        i.putExtra("courseID", courseID);
        startActivity(i);
    }

    public void onSaveDetailsAssessment(View view) {
        Assessment changedAssessment;
        if(typeChanged){
            changedAssessment = new Assessment(assessmentID,assessmentTitleDetails.getText().toString(),detailsAssessmentStartField.getText().toString(),assessmentDetailsEndDateField.getText().toString(),assessmentUpdatedType,courseID);

        }else{
            changedAssessment = new Assessment(assessmentID,assessmentTitleDetails.getText().toString(),detailsAssessmentStartField.getText().toString(),assessmentDetailsEndDateField.getText().toString(),assessmentType,courseID);
        }
        repo.update(changedAssessment);
        Intent i = new Intent(this, Assessments_List.class);
        i.putExtra("courseID", courseID);
        startActivity(i);
    }

    public void onDeleteAssessment(View view) {
        Assessment deletedAssessment = new Assessment(assessmentID,assessmentTitle,assessmentStart,assessmentEnd,assessmentType,courseID);
        repo.delete(deletedAssessment);
        Intent i = new Intent(this, Assessments_List.class);
        i.putExtra("courseID", courseID);
        startActivity(i);
    }
}