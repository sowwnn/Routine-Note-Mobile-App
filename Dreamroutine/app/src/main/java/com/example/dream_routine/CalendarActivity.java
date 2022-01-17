package com.example.dream_routine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CalendarActivity extends AppCompatActivity {
    // Listtodo
    ListView list;
    ArrayList<String> arrayList;
    TodoAdapter todoAdapter;
    ImageButton btnback;
    FloatingActionButton btnnewtask;
    BottomSheetDialog bottomSheetNewTask;

    //var
    TextView title;
    ArrayList<String> drdtags = new ArrayList<String>();
    String tag;
    Date today;

    //Database
    DataHelper db;
    int id;

    //Calendar
    CompactCalendarView compactcalendar_view;
    String date;

    DatePickerDialog.OnDateSetListener setListener;
    Calendar calendar = Calendar.getInstance();
    final int year = calendar.get(Calendar.YEAR);
    final int month = calendar.get(Calendar.MONTH);
    final int day = calendar.get(Calendar.DAY_OF_MONTH);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar1);

        db = new DataHelper(getApplicationContext());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tag = bundle.getString("job");

        //var

        title = findViewById(R.id.txtcldTag);
        title.setText(tag);
        id = intent.getIntExtra("Id", 0);


        //date

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        today = new Date();
        date = formatter.format(today);




            //Calendar
        compactcalendar_view = findViewById(R.id.compactcalendar_view);
        compactcalendar_view.setUseThreeLetterAbbreviation(true);

        calendar_event();

        //List task
        list = findViewById(R.id.lvcldTask);

        refreshList(date);

        compactcalendar_view.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {

                String date = formatter.format(dateClicked);
//                month = month+1;
//                String date = day+"/"+month+"/"+year;
                Toast.makeText(CalendarActivity.this, "chon "+date+" rui do", Toast.LENGTH_SHORT).show();
                refreshdayList(date);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {

            }
        });


        //BottomSheet

        refreshdayList(date);
        btnnewtask = findViewById(R.id.btnnewtask);
        btnnewtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });
    }
    public void showBottomSheetDialog() {

        refreshdayList(date);
        bottomSheetNewTask = new BottomSheetDialog(CalendarActivity.this, R.style.BottomSheetStyle);
        View v = LayoutInflater.from(CalendarActivity.this).inflate(R.layout.bottom_sheet_new_task, findViewById(R.id.bottomsheet));
        bottomSheetNewTask.setContentView(v);

//        var
        Button btnsave = bottomSheetNewTask.findViewById(R.id.btnsave);
        EditText txttaskname = bottomSheetNewTask.findViewById(R.id.txttaskname);
        EditText txtdeadline = bottomSheetNewTask.findViewById(R.id.txtdeadline);
        Spinner sptag = bottomSheetNewTask.findViewById(R.id.sptag);
        ImageButton btncalendar = bottomSheetNewTask.findViewById(R.id.btncalendar);
        EditText txttag = bottomSheetNewTask.findViewById(R.id.txttag);
        EditText txtnote = bottomSheetNewTask.findViewById(R.id.txtnote);

        bottomSheetNewTask.show();

        ArrayAdapter<String> tagArrayAdapter = new ArrayAdapter<String>(CalendarActivity.this, android.R.layout.simple_dropdown_item_1line, drdtags) {
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                return super.getDropDownView(position, convertView, parent);
            }

            public int getCount() {
                return drdtags.size() - 1;
            }
        };
        sptag.setAdapter(tagArrayAdapter);
        sptag.setSelection(tagArrayAdapter.getCount());
        sptag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tag = sptag.getSelectedItem().toString();
                txttag.setText(tag);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });


        btncalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CalendarActivity.this, android.R.style.Theme_Holo_Light_DarkActionBar,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT ));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = day+"/"+month+"/"+year;
                txtdeadline.setText(date);
            }
        };



        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskname = txttaskname.getText().toString();
                String taskdeadline = txtdeadline.getText().toString();
                String tasknote = txtnote.getText().toString();
                String tasktag = txttag.getText().toString();
                String userid = String.valueOf(id);
                Task task = new Task(taskname, tasktag, taskdeadline, tasknote, userid);
                if (taskname.equals("") || taskdeadline.equals("") || tasktag.equals("")) {
                    Toast.makeText(getApplicationContext(), "Can't be null", Toast.LENGTH_LONG).show();
                } else {
                    db.insertTask(task);
                    Toast.makeText(getApplicationContext(), "New task: " + txttaskname.getText() + " \nin: " + tasktag + " \ndeadline: " + txtdeadline.getText(), Toast.LENGTH_LONG).show();
                    refreshdayList(date);
                    calendar_event();
                    bottomSheetNewTask.dismiss();

                }
            }
        });

    }

    public void refreshList(String date){
        arrayList = Todo.initByTag(db,date,tag,id);

        todoAdapter = new TodoAdapter(CalendarActivity.this, R.layout.todo_item, arrayList);

        list.setAdapter(todoAdapter);

        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(CalendarActivity.this,Dashboard.class);
                back.putExtra("Id",id);
                startActivity(back);
            }
        });
    }

    public void refreshdayList(String date){
        arrayList = Todo.initDayByTag(db,date,tag,id);

        todoAdapter = new TodoAdapter(CalendarActivity.this, R.layout.todo_item, arrayList);

        list.setAdapter(todoAdapter);

    }
//    public void refreshTask() {
//        HashMap<String,Integer> table = db.getTaskTag(id);
//        ArrayList<String >job = new ArrayList<>(table.keySet());
//        ArrayList<Integer>tasks = new ArrayList<>(table.values());
//
//        drdtags = new ArrayList<String>();
//        drdtags = job;
//        drdtags.add("");
//    }

    public void calendar_event(){
        ArrayList<String> allday = db.getAllDay(id,tag);
        for(String itemdate : allday){
            Date date1 = today;
            try {
                date1 = new SimpleDateFormat("dd/MM/yyyy").parse(itemdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Event ev1 = new Event(Color.rgb(252,209,41), date1.getTime(),null);
            compactcalendar_view.addEvent(ev1);
        }
    }
}