package com.example.dream_routine;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    ArrayList<String> drdtags = new ArrayList<String>();

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

        drdtags.add("Machine Learning");
        drdtags.add("Android");
        drdtags.add("Database Adv");

        btnnewtask = findViewById(R.id.btnnewtask);
        btnnewtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });
    }
    public void showBottomSheetDialog(){
        bottomSheetNewTask = new BottomSheetDialog(CalendarActivity.this, R.style.BottomSheetStyle);
        View v = LayoutInflater.from(CalendarActivity.this).inflate(R.layout.bottom_sheet_new_task,findViewById(R.id.bottomsheet));
        bottomSheetNewTask.setContentView(v);

//        var
        Button btnsave = bottomSheetNewTask.findViewById(R.id.btnsave);
        EditText txttaskname = bottomSheetNewTask.findViewById(R.id.txttaskname);
        EditText txtdeadline = bottomSheetNewTask.findViewById(R.id.txtdeadline);
        Spinner sptag = bottomSheetNewTask.findViewById(R.id.sptag);
        EditText txttag = bottomSheetNewTask.findViewById(R.id.txttag);
        EditText txtnote = bottomSheetNewTask.findViewById(R.id.txtnote);

        bottomSheetNewTask.show();

        sptag.setAdapter(new ArrayAdapter<String>(CalendarActivity.this, android.R.layout.simple_dropdown_item_1line, drdtags));
        String tag = sptag.getSelectedItem().toString();

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"New task: "+ txttaskname.getText()+" \nin: "+tag+" \ndeadline: "+txtdeadline.getText(),Toast.LENGTH_LONG).show();
                bottomSheetNewTask.dismiss();
            }
        });

    }

}