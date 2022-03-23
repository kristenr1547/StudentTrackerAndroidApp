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
import com.example.studentapplication.Entity.Instructor;
import com.example.studentapplication.R;

import java.util.List;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.InstructorViewHolder> {

    class InstructorViewHolder extends RecyclerView.ViewHolder{
        private final TextView instructorItemView;
        private InstructorViewHolder(View itemView){
            super(itemView);
            instructorItemView = itemView.findViewById(R.id.instructorTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Instructor current = mInstructors.get(position);
                    Intent i = new Intent(context, InstructorDetails.class);
                    i.putExtra("courseID",current.getCourseID());
                    i.putExtra("instructorName", current.getInstructorName());
                    i.putExtra("instructorPhone", current.getInstructorPhoneNumber());
                    i.putExtra("instructorEmail", current.getInstructorEmail());
                    i.putExtra("instructorID", current.getInstructorID());
                    context.startActivity(i);
                }
            });
        }
    }

    private List<Instructor> mInstructors;
    private final Context context;
    private final LayoutInflater mInflater;
    public InstructorAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public InstructorAdapter.InstructorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.instructor_list_item,parent,false);
        return new InstructorViewHolder(itemView);
    }

    //this is where you put things onto the textview
    @Override
    public void onBindViewHolder(@NonNull InstructorAdapter.InstructorViewHolder holder, int position) {
        if(mInstructors != null){
            Instructor currentInstructor = mInstructors.get(position);
            String name = currentInstructor.getInstructorName();
            holder.instructorItemView.setText(name);
        }else{
            holder.instructorItemView.setText("No Instructor Title");
        }
    }

    public void setInstructors(List<Instructor> instructors) {
        mInstructors = instructors;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(mInstructors != null){
            return mInstructors.size();
        }else{
            return 0;
        }

    }












}