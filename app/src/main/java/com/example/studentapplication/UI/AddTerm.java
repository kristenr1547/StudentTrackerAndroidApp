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

public class AddTerm extends AppCompatActivity {
    Repository repository;
    EditText termTitleEdit;
    EditText termStartEdit;
    EditText termEndEdit;
    String AddStartDate;
    String AddEndDate;
    //String formatter
    SimpleDateFormat sdf =  new SimpleDateFormat("MM/dd/yy", Locale.US);
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalendarEnd = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);
        setTitle("Add A Term");
        //Initializing the views
        repository = new Repository(getApplication());
        termTitleEdit = findViewById(R.id.termTitleEdit);
        termStartEdit = findViewById(R.id.termStartEdit);
        termEndEdit = findViewById(R.id.termEndEdit);
        //onclick listener for editing dates
        termStartEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date date;
                String startTerm = termStartEdit.getText().toString();
                if(startTerm.equals("")){
                    startTerm = "01/01/22";
                }
                try{
                    myCalendarStart.setTime(sdf.parse(startTerm));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(AddTerm.this, startDate, myCalendarStart.get(Calendar.YEAR),
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
        termEndEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date date;
                String endTerm = termEndEdit.getText().toString();
                if(endTerm.equals("")){
                    endTerm = "01/01/22";
                }
                try{
                    myCalendarEnd.setTime(sdf.parse(endTerm));
                }catch (ParseException e){
                    e.printStackTrace();
                }
                new DatePickerDialog(AddTerm.this, endDate, myCalendarEnd.get(Calendar.YEAR),
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
        termStartEdit.setText(sdf.format(myCalendarStart.getTime()));
        AddStartDate = sdf.format(myCalendarStart.getTime());
    }
    private void updateLabelEnd(){
        termEndEdit.setText(sdf.format(myCalendarEnd.getTime()));
        AddEndDate = sdf.format(myCalendarEnd.getTime());
    }

    public void onCancelAddTerm(View view){
        Intent i = new Intent(this, Terms_List.class);
        startActivity(i);
    }

    public void onSave(View view){
        Term term;
        int termId;
        if(repository.getAllTerms().size()==0){
            termId =1;
            }else{
          termId = repository.getAllTerms().get(repository.getAllTerms().size()-1).getTermID() + 1;
        }
        String Title = termTitleEdit.getText().toString();
        term = new Term(termId,Title,AddStartDate,AddEndDate);
        repository.insert(term);
        Intent i = new Intent(this, Terms_List.class);
        startActivity(i);
}}