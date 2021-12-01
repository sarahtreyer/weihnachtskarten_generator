package com.example.weihnachtskarten_generator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private String GET_URL = "https://source.unsplash.com/1600x900/?christmas";
    private String USER_AGENT = "Mozilla/5.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnGenerateAct = findViewById(R.id.newCard);
        btnGenerateAct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    requestGET();
                    int threeee = 2;
                } catch (IOException e) {
                    e.printStackTrace();
                    int threeee = 5;
                }
                Intent resultActIntent = new Intent(MainActivity.this, RandomPictureActivity.class);
                startActivity(resultActIntent);
            }
        });

    }

    public void requestGET() throws IOException {
        try {
            URL url = new URL("https://source.unsplash.com/1600x900/?christmas");
            InputStream in = new BufferedInputStream(url.openStream());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;
            while (-1 != (n = in.read(buf))) {
                out.write(buf, 0, n);
            }
            out.close();
            in.close();
            byte[] response = out.toByteArray();


            FileOutputStream fos = new FileOutputStream("randompicture.jpg");
            fos.write(response);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}