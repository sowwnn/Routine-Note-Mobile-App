package com.example.dream_routine;

import static com.example.dream_routine.RecylerViewSwiper.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecylerViewSwiper.OnItemClickListener {

    //sidebar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    //action bar
    private ActionBar actionBar;

    //RecyclerView

    private static final String TAG = "Dashboard";
    private ArrayList<String> job = new ArrayList<>();
    private ArrayList<String> tasks = new ArrayList<>();

    // Listtodo
    ListView list;
    ArrayList<Todo> arrayList;
    TodoAdapter todoAdapter;

    //Bottom Sheet
    FloatingActionButton btnnewtask;
    BottomSheetDialog bottomSheetNewTask;
    String[] tags = {"University","Business","Job"};
    AutoCompleteTextView txttag;
    ArrayAdapter<String> adapterTag;
    EditText txtdeadline;
    Calendar calendar;
    DatePickerDialog.OnDateSetListener setListener;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        list = findViewById(R.id.todolist);

        arrayList = Todo.initTodo();

        todoAdapter = new TodoAdapter(Dashboard.this, R.layout.todo_item, arrayList);

        list.setAdapter(todoAdapter);


        //sidebar
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open_menu, R.string.Close_menu);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);


        //BottomSheet
        btnnewtask = findViewById(R.id.btnnewtask);
        btnnewtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();

            }
        });

        //Recycler View
        getCardData();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return true;
    }
    public void showBottomSheetDialog(){
        bottomSheetNewTask = new BottomSheetDialog(Dashboard.this, R.style.BottomSheetStyle);
        View v = LayoutInflater.from(Dashboard.this).inflate(R.layout.bottom_sheet_new_task,findViewById(R.id.bottomsheet));
        bottomSheetNewTask.setContentView(v);
        EditText txttaskname = findViewById(R.id.txttaskname);

        bottomSheetNewTask.show();

    }


    //Recycler View
    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView =findViewById(R.id.swiperview);
        recyclerView.setLayoutManager(layoutManager);
        RecylerViewSwiper adapder = new RecylerViewSwiper( job, tasks,this,this);
        recyclerView.setAdapter(adapder);
    }

    private void getCardData(){
       job.add("Machine Learning");
       job.add("Android");
       job.add("Database Adv");


       tasks.add("10");
       tasks.add("5");
       tasks.add("7");

        initRecyclerView();
    }


    @Override
    public void onItemClick(int position) {
        job.get(position);
        Intent intent = new Intent(Dashboard.this,CalendarActivity.class);
        intent.putExtra("job",job.get(position));
        startActivity(intent);
    }
}


