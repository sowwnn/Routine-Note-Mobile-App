package com.example.dream_routine;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.ViewParent;
import android.widget.ListView;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    //action bar
    private ActionBar actionBar;

    //UI views
    private ViewPager viewPager;

    private ArrayList<Swiper> modeArrayList;
    private SwiperAdapter swiperAdapter;

    // List todo
    ListView list;
    ArrayList<Todo> arrayList;
    TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //init actionbar
        actionBar = getSupportActionBar();

        //init UI views
        viewPager = findViewById(R.id.swiperview);
        loadCards();

        //set view pager change listiner
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        list = findViewById(R.id.todolist);

        arrayList = Todo.initTodo();

        todoAdapter = new TodoAdapter(Dashboard.this,R.layout.todo_item,arrayList);

        list.setAdapter(todoAdapter);

    }

    private void loadCards() {
        //init list
        modeArrayList = new ArrayList<>();

        //add items to list
        modeArrayList.add(new Swiper(
                "10 task",
                "University"
        ));
        modeArrayList.add(new Swiper(
                "5 task",
                "Bussiness"
        ));
        modeArrayList.add(new Swiper(
                "10 task",
                "Community"
        ));

        //setup adapter
        swiperAdapter = new SwiperAdapter(this, modeArrayList);

        //set adapter to view pager
        viewPager.setAdapter(swiperAdapter);
        //set default padding from left/right
        viewPager.setPadding(100,0,100,0);
    }

}