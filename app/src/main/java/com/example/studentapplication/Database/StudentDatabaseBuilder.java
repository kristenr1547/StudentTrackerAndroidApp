package com.example.studentapplication.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.studentapplication.DAO.AssessmentDAO;
import com.example.studentapplication.DAO.ClassNotesDao;
import com.example.studentapplication.DAO.CourseDAO;
import com.example.studentapplication.DAO.InstructorDAO;
import com.example.studentapplication.DAO.TermDAO;
import com.example.studentapplication.Entity.Assessment;
import com.example.studentapplication.Entity.ClassNotes;
import com.example.studentapplication.Entity.Course;
import com.example.studentapplication.Entity.Instructor;
import com.example.studentapplication.Entity.Term;


@Database(entities = {Term.class, Course.class, Assessment.class, Instructor.class, ClassNotes.class}, version = 11, exportSchema = false)
public abstract class StudentDatabaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();
    public abstract ClassNotesDao classNotesDAO();
    public abstract InstructorDAO instructorDAO();


    private static volatile StudentDatabaseBuilder INSTANCE;

    static StudentDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StudentDatabaseBuilder.class) {
                if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), StudentDatabaseBuilder.class, "StudentDatabase")
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
    }
    return INSTANCE;
}

}
