package info.androidhive.proclubDaiict;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import info.androidhive.slidingmenu.R;

/**
 * Created by NEHAL on 6/1/2015.
 */
public class Splash extends Activity {

    static Activity a;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            a = this;
            //setContentView(R.layout.splash);
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent i = new Intent("info.androidhive.proclubDaiict.MAINACTIVITY");
            startActivity(i);
            finish();
    }

    public static Activity getInstance()
    {
        return a;
    }



}
