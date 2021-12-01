package com.example.weihnachtskarten_generator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveCardActivity extends AppCompatActivity {

    private TextView nameInput;
    private Button drawAction;
    private ImageView preview;
    private Button saveAction;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_card);

        nameInput = findViewById(R.id.nameInput);
        preview = findViewById(R.id.preview);
        drawAction = findViewById(R.id.drawAction);
        saveAction = findViewById(R.id.saveAction);

        drawAction.setOnClickListener(v -> {
            draw();
        });

        saveAction.setOnClickListener(v -> {
            try {
                save();
            } catch (IOException e) {
                Toast.makeText(this, "Couldn't save picture :(", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void draw() {
        bitmap = Bitmap.createBitmap(800, 600, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(72);
        canvas.drawText(nameInput.getText().toString(), 400, 300, paint);

        preview.setImageBitmap(bitmap);
        saveAction.setEnabled(true);
    }

    private void save() throws IOException {
        String externalStorage = Environment.getExternalStorageDirectory().getAbsolutePath();
        File directory = new File(externalStorage + File.pathSeparator + Environment.DIRECTORY_PICTURES + File.pathSeparator + "PaintAPicture");
        directory.mkdir();
        String timeStamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        File outputFile = new File(directory, timeStamp + ".jpg");

        if(outputFile.exists()) {
            outputFile.delete();
        }

   boolean check;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                check = true;
            } else {
                check =  false;
            }

        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "testingtesting");
        if (!file.mkdirs()) {
            System.out.println("failed");
        }

        FileOutputStream output = new FileOutputStream(outputFile);

        bitmap.compress(Bitmap.CompressFormat.WEBP, 100, output);

        output.flush();
        output.close();
    }
}