package com.example.dream_routine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity {
    // Listtodo
    ListView list;
    ArrayList<Todo> arrayList;
    TodoAdapter todoAdapter;
    ImageButton btnback;
    FloatingActionButton btnnewtask;
    BottomSheetDialog bottomSheetNewTask;

    //var
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        //var
        title = findViewById(R.id.txtcldTag);
        title.setText(bundle.getString("job"));

        //List task
        list = findViewById(R.id.lvcldTask);

        arrayList = Todo.initTodo();

        todoAdapter = new TodoAdapter(CalendarActivity.this, R.layout.todo_item, arrayList);

        list.setAdapter(todoAdapter);

        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(CalendarActivity.this,Dashboard.class);
                startActivity(back);
            }
        });


        //BottomSheet
        btnnewtask = findViewById(R.id.btnnewtask);
        btnnewtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });
    }
    private void showBottomSheetDialog(){
        bottomSheetNewTask = new BottomSheetDialog(CalendarActivity.this, R.style.BottomSheetStyle);
        View v = LayoutInflater.from(CalendarActivity.this).inflate(R.layout.bottom_sheet_new_task,findViewById(R.id.bottomsheet));
        bottomSheetNewTask.setContentView(v);

        bottomSheetNewTask.show();
    }
}