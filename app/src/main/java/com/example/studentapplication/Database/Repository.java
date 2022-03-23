package com.example.studentapplication.Database;


import android.app.Application;

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

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private TermDAO mTermDao;
    private CourseDAO mCourseDao;
    private AssessmentDAO mAssessmentDAO;
    private ClassNotesDao mClassNotesDAO;
    private InstructorDAO mInstructorDAO;
    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Assessment> mAllAssessments;
    private List<Instructor> mAllInstructors;
    private List<ClassNotes> mAllClassNotes;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application){
        StudentDatabaseBuilder db =StudentDatabaseBuilder.getDatabase(application);
        mTermDao = db.termDAO();
        mCourseDao = db.courseDAO();
        mAssessmentDAO = db.assessmentDAO();
        mClassNotesDAO = db.classNotesDAO();
        mInstructorDAO = db.instructorDAO();

    }

    //term insert
    public void insert(Term term){
        databaseExecutor.execute(()->{
            mTermDao.insert(term);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
    //term update
    public void update(Term term){
        databaseExecutor.execute(()->{
            mTermDao.update(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //term delete
    public void delete(Term term){
        databaseExecutor.execute(()->{
            mTermDao.delete(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    //get terms
    public List<Term> getAllTerms(){
        databaseExecutor.execute(()->{
            mAllTerms = mTermDao.getAllTerms();
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllTerms;
    }
    //get term by ID
    public List<Term> getTermByID(int id){
        databaseExecutor.execute(()->{
            mAllTerms = mTermDao.getTermByID(id);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllTerms;
    }

    //course insert
    public void insert(Course course){
        databaseExecutor.execute(()->{
            mCourseDao.insert(course);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
    //course update
    public void update(Course course){
        databaseExecutor.execute(()->{
            mCourseDao.update(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //course delete
    public void delete(Course course){
        databaseExecutor.execute(()->{
            mCourseDao.delete(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    //get courses
    public List<Course> getAllCourses(){
        databaseExecutor.execute(()->{
            mAllCourses = mCourseDao.getAllCourses();
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllCourses;
    }
    //get courses by TermID

    public List<Course>getCoursesByTerm(int selectedTermID){
        databaseExecutor.execute(()->{
            mAllCourses = mCourseDao.getCoursesByTerm(selectedTermID);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllCourses;
    }

    //return course by ID
    public List<Course>getCourseByID(int selectedCourseID){
        databaseExecutor.execute(()->{
            mAllCourses = mCourseDao.getCourseByID(selectedCourseID);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllCourses;
    }


    //note insert
    public void insert(ClassNotes classNotes){
        databaseExecutor.execute(()->{
            mClassNotesDAO.insert(classNotes);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
    //note update
    public void update(ClassNotes classNotes){
        databaseExecutor.execute(()->{
            mClassNotesDAO.update(classNotes);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //note delete
    public void delete(ClassNotes classNotes){
        databaseExecutor.execute(()->{
            mClassNotesDAO.delete(classNotes);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    //get notes
    public List<ClassNotes> getAllNotes(){
        databaseExecutor.execute(()->{
            mAllClassNotes = mClassNotesDAO.getAllNotes();
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllClassNotes;
    }

    //get notes by courseid
    public List<ClassNotes> getNotesByCourse(int id){
        databaseExecutor.execute(()->{
            mAllClassNotes = mClassNotesDAO.getNotesByCourse(id);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllClassNotes;
    }





    //assessment insert
    public void insert(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.insert(assessment);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
    //assessment update
    public void update(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.update(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //assessment delete
    public void delete(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.delete(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    //get assessments
    public List<Assessment> getAllAssessments(){
        databaseExecutor.execute(()->{
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllAssessments;
    }
    public List<Assessment> getAssessmentsByCourse(int id){
        databaseExecutor.execute(()->{
            mAllAssessments = mAssessmentDAO.getAssessmentsByCourse(id);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllAssessments;
    }


    //instructor insert
    public void insert(Instructor instructor){
        databaseExecutor.execute(()->{
            mInstructorDAO.insert(instructor);
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }
    //instructor update
    public void update(Instructor instructor){
        databaseExecutor.execute(()->{
            mInstructorDAO.update(instructor);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //instructor delete
    public void delete(Instructor instructor){
        databaseExecutor.execute(()->{
            mInstructorDAO.delete(instructor);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    //get instructor
    public List<Instructor> getAllInstructors(){
        databaseExecutor.execute(()->{
            mAllInstructors = mInstructorDAO.getAllInstructors();
        });
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return mAllInstructors;
    }
//get instructors by courseID
public List<Instructor> getInstructorsByCourse(int id){
    databaseExecutor.execute(()->{
        mAllInstructors = mInstructorDAO.getInstructorsByCourse(id);
    });
    try {
        Thread.sleep(1000);
    }catch (InterruptedException e){
        e.printStackTrace();
    }
    return mAllInstructors;
}

}
