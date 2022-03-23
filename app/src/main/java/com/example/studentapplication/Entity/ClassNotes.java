package com.example.studentapplication.Entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes", foreignKeys =  @ForeignKey(entity = Course.class, parentColumns = "courseID", childColumns = "courseID", onDelete = CASCADE))
public class ClassNotes {
    @PrimaryKey(autoGenerate = true)
    private int noteID;
    private String noteTitle;
    private String noteBody;
    private int courseID;

    //constructor
    public ClassNotes(int noteID, String noteTitle, String noteBody, int courseID) {
        this.noteID = noteID;
        this.noteTitle = noteTitle;
        this.noteBody = noteBody;
        this.courseID = courseID;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
