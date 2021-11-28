package com.example.dream_routine;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecylerViewSwiper extends RecyclerView.Adapter<RecylerViewSwiper.MyViewHolder> {
    public static final String TAG = "RecyclerViewAdapter";
    //var
    private ArrayList<String> job = new ArrayList<>();
    private ArrayList<String> tasks = new ArrayList<>();
    private Context mContext;


    public RecylerViewSwiper(ArrayList<String> job, ArrayList<String> tasks, Context mContext) {
        this.job = job;
        this.tasks = tasks;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.txtjob.setText(job.get(position));
        holder.txttask.setText(tasks.get(position)+" tasks");
    }

    @Override
    public int getItemCount() {
        return job.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtjob,txttask;
        Button card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtjob = itemView.findViewById(R.id.txtjob);
            txttask = itemView.findViewById(R.id.txttask);
//            card = itemView.findViewById(R.id.cardbg);
        }
    }

}
