package com.example.dream_routine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TodoAdapter extends ArrayAdapter {
    Context context;
    ArrayList<String> arrayList;
    int layout;

    public TodoAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrayList = objects;
        this.layout = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String todo = arrayList.get(position);

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
        }
        CheckBox task = convertView.findViewById(R.id.cbtask);
        task.setText(todo);

        return convertView;

    }
}
