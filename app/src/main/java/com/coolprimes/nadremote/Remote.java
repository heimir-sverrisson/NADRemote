package com.coolprimes.nadremote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Remote extends AppCompatActivity {
    private String base_url = null;
    private static String TAG_NADREMOTE = "NAD_RemoteControl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        base_url = sharedPref.getString(SettingsFragment.KEY_BASE_URL, "");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    private void submitURI(String URI){
        Log.d(TAG_NADREMOTE, "Submitting: " + URI);
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(URI);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream response = urlConnection.getInputStream();
        } catch (Exception e){
            final String message = e.getLocalizedMessage();
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            Log.d(TAG_NADREMOTE, "Exception: " + e);
        } finally {
            urlConnection.disconnect();
        }
    }

    public void onClickOn(View v){
        submitURI(base_url + "/cgi-bin/nad_on.sh");
    }

    public void onClickOff(View v){
        submitURI(base_url + "/cgi-bin/nad_off.sh");
    }

    public void onClickVolumeUp(View v){
        submitURI(base_url + "/cgi-bin/nad_vol_up.sh");
    }

    public void onClickVolumeDown(View v){
        submitURI(base_url + "/cgi-bin/nad_vol_down.sh");
    }

    public void onClickSourceUp(View v){
        submitURI(base_url + "/cgi-bin/nad_source_up.sh");
    }

    public void onClickSourceDown(View v){
        submitURI(base_url + "/cgi-bin/nad_source_down.sh");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_remote, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
