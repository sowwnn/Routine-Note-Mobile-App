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

    public static ArrayList<String> initTodo(DataHelper db){

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList = db.getAllTaskName();
        return arrayList;
    }
}
