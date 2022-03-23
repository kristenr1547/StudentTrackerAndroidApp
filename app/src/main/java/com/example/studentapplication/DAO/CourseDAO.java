package com.example.studentapplication.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.studentapplication.Entity.Course;
import com.example.studentapplication.Entity.Term;


import java.util.List;

@Dao
public interface CourseDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);

    @Query("SELECT * FROM courses ORDER BY courseID")
    List<Course> getAllCourses();

    @Query("SELECT * FROM courses WHERE termID = :selectedTermID")
    List<Course> getCoursesByTerm(int selectedTermID);

    @Query("SELECT * FROM courses WHERE courseID = :id")
    List<Course> getCourseByID(int id);

}
