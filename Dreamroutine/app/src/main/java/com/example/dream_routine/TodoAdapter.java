package com.example.dream_routine;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends ArrayAdapter {
    Context context;
    ArrayList<Todo> arrayList;
    int layout;

    public TodoAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Todo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrayList = objects;
        this.layout = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Todo todo = arrayList.get(position);

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
        }
        CheckBox task = convertView.findViewById(R.id.cbtask);
        task.setText(todo.getTask());

        return convertView;

    }
}
