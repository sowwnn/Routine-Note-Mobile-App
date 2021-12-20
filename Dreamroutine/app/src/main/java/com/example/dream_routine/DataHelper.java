package com.example.dream_routine;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DataHelper extends SQLiteOpenHelper {

    private static final String LOG = "DatabaseHelper";

    // database version
    private static final int DATABASE_VERSION = 1;
    // database name
    private static final String DATABASE_NAME = "db_Dream_Routine";
    // table name
    private static final String TABLE_USER = "tbl_user";
    private static final String TABLE_TASK = "tbl_task";
    // mutual column names
    private static final String KEY_ID = "_id";
    // user table
    private static final String KEY_USER_NAME = "User_name";
    private static final String KEY_NAME = "Name";
    private static final String KEY_PASS = "Password";
    private static final String KEY_EMAIL = "Email";
    // task table
    private static final String KEY_TASK_NAME = "Task_name";
    private static final String KEY_TASK_TAG = "Task_tag";
    private static final String KEY_DEADLINE = "Deadline";
    private static final String KEY_TASK_NOTE = "Task_note";
    private static final String KEY_USER_ID = "User_id";

    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_USER_NAME + " TEXT NOT NULL UNIQUE, "
            + KEY_NAME + " TEXT," + KEY_PASS + " VARCHAR(12)," + KEY_EMAIL + " TEXT )";

    private static final String CREATE_TASK_TABLE = "CREATE TABLE " + TABLE_TASK + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TASK_NAME + " TEXT,"
            + KEY_TASK_TAG + " TEXT," + KEY_DEADLINE + " DATE," + KEY_TASK_NOTE + " TEXT," + KEY_USER_ID + " INTERGER )";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_TASK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

    public void insertUser(User user) {
        // cap quyen ghi CSDL cho bien database
        SQLiteDatabase database = this.getWritableDatabase();

        // dat cac gia tri cua student can them cho bien values
        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, user.getUserName());
        values.put(KEY_NAME, user.getName());
        values.put(KEY_PASS, user.getUserPass());
        values.put(KEY_EMAIL, user.getUserEmail());

        // them vao CSDL
        database.insert(TABLE_USER, null, values);
    }

    public void insertTask(Task task) {
        // cap quyen ghi CSDL cho bien database
        SQLiteDatabase database = this.getWritableDatabase();

        // dat cac gia tri cua student can them cho bien values
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_NAME, task.getTaskName());
        values.put(KEY_TASK_TAG, task.getTaskTag());
        values.put(KEY_DEADLINE, task.getTaskDeadline());
        values.put(KEY_TASK_NOTE, task.getTaskNote());
        values.put(KEY_USER_ID, task.getUserId());

        // them vao CSDL
        database.insert(TABLE_TASK, null, values);
    }

    @SuppressLint("Range")
    public User getUser(int id) {
        // cap quyen doc CSDL cho bien database
        SQLiteDatabase database = this.getReadableDatabase();

        // gan cau lenh SQL vao bien selectQuerry
        String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + KEY_ID + " = " + id;

        // Log ra selectQuerry
        LogUtil.LogD(LOG, selectQuery);

        // doi tuong luu cac hang cua bang truy van
        Cursor c = database.rawQuery(selectQuery, null);
        User user = new User();
        // chuyen con tro den dong dau tien neu du lieu tra ve tu CSDL khong phai null
        if ((c != null) && (c.getCount() >0)){
            c.moveToFirst();

            user.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
            user.setUserName(c.getString(c.getColumnIndex(KEY_USER_NAME)));
            user.setName(c.getString(c.getColumnIndex(KEY_NAME)));
            user.setUserPass(c.getString(c.getColumnIndex(KEY_PASS)));
            user.setUserEmail(c.getString(c.getColumnIndex(KEY_EMAIL)));
        }

        return user;
    }

    @SuppressLint("Range")
    public Task getTask(int id) {
        // cap quyen doc CSDL cho bien database
        SQLiteDatabase database = this.getReadableDatabase();

        // gan cau lenh SQL vao bien selectQuerry
        String selectQuery = "SELECT * FROM " + TABLE_TASK + " WHERE " + KEY_ID + " = " + id;

        // Log ra selectQuerry
        LogUtil.LogD(LOG, selectQuery);

        // doi tuong luu cac hang cua bang truy van
        Cursor c = database.rawQuery(selectQuery, null);

        // chuyen con tro den dong dau tien neu du lieu tra ve tu CSDL khong phai null
        if (c != null) {
            c.moveToFirst();
        }

        // dong goi thong tin vao 1 doi tuong task
        Task task = new Task();
        task.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
        task.setTaskName(c.getString(c.getColumnIndex(KEY_TASK_NAME)));
        task.setTaskTag(c.getString(c.getColumnIndex(KEY_TASK_TAG)));
        task.setTaskDeadline(c.getString(c.getColumnIndex(KEY_DEADLINE)));
        task.setTaskNote(c.getString(c.getColumnIndex(KEY_TASK_NOTE)));
        task.setUserId(c.getString(c.getColumnIndex(KEY_USER_ID)));

        // tra ve 1 task
        return task;
    }

    @SuppressLint("Range")
    public ArrayList<Task> getAllTask() {
        ArrayList<Task> arrTask = new ArrayList<Task>();

        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuerry = "SELECT * FROM " + TABLE_TASK;

        LogUtil.LogD(LOG, selectQuerry);

        Cursor c = database.rawQuery(selectQuerry, null);

        if (c != null) {
            c.moveToFirst();

            do {
                // dong goi thong tin vao 1 doi tuong task
                Task task = new Task();

                task.set_id(c.getInt(c.getColumnIndex(KEY_ID)));
                task.setTaskName(c.getString(c.getColumnIndex(KEY_TASK_NAME)));
                task.setTaskTag(c.getString(c.getColumnIndex(KEY_TASK_TAG)));
                task.setTaskDeadline(c.getString(c.getColumnIndex(KEY_DEADLINE)));
                task.setTaskNote(c.getString(c.getColumnIndex(KEY_TASK_NOTE)));
                task.setUserId(c.getString(c.getColumnIndex(KEY_USER_ID)));

                arrTask.add(task);
            } while (c.moveToNext()); // chuyen toi dong tiep theo
        }

        // tra ve danh sach cac task
        return arrTask;
    }
    @SuppressLint("Range")
    public ArrayList<String> getAllTaskName() {
        ArrayList<String> arrTask = new ArrayList<String>();

        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuerry = "SELECT * FROM " + TABLE_TASK;

        LogUtil.LogD(LOG, selectQuerry);

        Cursor c = database.rawQuery(selectQuerry, null);

        if ((c != null) && (c.getCount() > 0)) {
            c.moveToFirst();

            do {
                // dong goi thong tin vao 1 doi tuong task
                String task;

                task = (c.getString(c.getColumnIndex(KEY_TASK_NAME)));

                arrTask.add(task);
            } while (c.moveToNext()); // chuyen toi dong tiep theo
        }
        else
        {
            arrTask.add("");
        }

        // tra ve danh sach cac task
        return arrTask;
    }
    @SuppressLint("Range")
    public ArrayList<String> getTaskTodo(String date,int u_id) {
        ArrayList<String> arrTask = new ArrayList<String>();

        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuerry = "SELECT * FROM " + TABLE_TASK +" WHERE Deadline = \""+date+"\"AND User_id = "+ u_id;

        LogUtil.LogD(LOG, selectQuerry);

        Cursor c = database.rawQuery(selectQuerry, null);

        if ((c != null) && (c.getCount() > 0)) {
            c.moveToFirst();

            do {
                // dong goi thong tin vao 1 doi tuong task
                String task;

                task = (c.getString(c.getColumnIndex(KEY_TASK_NAME)));

                arrTask.add(task);
            } while (c.moveToNext()); // chuyen toi dong tiep theo
        }
        else
        {
            arrTask.add("");
        }

        // tra ve danh sach cac task
        return arrTask;
    }
    @SuppressLint("Range")
    public ArrayList<String> getTaskTodoByTag(String date,String tag,int u_id) {
        ArrayList<String> arrTask = new ArrayList<String>();

        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuerry = "SELECT * FROM " + TABLE_TASK +" WHERE Deadline = \""+date+"\" AND Task_tag = \""+ tag+"\" "+"AND User_id = "+ u_id;

        LogUtil.LogD(LOG, selectQuerry);

        Cursor c = database.rawQuery(selectQuerry, null);

        if ((c != null) && (c.getCount() > 0)) {
            c.moveToFirst();
            do {
                // dong goi thong tin vao 1 doi tuong task
                String task;

                task = (c.getString(c.getColumnIndex(KEY_TASK_NAME)));

                arrTask.add(task);
            } while (c.moveToNext()); // chuyen toi dong tiep theo
        }
        else
        {
           arrTask.add("");
        }

        // tra ve danh sach cac task
        return arrTask;
    }

    public void updateTask(Task task, int _id) {
        // cap quyen ghi cho bien database
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TASK_NAME, task.getTaskName());
        values.put(KEY_TASK_TAG, task.getTaskTag());
        values.put(KEY_DEADLINE, task.getTaskDeadline());
        values.put(KEY_TASK_NOTE, task.getTaskNote());
        values.put(KEY_USER_ID, task.getUserId());

        // sua task co ID = _id theo cac thong tin trong bien values
        database.update(TABLE_TASK, values, KEY_ID + " = " + _id, null);
    }

    // xoa task co ID = _id
    public void deleteStudent(int _id) {
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(TABLE_TASK, KEY_ID + " = " + _id, null);
    }

    public boolean CheckValid(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from tbl_user where User_name = \"" + username + "\" and Password = \"" + password + "\"", null);
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public boolean CheckUser(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from tbl_user where User_name = \"" + username +"\"" , null);
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public int getUserID(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select _id from tbl_user where User_name = \"" + username + "\" and Password = \"" + password + "\"", null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public HashMap<String, Integer> getTaskTag(int u_id) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        HashMap<String,Integer> tag = new HashMap<>();

        Cursor cursor = MyDB.rawQuery("SELECT Task_tag, count(*) FROM tbl_task WHERE User_id = "+u_id+" GROUP by Task_tag", null);
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();

            do {
                String job = (cursor.getString(0));
                int tasks = (cursor.getInt(1));

                tag.put(job,tasks);

            } while (cursor.moveToNext()); // chuyen toi dong tiep theo
        }
        else
            tag.put("empty",0);
        return tag;
    }

    public ArrayList<String> getAllDay(int u_id,String tag){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ArrayList<String> allday= new ArrayList<String>();
        Cursor cursor = myDB.rawQuery("SELECT Deadline FROM tbl_task WHERE Task_tag = \""+ tag+"\" AND User_id =\""+u_id+"\"" ,null);
        if ((cursor != null) && (cursor.getCount() > 0)) {
            cursor.moveToFirst();

            do {
                String date = (cursor.getString(0));
                allday.add(date);

            } while (cursor.moveToNext()); // chuyen toi dong tiep theo
        }
        return allday;
    }
    @SuppressLint("Range")
    public ArrayList<String> getDayTaskByTag(String date, String tag, int u_id) {
        ArrayList<String> arrTask = new ArrayList<String>();

        SQLiteDatabase database = this.getReadableDatabase();
        String selectQuerry = "SELECT * FROM " + TABLE_TASK +" WHERE Deadline = \""+date+"\" AND Task_tag = \""+ tag+"\" "+"AND User_id = "+ u_id;

        LogUtil.LogD(LOG, selectQuerry);

        Cursor c = database.rawQuery(selectQuerry, null);

        if ((c != null) && (c.getCount() > 0)) {
            c.moveToFirst();
            do {
                // dong goi thong tin vao 1 doi tuong task
                String task;

                task = (c.getString(c.getColumnIndex(KEY_TASK_NAME)));

                arrTask.add(task);
            } while (c.moveToNext()); // chuyen toi dong tiep theo
        }
        else
        {
            arrTask.add("");
        }

        // tra ve danh sach cac task
        return arrTask;
    }
}
