package com.example.dream_routine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecylerViewSwiper.OnItemClickListener ,TodoRAdapter.OnTaskListener{

    //sidebar
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    //action bar
    private ActionBar actionBar;

    //RecyclerView

    private static final String TAG = "Dashboard";
    ArrayList<String> job = new ArrayList<>();
    ArrayList<Integer> tasks = new ArrayList<>();

    // Listtodo
    RecyclerView list;
    ArrayList<String> arrayList;
    ArrayList<Task> arrayFList;
    TodoRAdapter todoAdapter;
    CheckBox cbtask;

    String date;

    ConstraintLayout rootView;

    //Bottom Sheet
    FloatingActionButton btnnewtask;
    BottomSheetDialog bottomSheetNewTask;

    ArrayList<String> drdtags = new ArrayList<String>();

    DataHelper db;
    int id;
    String tag;

    DatePickerDialog.OnDateSetListener setListener;
    Calendar calendar = Calendar.getInstance();
    final int year = calendar.get(Calendar.YEAR);
    final int month = calendar.get(Calendar.MONTH);
    final int day = calendar.get(Calendar.DAY_OF_MONTH);

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Get extend

        db = new DataHelper(getApplicationContext());

        Intent intent = getIntent();
        id = intent.getIntExtra("Id", 0);

        User user = db.getUser(id);

        TextView txthello = findViewById(R.id.txtHelloUser);
        txthello.setText("Hello! " + user.getUserName());


        //date

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        date = formatter.format(today);

        //list
        rootView = findViewById(R.id.root_view);
        list = findViewById(R.id.todolist);

        refreshTask();



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


    public void showBottomSheetDialog() {

        refreshTask();
        bottomSheetNewTask = new BottomSheetDialog(Dashboard.this, R.style.BottomSheetStyle);
        View v = LayoutInflater.from(Dashboard.this).inflate(R.layout.bottom_sheet_new_task, findViewById(R.id.bottomsheet));
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

        ArrayAdapter<String> tagArrayAdapter = new ArrayAdapter<String>(Dashboard.this, android.R.layout.simple_dropdown_item_1line, drdtags) {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(Dashboard.this, android.R.style.Theme_Holo_Light_DarkActionBar,setListener,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT ));
                datePickerDialog.show();
            }
        });
        setListener = new DatePickerDialog.OnDateSetListener() {
            String smonth;
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                if(month < 10){
                   smonth = "0"+month;
                }
                else{
                    smonth = ""+month;
                }
                String date = day+"/"+smonth+"/"+year;
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
                Task task = new Task(taskname, tasktag, taskdeadline, tasknote, userid,0,0);
                if (taskname.equals("") || taskdeadline.equals("") || tasktag.equals("")) {
                    Toast.makeText(getApplicationContext(), "Can't be null", Toast.LENGTH_LONG).show();
                } else {
                    db.insertTask(task);
                    Toast.makeText(getApplicationContext(), "New task: " + txttaskname.getText() + " \nin: " + tasktag + " \ndeadline: " + txtdeadline.getText(), Toast.LENGTH_LONG).show();
                    refreshTask();
                    getCardData();
                    bottomSheetNewTask.dismiss();
                }
            }
        });

    }


    //Recycler View
    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.swiperview);
        recyclerView.setLayoutManager(layoutManager);
        RecylerViewSwiper adapder = new RecylerViewSwiper(job, tasks, this, this);
        recyclerView.setAdapter(adapder);
    }

    private void getCardData() {
        HashMap<String,Integer> table = db.getTaskTag(id);
        job = new ArrayList<>(table.keySet());
        tasks = new ArrayList<>(table.values());
        initRecyclerView();
    }


    @Override
    public void onItemClick(int position) {
        job.get(position);
        Intent intent = new Intent(Dashboard.this, CalendarActivity.class);
        intent.putExtra("job", job.get(position));
        intent.putExtra("Id", id);
        startActivity(intent);
    }




    ///Task
    public void refreshTask() {
        arrayFList  = Todo.initFTodo(db,date,id);

        todoAdapter = new TodoRAdapter(Dashboard.this, arrayFList,this,db);

        list.setAdapter(todoAdapter);
        list.setLayoutManager(new LinearLayoutManager(this));

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(list);

        cbtask = findViewById(R.id.cbtask);

        drdtags = job;
        drdtags.add("");
    }
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int index = viewHolder.getAdapterPosition();

            Task temp = arrayFList.get(index);
            Toast.makeText(getApplicationContext(),temp.getTaskName()+"---"+temp.get_id(), Toast.LENGTH_LONG).show();
            db.moveToTrash(temp.get_id());
            arrayFList.remove(viewHolder.getAdapterPosition());
            todoAdapter.notifyDataSetChanged();

            Snackbar snackbar = Snackbar.make(rootView, "Removed",Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.undo(temp.get_id());
                    refreshTask();
                }
            });


            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }

    };

    @Override
    public void onTaskClick(int position) {
        Intent intent = new Intent(this,EditTask.class);
        intent.putExtra("task_id",arrayFList.get(position)._id);
        startActivity(intent);
    }

}


