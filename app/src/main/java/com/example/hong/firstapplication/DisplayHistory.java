package com.example.hong.firstapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class DisplayHistory extends AppCompatActivity {

    MyDBHandler dbHandler ;
    CustomAdapter historyAdapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_history);
        listView = (ListView) findViewById(R.id.display_history);
        dbHandler = new MyDBHandler(this, null, null, 1);
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = dbHandler.getInfo(db);
        historyAdapter = new CustomAdapter(this,R.layout.custom_row);
        String subject, date, startTime,endTime,logTime,instructor,checked;
        while(cursor.moveToNext())
        {
            subject = cursor.getString(cursor.getColumnIndex(MyDBHandler.COLUMN_SUBJECT));
            date = cursor.getString(cursor.getColumnIndex(MyDBHandler.COLUMN_DATE));
            startTime = cursor.getString(cursor.getColumnIndex(MyDBHandler.COLUMN_STARTTIME));
            endTime = cursor.getString(cursor.getColumnIndex(MyDBHandler.COLUMN_ENDTIME));
            logTime = cursor.getString(cursor.getColumnIndex(MyDBHandler.COLUMN_LOGTIME));
            instructor = cursor.getString(cursor.getColumnIndex(MyDBHandler.COLUMN_INSTRUCTOR));
            checked = cursor.getString(cursor.getColumnIndex(MyDBHandler.COLUMN_CHECKED));
            History history = new History(date,subject,startTime,endTime,logTime,instructor,checked);
            historyAdapter.add(history);
        }
       listView.setAdapter(historyAdapter);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               History h = (History) parent.getItemAtPosition(position);
               Toast.makeText(getApplicationContext(),h.get_logTime(),Toast.LENGTH_SHORT).show();
           }
       });
    }

}
