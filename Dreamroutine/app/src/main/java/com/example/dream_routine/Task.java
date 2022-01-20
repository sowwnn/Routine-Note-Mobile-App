package com.example.dream_routine;

import java.util.Date;

public class Task {
    public String taskName;
    public String taskTag;
    public String taskDeadline;
    public String taskNote;
    public String userId;
    public int _id;
    public int taskDone;
    public int taskTrash;

//    public Task(int _id, String taskName, String taskTag, String taskDeadline, String taskNote, String userId){
//        this.taskName = taskName;
//        this.taskTag = taskTag;
//        this.taskDeadline = taskDeadline;
//        this.taskNote = taskNote;
//        this.userId = userId;
//    }
    public Task(){
    }

    public Task(String taskName, String taskTag, String taskDeadline, String taskNote, String userId, int _id, int taskDone, int taskTrash) {
        this.taskName = taskName;
        this.taskTag = taskTag;
        this.taskDeadline = taskDeadline;
        this.taskNote = taskNote;
        this.userId = userId;
        this.taskDone = taskDone;
        this.taskTrash = taskTrash;
    }
    public Task(String taskName, String taskTag, String taskDeadline, String taskNote, String userId, int taskDone, int taskTrash) {
        this.taskName = taskName;
        this.taskTag = taskTag;
        this.taskDeadline = taskDeadline;
        this.taskNote = taskNote;
        this.userId = userId;
        this.taskDone = taskDone;
        this.taskTrash = taskTrash;
    }

    public int getTaskDone() {
        return taskDone;
    }

    public void setTaskDone(int taskDone) {
        this.taskDone = taskDone;
    }

    public int getTaskTrash() {
        return taskTrash;
    }

    public void setTaskTrash(int taskTrash) {
        this.taskTrash = taskTrash;
    }


    public Task(String taskName, String taskTag, String taskDeadline, String taskNote, String userId){
        this.taskName = taskName;
        this.taskTag = taskTag;
        this.taskDeadline = taskDeadline;
        this.taskNote = taskNote;
        this.userId = userId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskTag() {
        return taskTag;
    }

    public void setTaskTag(String taskTag) {
        this.taskTag = taskTag;
    }

    public String getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(String taskDeadline) {
        this.taskDeadline = taskDeadline;
    }

    public String getTaskNote() {
        return taskNote;
    }

    public void setTaskNote(String taskNote) {
        this.taskNote = taskNote;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
