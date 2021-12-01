package com.example.weihnachtskarten_generator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//import com.groupdocs.conversion.internal.c.f.j.db.util.Converter;


public class RandomPictureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_picture);

        Button btnBack = findViewById(R.id.back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button btnConfirm = findViewById(R.id.confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultActIntent = new Intent(RandomPictureActivity.this, CreateCardActivity.class);

                /*EditText gewicht = (EditText) findViewById(R.id.gewicht);
                String messageGewicht = gewicht.getText().toString();

                EditText groesse = (EditText) findViewById(R.id.groesse);
                String messageGroesse = groesse.getText().toString();

                resultActIntent.putExtra(EXTRA_MESSAGE_GEWICHT, messageGewicht);
                resultActIntent.putExtra(EXTRA_MESSAGE_GROESSE, messageGroesse);*/
                startActivity(resultActIntent);
            }
        });

        Button btnNewRandomPicture = findViewById(R.id.getNewPicture);
        btnNewRandomPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });
    }
}