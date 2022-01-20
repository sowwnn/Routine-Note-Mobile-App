package com.example.dream_routine;

import static com.example.dream_routine.RecylerViewSwiper.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoRAdapter extends RecyclerView.Adapter<TodoRAdapter.ViewHolder> {
    Context context;
    OnTaskListener monTaskListener;
    DataHelper db;
    ArrayList<Task> arrayList;


    public TodoRAdapter(Context context, ArrayList<Task> arrayList, OnTaskListener onTaskListener, DataHelper db) {
        this.context = context;
        this.arrayList = arrayList;
        this.monTaskListener = onTaskListener;
        this.db = db;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        CheckBox cbtask;
        TextView taskname;
        OnTaskListener onTaskListener;

        public ViewHolder(@NonNull View itemView, OnTaskListener onTaskListener) {
            super(itemView);
            cbtask = itemView.findViewById(R.id.cbtask);
            taskname = itemView.findViewById(R.id.taskname);

            this.onTaskListener = onTaskListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onTaskListener.onTaskClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public TodoRAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item,parent,false);
        return new TodoRAdapter.ViewHolder(view,monTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoRAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.taskname.setText(arrayList.get(position).getTaskName());

        if(arrayList.get(position).getTaskDone() == 1){
            holder.cbtask.setChecked(false);
            holder.taskname.setPaintFlags(0);
        }
        else{holder.cbtask.setChecked(true);holder.taskname.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);}


        holder.cbtask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true){
                    db.TaskDone(arrayList.get(position).get_id());
                    holder.taskname.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                }
                else{
                    db.TaskNotDone(arrayList.get(position).get_id());
                    holder.taskname.setPaintFlags(0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public interface OnTaskListener{
        void onTaskClick(int position);
    }


}
