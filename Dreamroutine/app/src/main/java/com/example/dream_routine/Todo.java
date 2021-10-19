package com.example.dream_routine;

import java.util.ArrayList;

public class Todo {
    String task;

    public Todo(String task) {
        this.task = task;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
    public static ArrayList initTodo(){
        ArrayList<Todo> arrayList = new ArrayList<>();
        arrayList.add(new Todo("Supervised Learning"));
        arrayList.add(new Todo("Unsupervised Learning"));
        arrayList.add(new Todo("Machine Learning"));
        arrayList.add(new Todo("Deep Learning"));
        return arrayList;
    }
}
