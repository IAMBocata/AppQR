package com.example.guyfawkes.iamqrfinal;

import android.app.Activity;
import android.content.Intent;
import android.net.MailTo;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {
    //admin@iam.cat
    TextInputEditText mail;
    //CCXFU6kz
    TextInputEditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mail = (TextInputEditText) findViewById(R.id.laEtMail);
        password = (TextInputEditText) findViewById(R.id.laEtPassword);

    }

    public void onClickLogin(View view) {

        String sMail = mail.getText().toString();
        String sPassword = password.getText().toString();

        if (validateMail(sMail)) {
            password.setText("");

            LoginAsync loginAsync = new LoginAsync(sMail.trim(), sPassword.trim(), this);
            loginAsync.execute();
        } else {
            Toast.makeText(this, "Invalid Mail", Toast.LENGTH_SHORT).show();
        }
        
    }

    private boolean validateMail(String mail) {
        return Patterns.EMAIL_ADDRESS.matcher(mail).matches();
    }

    class LoginAsync extends AsyncTask<String, Void, Boolean> {

        private String mail;
        private String password;
        Activity activity;

        public LoginAsync(String mail, String password, Activity activity) {
            this.mail = mail;
            this.password = password;
            this.activity = activity;
        }

        @Override
        protected Boolean doInBackground(String... strings) {

            try {
                BufferedReader in = getDataFromAPI(Constants.URL_SERVER +
                        Constants.LOGIN_PATH + "?mail=" + mail + "&passwd=" + password +
                        "&API_KEY=" + Constants.API_KEY);

                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                String jsonString = response.toString();

                return JSONParser.parseLogin(jsonString);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            if (aBoolean) {
                startActivity(new Intent(activity, MainActivity.class));
            } else {
                Toast.makeText(activity, "Failako", Toast.LENGTH_SHORT).show();
            }
        }

        public BufferedReader getDataFromAPI(String urlLogin) throws IOException {

            URL url = new URL(urlLogin);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setReadTimeout(5000);
            con.setDoOutput(true);
            con.setRequestMethod("GET");
            con.disconnect();

            return new BufferedReader(new InputStreamReader(con.getInputStream()));
        }
    }
}
