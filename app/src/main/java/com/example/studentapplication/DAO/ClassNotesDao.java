package com.example.studentapplication.DAO;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.studentapplication.Entity.Assessment;
import com.example.studentapplication.Entity.ClassNotes;

import java.util.List;

@Dao
public interface ClassNotesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ClassNotes classNotes);

    @Update
    void update(ClassNotes classNotes);

    @Delete
    void delete(ClassNotes classNotes);

    @Query("SELECT * FROM notes ORDER BY noteID")
    List<ClassNotes> getAllNotes();

    @Query("SELECT * FROM notes WHERE courseID = :selectedCourseID")
    List<ClassNotes> getNotesByCourse(int selectedCourseID);
}
