package com.example.dream_routine;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDataBase extends SQLiteOpenHelper{

     private Context context;
     private static final String DATABASE_NAME = "Dream_Routine";
     private static final int DATABASE_VERSION = 1;

     private static final String TABLE_NAME= "my_task";
     private static final String COLUMN_ID= "_id";
     private static final String COLUMN_TASK= "task";
     private static final String COLUMN_TAG= "tag";
     private static final String COLUMN_DEADLINE= "deadline";
     private static final String COLUMN_NOTE= "note";


    public MyDataBase(@Nullable Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db ) {
        String query = "CREATE TABLE " + TABLE_NAME +
                "("+ COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TASK+"TEXT,"+
                COLUMN_TAG+" TEXT,"+
                COLUMN_DEADLINE+" DATE,"
                +COLUMN_NOTE+" TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
}
