package com.example.studentapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.studentapplication.Entity.ClassNotes;
import com.example.studentapplication.R;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    class NoteViewHolder extends RecyclerView.ViewHolder{
        private final TextView noteItemView;
        private NoteViewHolder(View itemView){
            super(itemView);
            noteItemView = itemView.findViewById(R.id.noteTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final ClassNotes current = mNotes.get(position);
                    Intent i = new Intent(context, NoteDetails.class);
                    i.putExtra("id",current.getNoteID());
                    i.putExtra("title", current.getNoteTitle());
                    i.putExtra("body", current.getNoteBody());
                    i.putExtra("courseID", current.getCourseID());
                    context.startActivity(i);
                }
            });
        }
    }
    private List<ClassNotes> mNotes;
    private final Context context;
    private final LayoutInflater mInflater;
    public NoteAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.note_list_item,parent,false);
        return new NoteViewHolder(itemView);
    }

    //this is where you put things onto the textview
    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {
        if(mNotes != null){
            ClassNotes currentNote = mNotes.get(position);
            String name = currentNote.getNoteTitle();
            holder.noteItemView.setText(name);
        }else{
            holder.noteItemView.setText("No Note Title");
        }
    }

    public void setNotes(List<ClassNotes> notes) {
        mNotes = notes;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        if(mNotes != null){
            return mNotes.size();
        }else{
            return 0;
        }

    }
}