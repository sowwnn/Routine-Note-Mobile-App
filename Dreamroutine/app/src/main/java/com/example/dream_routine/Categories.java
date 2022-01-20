package com.example.dream_routine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;

public class Categories extends AppCompatActivity implements TodoRAdapter.OnTaskListener{
    ArrayList<Task> arrayFList;
    TodoRAdapter todoAdapter;
    ConstraintLayout rootView;
    RecyclerView trashlist;


    DataHelper db;
    int id = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);

        db = new DataHelper(getApplicationContext());

//        btncatback = findViewById(R.id.btncatback);
//        btncatback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent back = new Intent(Categories.this,Dashboard.class);
//                back.putExtra("Id",id);
//                startActivity(back);
//            }
//        });

        rootView = findViewById(R.id.root_view_3);
        trashlist = findViewById(R.id.trashList);

        refreshtrashList();


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
            //Toast.makeText(getApplicationContext(),temp.getTaskName()+"---"+temp.get_id(), Toast.LENGTH_LONG).show();
            db.undo(temp.get_id());
            arrayFList.remove(viewHolder.getAdapterPosition());
            todoAdapter.notifyDataSetChanged();

            Snackbar snackbar = Snackbar.make(rootView, "Restore",Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.moveToTrash(temp.get_id());
                    refreshtrashList();
                }
            });


            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }

    };
    public void refreshtrashList(){
        arrayFList = Todo.initAllTrash(db,id);

        todoAdapter = new TodoRAdapter(Categories.this, arrayFList, this, db);

        trashlist.setAdapter(todoAdapter);

        trashlist.setLayoutManager(new LinearLayoutManager(this));

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(trashlist);
    }

    @Override
    public void onTaskClick(int position) {

    }
}