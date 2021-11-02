package com.example.dream_routine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity {
    // Listtodo
    ListView list;
    ArrayList<Todo> arrayList;
    TodoAdapter todoAdapter;
    ImageButton btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

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
    }
}