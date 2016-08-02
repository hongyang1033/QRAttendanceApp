package com.example.hong.firstapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.util.Log;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;


public class Insert extends AppCompatActivity {

    TextView txt;
    TextView txt1;
    TextView txt2;
    TextView txt3;
    TextView idBox;
    String dbname;
    Handler mHandler = new Handler();
    MyDBHandler dbHandler;
    String Datetime;
    String checked;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        SharedPreferences sharedPref = getSharedPreferences("userID", Context.MODE_PRIVATE);
        String ID = sharedPref.getString("ID","");
        idBox = (TextView) findViewById(R.id.idBox);
        txt = (TextView) findViewById(R.id.tx_date);
        txt1 = (TextView) findViewById(R.id.classDate);
        txt2 = (TextView) findViewById(R.id.tx_startTime);
        txt3 = (TextView) findViewById(R.id.tx_endTime);
        dbHandler = new MyDBHandler(this, null, null, 1);
        final Intent qrResult = getIntent();
        idBox.setText(ID);

        new Thread(new Runnable() {
            @Override
            public void run () {
                String CurrentString = qrResult.getStringExtra("subject");
                final String[] separated = CurrentString.split("%");

                mHandler.post(new Runnable() {
                    @Override
                    public void run () {
                        txt1.setText(separated[0]);
                        txt.setText(separated[1]);
                        txt2.setText(separated[2]);
                        txt3.setText(separated[3]);
                        dbname= separated[4];
                    }
                });
            }
        }).start();


  }




    public void sendData (View view){

        final HashMap postData = new HashMap();

        new Thread(new Runnable() {
            @Override
            public void run () {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        dateformat.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        Datetime = dateformat.format(c.getTime());
        SharedPreferences sharedPref = getSharedPreferences("userID", Context.MODE_PRIVATE);
        String Name = sharedPref.getString("Name","");
        String tableName = txt1.getText().toString() + " " +txt.getText().toString();
        idBox = (TextView) findViewById(R.id.idBox);
        postData.put("id",idBox.getText().toString());
        postData.put("dateTime",Datetime);
        postData.put("tableName", tableName);
        postData.put("dbname",dbname);
        postData.put("name", Name);




                mHandler.post(new Runnable() {
                    @Override
                    public void run () {
                    }
                });
            }
        }).start();


        PostResponseAsyncTask taskInsert = new PostResponseAsyncTask(Insert.this,
                postData, false,new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if(s.contains("success")){
                    checked = "true";
                    History h = new History(txt1.getText().toString(),txt.getText().toString(),txt2.getText().toString(),txt3.getText().toString(),Datetime,dbname,checked);
                    dbHandler.addHistory(h);
                    Toast.makeText(Insert.this, "Insert Successfully", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Insert.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    checked = "false";
                    History h = new History(txt1.getText().toString(),txt.getText().toString(),txt2.getText().toString(),txt3.getText().toString(),Datetime,dbname,checked);
                    dbHandler.addHistory(h);
                    Intent i = new Intent(Insert.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
        taskInsert.execute("http://172.18.61.128/android/insert.php");

    }

}
