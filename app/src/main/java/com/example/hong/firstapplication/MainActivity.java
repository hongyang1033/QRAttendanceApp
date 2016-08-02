package com.example.hong.firstapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.os.Handler;
import android.os.Message;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {
    TextView id;
    TextView name;
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        id = (TextView) findViewById(R.id.id);
        name = (TextView) findViewById(R.id.name);
       authenticate();


    }


    public void scanNow(View view) {
        Log.d("test", "button works!");
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(ScanActivity.class);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("");
        integrator.setBeepEnabled(false);
        integrator.initiateScan();
    }

    public void displayHistory(View view){
        Intent i = new Intent (MainActivity.this,DisplayHistory.class);
        startActivity(i);

    }

    public void activate (View view){
        SharedPreferences sharedPref = getSharedPreferences("userID", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();
        Log.d("Clear", "Clear");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {

            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();

            } else {

                Intent i = new Intent (MainActivity.this,Insert.class);
                i.putExtra("subject",result.getContents());
                startActivity(i);
                finish();
            }
        }else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.hong.firstapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.hong.firstapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private void authenticate () {
        SharedPreferences sharedPref = getSharedPreferences("userID", Context.MODE_PRIVATE);
        boolean logIn = sharedPref.getBoolean("Logged In", false);
        if (logIn){
            Log.d("True", "True");
            displayData();
        }else
        {
            Log.d("FALSE","False");
            Intent intent = new Intent(this, Activate.class);
            startActivity(intent);
            finish();
        }
    }

    private void displayData() {
        SharedPreferences sharedPref = getSharedPreferences("userID", Context.MODE_PRIVATE);
        String ID = sharedPref.getString("ID","");
        String Name = sharedPref.getString("Name","");
        id.setText(ID);
        name.setText(Name);
    }




}
