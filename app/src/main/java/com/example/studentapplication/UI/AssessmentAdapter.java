package com.example.studentapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentapplication.Entity.Assessment;
import com.example.studentapplication.R;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {

    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentItemView;
        private AssessmentViewHolder(View itemView){
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.assessmentTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessments.get(position);
                    Intent i = new Intent(context, AssessmentDetails.class);//should be sent to the term details?
                    i.putExtra("assessmentID",current.getAssessmentID());
                    i.putExtra("assessmentTitle", current.getAssessmentTitle());
                    i.putExtra("assessmentStart", current.getAssessmentStartDate());
                    i.putExtra("assessmentEnd", current.getAssessmentEndDate());
                    i.putExtra("assessmentType", current.getAssessmentType());
                    i.putExtra("assessmentCourseID", current.getCourseID());
                    context.startActivity(i);
                }
            });
        }
    }
    private List<Assessment> mAssessments;
    private final Context context;
    private final LayoutInflater mInflater;
    public AssessmentAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public AssessmentAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item,parent,false);
        return new AssessmentViewHolder(itemView);
    }

    //this is where you put things onto the textview
    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if(mAssessments != null){
            Assessment currentAssessment = mAssessments.get(position);
            String name = currentAssessment.getAssessmentTitle();
            holder.assessmentItemView.setText(name);
        }else{
            holder.assessmentItemView.setText("No Term Title");
        }
    }

    public void setAssesssments(List<Assessment> assessments) {
        mAssessments = assessments;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(mAssessments != null){
            return mAssessments.size();
        }else{
            return 0;
        }

    }
}

