package com.example.dream_routine;

import android.content.Context;
import android.provider.ContactsContract;
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
    private ArrayList<Integer> tasks = new ArrayList<>();

    private Context mContext;
    private OnItemClickListener itemlistener;




    public RecylerViewSwiper(ArrayList<String> job, ArrayList<Integer> tasks, Context mContext, OnItemClickListener itemlistener) {
        this.job = job;
        this.tasks = tasks;
        this.mContext = mContext;
        this.itemlistener = itemlistener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);

        return new MyViewHolder(view, itemlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.txtjob.setText(job.get(position));
        holder.txttask.setText(tasks.get(position).toString()+" tasks");
    }

    @Override
    public int getItemCount() {
        return job.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtjob,txttask;
        Button card;
        OnItemClickListener onItemClickListener;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);

            txtjob = itemView.findViewById(R.id.txtjob);
            txttask = itemView.findViewById(R.id.txttask);

            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
//            card = itemView.findViewById(R.id.cardbg);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }



    public interface OnItemClickListener{
        void onItemClick(int position);
    }

}
