package com.example.studentapplication.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.studentapplication.Entity.Assessment;
import com.example.studentapplication.Entity.Course;


import java.util.List;

@Dao
public interface AssessmentDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Update
    void update(Assessment assessment);


    @Delete
    void delete(Assessment assessment);

    @Query("SELECT * FROM assessments ORDER BY assessmentID")
    List<Assessment> getAllAssessments();

    @Query("SELECT * FROM assessments WHERE courseID = :selectedCourseID")
    List<Assessment> getAssessmentsByCourse(int selectedCourseID);

}
