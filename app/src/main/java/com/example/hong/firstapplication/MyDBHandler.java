package com.example.hong.firstapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Hong on 3/23/2016.
 */
public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "history.db";
    public static final String TABLE_HISTORY = "history";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_STARTTIME = "startTime";
    public static final String COLUMN_ENDTIME = "endTime";
    public static final String COLUMN_LOGTIME = "logTime";
    public static final String COLUMN_INSTRUCTOR = "instructor";
    public static final String COLUMN_CHECKED = "checked";



    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_HISTORY + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_SUBJECT + " TEXT ," +
                COLUMN_DATE + " TEXT ," +
                COLUMN_STARTTIME + " TEXT ," +
                COLUMN_ENDTIME + " TEXT ," +
                COLUMN_LOGTIME + " TEXT ," +
                COLUMN_INSTRUCTOR + " TEXT ," +
                COLUMN_CHECKED + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + TABLE_HISTORY);
        onCreate(db);
    }

    //Add new row to database
    public void addHistory(History history){
        ContentValues values = new ContentValues();
        values.put(COLUMN_SUBJECT, history.get_subject());
        values.put(COLUMN_DATE, history.get_date());
        values.put(COLUMN_STARTTIME, history.get_startTime());
        values.put(COLUMN_ENDTIME, history.get_endTime());
        values.put(COLUMN_LOGTIME, history.get_logTime());
        values.put(COLUMN_INSTRUCTOR,history.get_instructor());
        values.put(COLUMN_CHECKED,history.get_checked());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_HISTORY, null, values);
        db.close();
    }



    public Cursor getInfo (SQLiteDatabase db){
        String [] projections ={
                COLUMN_ID,COLUMN_SUBJECT,COLUMN_DATE,COLUMN_STARTTIME,COLUMN_ENDTIME,COLUMN_LOGTIME,COLUMN_INSTRUCTOR,COLUMN_CHECKED
        };

        Cursor cursor = db.query(TABLE_HISTORY,projections,null,null,null,null,null);

        return cursor;
    }
}