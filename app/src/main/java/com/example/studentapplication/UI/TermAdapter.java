package com.example.studentapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentapplication.Entity.Term;
import com.example.studentapplication.R;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {

    class TermViewHolder extends RecyclerView.ViewHolder{
        private final TextView termItemView;
        private TermViewHolder(View itemView){
            super(itemView);
            termItemView = itemView.findViewById(R.id.termTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Term current = mTerms.get(position);
                    Intent i = new Intent(context, TermDetails.class);
                    i.putExtra("id",current.getTermID());
                    i.putExtra("title", current.getTermTitle());
                    i.putExtra("start", current.getStartDate());
                    i.putExtra("end", current.getEndDate());
                    context.startActivity(i);
                }
            });
        }
    }
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;
    public TermAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.term_list_item,parent,false);
        return new TermViewHolder(itemView);
    }

    //this is where you put things onto the textview
    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(mTerms != null){
            Term currentTerm = mTerms.get(position);
            String name = currentTerm.getTermTitle();
            holder.termItemView.setText(name);
        }else{
            holder.termItemView.setText("No Term Title");
        }
    }

    public void setTerms(List<Term> terms) {
        mTerms = terms;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(mTerms != null){
            return mTerms.size();
        }else{
            return 0;
        }

    }
}
