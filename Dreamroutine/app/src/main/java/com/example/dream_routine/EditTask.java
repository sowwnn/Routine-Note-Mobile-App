package com.example.dream_routine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;

public class EditTask extends AppCompatActivity {

    DataHelper db;
    int u_id;
    int _id;

    //Task
    EditText taskName, deadLine, tag, note;
    Spinner sptags;
    Button btnsave, btncalendar;
    Task task;
    CheckBox cbDone, cbTrash;

    //Calendar
    DatePickerDialog.OnDateSetListener setListener;
    Calendar calendar = Calendar.getInstance();
    final int year = calendar.get(Calendar.YEAR);
    final int month = calendar.get(Calendar.MONTH);
    final int day = calendar.get(Calendar.DAY_OF_MONTH);

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        db = new DataHelper(getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        u_id = bundle.getInt("u_id", 0);
        _id = bundle.getInt("task_id", 0);

        taskName = findViewById(R.id.edit_taskname);
        deadLine = findViewById(R.id.edit_txtdeadline);
        tag = findViewById(R.id.edit_txttag);
        note = findViewById(R.id.edit_txtnote);

        sptags = findViewById(R.id.sptag);

        btnsave = findViewById(R.id.btnsave);
//        btncalendar = findViewById(R.id.btncalendar);

        cbDone = findViewById(R.id.cbDone);
        cbTrash = findViewById(R.id.cbTrash);

        task = db.getTaskbyID(_id, u_id);


        //Set value

        taskName.setText(task.getTaskName());
        deadLine.setText(task.getTaskDeadline());
        tag.setText(task.getTaskTag());
        note.setText(task.getTaskNote());
        if (task.getTaskDone() == 1) {
            cbDone.setChecked(false);
        } else {
            cbDone.setChecked(true);
        }

        if (task.getTaskTrash() == 1) {
            cbTrash.setChecked(false);
        } else {
            cbTrash.setChecked(true);
        }

        //Save
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskname = taskName.getText().toString();
                String taskdeadline = deadLine.getText().toString();
                String tasknote = note.getText().toString();
                String tasktag = tag.getText().toString();
                String userid = String.valueOf(_id);
                int done,trash;
                if (cbDone.isChecked() == true){
                    done = 0;
                }
                else done = 1;
                if (cbTrash.isChecked() == true){
                    trash = 0;
                }
                else trash = 1;
                Task task = new Task(taskname, tasktag, taskdeadline, tasknote, userid,done,trash);
                db.updateTask(task,_id);
            }
        });
    }



}
