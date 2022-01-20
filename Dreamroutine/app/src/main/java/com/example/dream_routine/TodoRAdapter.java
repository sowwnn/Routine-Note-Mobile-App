package com.example.dream_routine;

import static com.example.dream_routine.RecylerViewSwiper.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoRAdapter extends RecyclerView.Adapter<TodoRAdapter.ViewHolder> {
    Context context;
    OnTaskListener monTaskListener;

    ArrayList<Task> arrayList;


    public TodoRAdapter(Context context, ArrayList<Task> arrayList, OnTaskListener onTaskListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.monTaskListener = onTaskListener;
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
    public void onBindViewHolder(@NonNull TodoRAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.taskname.setText(arrayList.get(position).getTaskName());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public interface OnTaskListener{
        void onTaskClick(int position);
    }

}
