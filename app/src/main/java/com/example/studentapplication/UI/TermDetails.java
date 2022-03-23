package com.example.studentapplication.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.studentapplication.Database.Repository;
import com.example.studentapplication.Entity.Term;
import com.example.studentapplication.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TermDetails extends AppCompatActivity {
    EditText editTerm;//if user wants to modify title
    EditText editTermStart;//if user wants to change start date
    EditText editTermEnd;//if user wants to change end date
    String title;//data passed from the selected term in the term adapter/term list page
    String termStartDate;//data passed from the selected term in the term adapter/term list page
    String termEndDate;//data passed from the selected term in the term adapter/term list page
    SimpleDateFormat sdf =  new SimpleDateFormat("MM/dd/yy", Locale.US);
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarEnd = Calendar.getInstance();
    int termID;
    Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        setTitle("Term Details");
        //getting information sent from termlist
        title = getIntent().getStringExtra("title");
        termStartDate = getIntent().getStringExtra("start");
        termEndDate = getIntent().getStringExtra("end");
        termID = getIntent().getIntExtra("id", -1);
        //initializing views
        editTerm = findViewById(R.id.editTerm);
        editTermStart = findViewById(R.id.editTermStart);
        editTermEnd = findViewById(R.id.editTermEnd);
        //giving the views values passed from termlist
        editTerm.setText(title);
        editTermStart.setText(termStartDate);
        editTermEnd.setText(termEndDate);

        //initializing the repository
        repository = new Repository(getApplication());
        //setting up calendar views
        editTermStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date date;
                String startTerm = editTermStart.getText().toString();
                if(startTerm.equals("")){
                    startTerm = "01/01/22";
                }
                try{
                    myCalendarStart.setTime(sdf.parse(startTerm));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, startDate, myCalendarStart.get(Calendar.YEAR),
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
        editTermEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date date;
                String endTerm = editTermEnd.getText().toString();
                if(endTerm.equals("")){
                    endTerm = "01/01/22";
                }
                try{
                    myCalendarEnd.setTime(sdf.parse(endTerm));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(TermDetails.this, endDate, myCalendarEnd.get(Calendar.YEAR),
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
    private void updateLabelStart(){
        editTermStart.setText(sdf.format(myCalendarStart.getTime()));
    }
    private void updateLabelEnd(){
        editTermEnd.setText(sdf.format(myCalendarEnd.getTime()));
    }


    public void viewCourses(View view){
        Intent i = new Intent(this, Courses_List.class);
        i.putExtra("id",termID);
        startActivity(i);
    }


    //on save button actions if nothing in the editText fields were changed the program should not update the term, if it was edited term should be edited in database
    public void onSave(View view){
        Term term;
        if(editTerm.getText().toString().equals(title) && editTermStart.getText().toString().equals(termStartDate) && editTermEnd.getText().toString().equals(termEndDate)){
            //nothing was changed, no need to update database
        }else{
            term = new Term(termID,editTerm.getText().toString(),editTermStart.getText().toString(),editTermEnd.getText().toString());
            repository.update(term);
        }
        Intent i = new Intent(this, Terms_List.class);
        i.putExtra("id",termID);
        startActivity(i);

    }

    public void deleteTerm(View view){
        //only allows user to delete term if there are no courses
        if(repository.getCoursesByTerm(termID).size() == 0){
            Term term = new Term(termID,editTerm.getText().toString(),editTermStart.getText().toString(),editTermEnd.getText().toString());
            repository.delete(term);
        }
        Intent i = new Intent(this, Terms_List.class);
        i.putExtra("id",termID);
        startActivity(i);
    }









}
