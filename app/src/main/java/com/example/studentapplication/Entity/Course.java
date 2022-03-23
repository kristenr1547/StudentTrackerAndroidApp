package com.example.studentapplication.Entity;


import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses", foreignKeys =  @ForeignKey(entity = Term.class, parentColumns = "termID", childColumns = "termID", onDelete = CASCADE))
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;
    private String courseTitle;
    private String courseStart;
    private String courseEnd;
    private String status;
    private int termID;

    public Course(int courseID, String courseTitle, String courseStart, String courseEnd, String status, int termID) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.courseStart = courseStart;
        this.courseEnd = courseEnd;
        this.status = status;
        this.termID = termID;
    }
    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
    public int getTermID() {
        return termID;
    }

    public void setTermID(int courseID) {
        this.termID = courseID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseStart() {
        return courseStart;
    }

    public void setCourseStart(String courseStart) {
        this.courseStart = courseStart;
    }

    public String getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(String courseEnd) {
        this.courseEnd = courseEnd;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @NonNull
    @Override
    public String toString() {
        return "Course ID: " + courseID + " Course Title: " + courseTitle +
                " Start Date:  " + courseStart + " End Date: " + courseEnd +
                " Status: " + status;
    }
}
