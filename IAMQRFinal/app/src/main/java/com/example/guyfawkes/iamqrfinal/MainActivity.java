package com.example.guyfawkes.iamqrfinal;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    DownloaderAsyncTask dat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toast.makeText(this, usr.toString(), Toast.LENGTH_SHORT).show();
        dat = new DownloaderAsyncTask(this);
    }

    public void onScanned(View view){
        try {

            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

            startActivityForResult(intent, 0);

        } catch (Exception e) {

            Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
            Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
            startActivity(marketIntent);

        }
    }

    public void onProva(View view){
        dat.execute(1);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                String contents = data.getStringExtra("SCAN_RESULT");
                textView = (TextView) findViewById(R.id.myTV);
                textView.setText(contents.toString());

                int iduser = Integer.parseInt(contents.toString());

                dat.execute(iduser);

            }
            if(resultCode == RESULT_CANCELED){
                //handle cancel
                Toast.makeText(getApplicationContext(), "Closed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
