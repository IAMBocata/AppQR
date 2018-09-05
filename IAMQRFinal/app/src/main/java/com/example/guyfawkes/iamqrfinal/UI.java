package com.example.guyfawkes.iamqrfinal;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UI extends AppCompatActivity {

    User user;

    int idUser;
    int idAdmin;

    TextView name, mail, wallet;
    EditText et_money;

    AddMoneyToUserAsync async;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);

        user = (User) getIntent().getSerializableExtra("user");

        //Falta cambiar la logica de como se consigue
        idAdmin = 14;

        idUser = user.getId();

        name = (TextView) findViewById(R.id.tv_name);
        name.setText(user.getName().toString());
        mail = (TextView) findViewById(R.id.tv_mail);
        mail.setText(user.getMail().toString());
        wallet = (TextView) findViewById(R.id.tv_wallet);
        wallet.setText(String.valueOf(user.getMoney()));

        async = new AddMoneyToUserAsync(this, idUser);





        //Toast.makeText(this, user.toString(), Toast.LENGTH_LONG).show();

    }

    public void addToWallet(View view){

        try {
            et_money = (EditText) findViewById(R.id.wallet_input);


                int moneyAmount = Integer.parseInt(et_money.getText().toString());

                String addMoneyToThisUser = "http://labs.iam.cat/~a16josortmar/api/addMoneyToThisUser.php?" +
                        "iduser=" + idUser + "&" +
                        "iduserapplicant=" + idAdmin + "&" +
                        "money=" + moneyAmount + "&" +
                        "API_KEY="+ Constants.API_KEY;

                et_money.setText("");

                async.execute(addMoneyToThisUser);

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Insert a value first", Toast.LENGTH_SHORT).show();
        }

    }

    public void done(View view){
        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    public void addfixedValues(View view){

        et_money = (EditText) findViewById(R.id.wallet_input);
        int et_moneyIntger = 0;
        try {
            et_moneyIntger = Integer.valueOf(et_money.getText().toString());
        }catch(NumberFormatException e){
            e.getMessage();
            et_money.setText("0");
        }
        switch (view.getId()){
            case R.id.btn_plus5:
                et_money.setText(String.valueOf(et_moneyIntger+5));
                break;

            case R.id.btn_plus10:
                et_money.setText(String.valueOf(et_moneyIntger+10));
                break;

            case R.id.btn_plus20:
                et_money.setText(String.valueOf(et_moneyIntger+20));
                break;
        }
    }
}


class AddMoneyToUserAsync extends AsyncTask<String, Void, String>{

    Activity activity;
    String jsonString;
    int id;

    public AddMoneyToUserAsync(Activity activity, int idUser) {
        this.activity = activity;
        this.id = idUser;
    }


    @Override
    protected String doInBackground(String... strings) {

        BufferedReader in = null;
        try {
            in = getDataFromAPI(strings[0]);

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

        boolean done = false;
        try {
            done = JSONParser.getMoneyRequest(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        if (done){
            Log.d("Is money added?", "Yes.");
            /*if (Build.VERSION.SDK_INT >= 11) {
                activity.recreate();
            } else {
                Intent intent = activity.getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                activity.finish();
                activity.overridePendingTransition(0, 0);

                activity.startActivity(intent);
                activity.overridePendingTransition(0, 0);
            }*/
            DownloaderAsyncTask dat = new DownloaderAsyncTask(activity);
            dat.execute(id);
            activity.finish();

        }else {
            Log.d("Is money added?", "Nope.");
        }
    }

    public BufferedReader getDataFromAPI(String urlAddMoney) throws IOException {

        URL url = new URL(urlAddMoney);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setReadTimeout(5000);
        con.setDoOutput(true);
        con.setRequestMethod("GET");
        con.disconnect();

        return new BufferedReader(new InputStreamReader(con.getInputStream()));
    }
}