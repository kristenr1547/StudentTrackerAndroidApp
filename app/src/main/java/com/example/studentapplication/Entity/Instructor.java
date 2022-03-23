package com.example.studentapplication.Entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "instructors", foreignKeys =  @ForeignKey(entity = Course.class, parentColumns = "courseID", childColumns = "courseID", onDelete = CASCADE))
public class Instructor {
    @PrimaryKey(autoGenerate = true)
    private int instructorID;
    private String instructorName;
    private String instructorEmail;
    private String instructorPhoneNumber;
    private int courseID;

    //constructor

    public Instructor(int instructorID, String instructorName, String instructorEmail, String instructorPhoneNumber, int courseID) {
        this.instructorID = instructorID;
        this.instructorName = instructorName;
        this.instructorEmail = instructorEmail;
        this.instructorPhoneNumber = instructorPhoneNumber;
        this.courseID = courseID;
    }

    //getters and setters


    public int getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(int instructorID) {
        this.instructorID = instructorID;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public String getInstructorPhoneNumber() {
        return instructorPhoneNumber;
    }

    public void setInstructorPhoneNumber(String instructorPhoneNumber) {
        this.instructorPhoneNumber = instructorPhoneNumber;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
