package com.example.dream_routine;

import java.util.ArrayList;
import java.util.Date;

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

    public static ArrayList<String> initTodo(DataHelper db,String date,int u_id){

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList = db.getTaskTodo(date,u_id);
        return arrayList;
    }
    public static ArrayList<String> initAll(DataHelper db){

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList = db.getAllTaskName();
        return arrayList;
    }
    public static ArrayList<String> initByTag(DataHelper db,String date,String tag,int u_id){

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList = db.getTaskTodoByTag(date,tag,u_id);
        return arrayList;
    }
    public static ArrayList<String> initDayByTag(DataHelper db,String date,String tag,int u_id){

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList = db.getDayTaskByTag(date,tag,u_id);
        return arrayList;
    }
}
