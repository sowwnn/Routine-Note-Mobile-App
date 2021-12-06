package com.example.dream_routine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;

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

    ArrayList<String> drdtags = new ArrayList<String>();

    DataHelper db;
    int id;
    String tag;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        list = findViewById(R.id.todolist);

        arrayList = Todo.initTodo();

        todoAdapter = new TodoAdapter(Dashboard.this, R.layout.todo_item, arrayList);

        list.setAdapter(todoAdapter);

        db = new DataHelper(getApplicationContext());

        Intent intent = getIntent();
        id = intent.getIntExtra("Id", 0);

        User user = db.getUser(id);

        TextView txthello = findViewById(R.id.txtHelloUser);
        txthello.setText("Hello! " + user.getUserName());

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

        drdtags.add("Machine Learning");
        drdtags.add("Android");
        drdtags.add("Database Adv");
        drdtags.add("");
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

//        var
        Button btnsave = bottomSheetNewTask.findViewById(R.id.btnsave);
        EditText txttaskname = bottomSheetNewTask.findViewById(R.id.txttaskname);
        EditText txtdeadline = bottomSheetNewTask.findViewById(R.id.txtdeadline);
        Spinner sptag = bottomSheetNewTask.findViewById(R.id.sptag);
        EditText txttag = bottomSheetNewTask.findViewById(R.id.txttag);
        EditText txtnote = bottomSheetNewTask.findViewById(R.id.txtnote);

        bottomSheetNewTask.show();

        ArrayAdapter<String> tagArrayAdapter = new ArrayAdapter<String>(Dashboard.this, android.R.layout.simple_dropdown_item_1line, drdtags){
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


        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskname = txttaskname.getText().toString();
                String taskdeadline = txtdeadline.getText().toString();
                String tasknote = txtnote.getText().toString();
                String tasktag = txttag.getText().toString();
                String userid = String.valueOf(id);
                Task task = new Task(taskname, tasktag, taskdeadline, tasknote, userid);
                db.insertTask(task);
                Toast.makeText(getApplicationContext(),"New task: "+ txttaskname.getText()+" \nin: "+tasktag+" \ndeadline: "+txtdeadline.getText(),Toast.LENGTH_LONG).show();
                bottomSheetNewTask.dismiss();
            }
        });

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
        intent.putExtra("Id",id);
        startActivity(intent);
    }
}


