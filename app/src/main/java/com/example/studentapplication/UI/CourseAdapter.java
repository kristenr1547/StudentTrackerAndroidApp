package com.example.studentapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentapplication.Entity.Course;
import com.example.studentapplication.Entity.Term;
import com.example.studentapplication.R;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseItemView;
        private CourseViewHolder(View itemView){
            super(itemView);
            courseItemView = itemView.findViewById(R.id.courseTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Course current = mCourses.get(position);
                    Intent i = new Intent(context, CourseDetails.class);
                    i.putExtra("courseID",current.getCourseID());
                    i.putExtra("courseTitle", current.getCourseTitle());
                    i.putExtra("courseStart", current.getCourseStart());
                    i.putExtra("courseEnd", current.getCourseEnd());
                    i.putExtra("courseStatus", current.getStatus());
                    i.putExtra("termID", current.getTermID());
                    context.startActivity(i);
                }
            });
        }
    }

    private List<Course> mCourses;
    private final Context context;
    private final LayoutInflater mInflater;
    public CourseAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public CourseAdapter.CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.course_list_item,parent,false);
        return new CourseViewHolder(itemView);
    }

    //this is where you put things onto the textview
    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if(mCourses != null){
            Course currentCourse = mCourses.get(position);
            String name = currentCourse.getCourseTitle();
            holder.courseItemView.setText(name);
        }else{
            holder.courseItemView.setText("No Course Title");
        }
    }

    public void setCourses(List<Course> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(mCourses != null){
            return mCourses.size();
        }else{
            return 0;
        }

    }












}
