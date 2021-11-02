package com.example.dream_routine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;



public class SwiperAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<Swiper> modeArrayList;
    public SwiperAdapter(Context context, ArrayList<Swiper> modeArrayList) {
        this.context = context;
        this.modeArrayList = modeArrayList;
    }

    @Override
    public int getCount() {
        return modeArrayList.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //inflate layout card
        View view  = LayoutInflater.from(context).inflate(R.layout.card_item,container,false);

        //init uid view from card
        View cardbg = view.findViewById(R.id.cardbg);
        TextView txttask = view.findViewById(R.id.txttask);
        TextView txtjob = view.findViewById(R.id.txtjob);

        //get data
        Swiper model = modeArrayList.get(position);
        String job = model.getJob();
        String task = model.getTask();

        //set data
        txttask.setText(task);
        txtjob.setText(job);

        //add view to container
        container.addView(view,position);

        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View) object);
    }


}
