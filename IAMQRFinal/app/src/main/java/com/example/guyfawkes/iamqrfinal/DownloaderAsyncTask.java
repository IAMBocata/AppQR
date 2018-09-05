package com.example.guyfawkes.iamqrfinal;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by guyfawkes on 11/04/18.
 */

public class DownloaderAsyncTask extends AsyncTask<Integer, Void, String> {

    Activity activity;
    String jsonString;

    public DownloaderAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Integer... integers) {


        BufferedReader in = null;
        try {
            in = getDataFromAPI(integers[0]);

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            jsonString = response.toString();

            Log.d("json", jsonString);

            return jsonString;

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("exception:", e.getMessage());
        }

        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        User user = null;

        try {
            user = JSONParser.getUser(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(activity,UI.class);
        intent.putExtra("user", user);
        activity.startActivity(intent);
    }

    public BufferedReader getDataFromAPI(int idUser) throws IOException {

        String urlGetUser = Constants.GET_ALL_USER + idUser + Constants.GET_ALL_USER_API;
        URL url = new URL(urlGetUser);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setReadTimeout(5000);
        con.setDoOutput(true);
        con.setRequestMethod("GET");
        con.disconnect();

        return new BufferedReader(new InputStreamReader(con.getInputStream()));
    }
}
