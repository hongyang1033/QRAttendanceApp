package com.example.hong.firstapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;
import android.provider.Settings.Secure;

import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.HashMap;


public class Activate extends AppCompatActivity {

    EditText id;
    EditText name;
    String deviceID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate);
        name = (EditText) findViewById(R.id.name);
        id = (EditText) findViewById(R.id.id);
        deviceID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);

    }

    public void onClick (View view){

        final String userID = id.getText().toString();
        final String userName = name.getText().toString();
        if (TextUtils.isEmpty(userID)){
            id.setError("Please insert your ID");
        } else if (TextUtils.isEmpty(userName)) {
            name.setError("Please insert your name");
        }else{
            new AlertDialog.Builder(this)
                    .setMessage("Confirm Your ID: " + userID)
                    .setNegativeButton(android.R.string.cancel, null) // dismisses by default
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) {
                            HashMap postData = new HashMap();
                            postData.put("ID", userID);
                            postData.put("DeviceID", deviceID);
                            postData.put("Username",userName);
                            PostResponseAsyncTask activate = new PostResponseAsyncTask(Activate.this,
                                    postData, false,new AsyncResponse() {
                                @Override
                                public void processFinish(String s) {
                                    if(s.contains("success")){
                                        SharedPreferences sharedPref = getSharedPreferences("userID", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putString("ID", userID);
                                        editor.putBoolean("Logged In", true);
                                        editor.putString("Name",userName);
                                        editor.apply();
                                        Intent i = new Intent(Activate.this,MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                    else
                                    {
                                        Intent i = new Intent(Activate.this,MainActivity.class);
                                        startActivity(i);
                                        Toast.makeText(Activate.this, "Failed: Device or ID have been registered", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            activate.execute("http://172.31.32.94/android/register.php");
                        }
                    })
                    .create()
                    .show();

        }

    }



}
