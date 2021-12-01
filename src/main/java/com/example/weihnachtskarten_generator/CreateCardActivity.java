package com.example.weihnachtskarten_generator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import top.defaults.colorpicker.ColorPickerPopup;
//import top.defaults.colorpicker.ColorPickerPopup;
//import com.groupdocs.conversion.internal.c.f.j.db.util.Converter;
//import com.groupdocs.foundation.domain.FileType;


public class CreateCardActivity extends AppCompatActivity {
    //Text
    private TextView gfgTextViewT;
    private View mColorPreviewT;
    private int mDefaultColorT;

    //Background
    private FrameLayout gfgTextViewB;
    private View mColorPreviewB;
    private int mDefaultColorB;

    Bitmap mBitmap;
    public int textColor;
    public int backgroundColor;
    public String textName;
    public Bitmap overlayBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        Button btnBack = findViewById(R.id.back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button btnSave = findViewById(R.id.saveInGalerie);
        btnSave.setOnClickListener(v -> {
            Intent resultActIntent = new Intent(CreateCardActivity.this, SaveCardActivity.class);
            startActivity(resultActIntent);
            /*try {
                save();

            } catch (IOException e) {
                Toast.makeText(this, "Couldn't save picture :(", Toast.LENGTH_LONG).show();
            }*/
        });

        //TEXTCOLOR
        textColor = ResourcesCompat.getColor(getResources(), R.color.red, null);

        //BACKGROUNDCOLOR
        backgroundColor = ResourcesCompat.getColor(getResources(), R.color.black, null);

        //TEXT
        textName = "Sarah";

        //OVERLAY
        overlayBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.overlay_1);


        chooseTextColor();
        chooseBackgroundColor();
        createDropdown();
        setNewText();
        changeOverlayDropdown();

       createCanvas(backgroundColor, textColor, overlayBitmap, textName);
    }

    private void save() throws IOException {
        String externalStorage = Environment.getExternalStorageDirectory().getAbsolutePath();
        File directory = new File(externalStorage + File.separator + Environment.DIRECTORY_PICTURES + File.separator + "WeihnachtskartenGenerator");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String path = Environment.getDataDirectory().getAbsolutePath().toString() + "/storage/emulated/0/Pictures/Weihnachtskarten";
        File mFolder = new File(path);
        if (!mFolder.exists()) {
            mFolder.mkdir();
        }


        String timeStamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
        File outputFile = new File(directory, timeStamp + ".jpeg");

        if(outputFile.exists()) {
            outputFile.delete();
        }

      /* File mydir = context.getDir("users", Context.MODE_PRIVATE); //Creating an internal dir;
        if (!mydir.exists())
        {
            mydir.mkdirs();
        }
        FileOutputStream output = new FileOutputStream(outputFile);

        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);

        output.flush();
        output.close();*/
    }

    public void chooseTextColor() {
        mColorPreviewT = findViewById(R.id.preview_selected_color_text);
        mDefaultColorT = 0;

        mColorPreviewT.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        new ColorPickerPopup.Builder(CreateCardActivity.this).initialColor(
                                Color.RED)
                                .enableBrightness(
                                        true)
                                .enableAlpha(
                                        true)
                                .okTitle(
                                        "Choose")
                                .cancelTitle(
                                        "Cancel")
                                .showIndicator(
                                        true)
                                .showValue(
                                        true)
                                .build()
                                .show(
                                        v,
                                        new ColorPickerPopup.ColorPickerObserver() {
                                            @Override
                                            public void
                                            onColorPicked(int color) {
                                                mDefaultColorT = color;
                                                mColorPreviewT.setBackgroundColor(mDefaultColorT);
                                                textColor = color;
                                                createCanvas(backgroundColor, textColor, overlayBitmap, textName);
                                            }
                                        });
                    }
                });
    }

    public void chooseBackgroundColor() {
        mColorPreviewB = findViewById(R.id.preview_selected_color_background);
        mDefaultColorB = 0;

        mColorPreviewB.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        new ColorPickerPopup.Builder(CreateCardActivity.this).initialColor(
                                Color.RED)
                                .enableBrightness(
                                        true)
                                .enableAlpha(
                                        true)
                                .okTitle(
                                        "Choose")
                                .cancelTitle(
                                        "Cancel")
                                .showIndicator(
                                        true)
                                .showValue(
                                        true)
                                .build()
                                .show(
                                        v,
                                        new ColorPickerPopup.ColorPickerObserver() {
                                            @Override
                                            public void
                                            onColorPicked(int color) {
                                                mDefaultColorB = color;
                                                mColorPreviewB.setBackgroundColor(mDefaultColorB);
                                                backgroundColor = color;
                                                createCanvas(backgroundColor, textColor, overlayBitmap, textName);
                                            }
                                        });
                    }
                });
    }

    public void setNewText() {
        EditText yourEditText = (EditText) findViewById(R.id.editText);

        yourEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                textName = yourEditText.getText().toString();
                createCanvas(backgroundColor, textColor, overlayBitmap, textName);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    public void createDropdown() {
        Spinner dropdown = findViewById(R.id.overlayDropdown);
        String[] items = new String[]{"1", "2", "3", "4", "5", "6"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }

    public void changeOverlayDropdown() {
        Spinner spinner = (Spinner)findViewById(R.id.overlayDropdown);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        String selectedItem = spinner.getSelectedItem().toString();

                        switch (selectedItem) {
                            case "1":   overlayBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.overlay_1);
                                        createCanvas(backgroundColor, textColor, overlayBitmap, textName);
                                break;
                            case "2":   overlayBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.overlay_2);
                                        createCanvas(backgroundColor, textColor, overlayBitmap, textName);
                                break;
                            case "3":   overlayBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.overlay_3);
                                        createCanvas(backgroundColor, textColor, overlayBitmap, textName);
                                break;
                            case "4":   overlayBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.overlay_4);
                                        createCanvas(backgroundColor, textColor, overlayBitmap, textName);
                                break;
                            case "5":   overlayBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.overlay_5);
                                        createCanvas(backgroundColor, textColor, overlayBitmap, textName);
                                break;
                            case "6":   overlayBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.overlay_6);
                                        createCanvas(backgroundColor, textColor, overlayBitmap, textName);
                                break;
                        }

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
    }
        public void createCanvas(int backgroundColor, int textColor, Bitmap overlayBitmap, String textName) {
            int width = 1019;
            int height = 611;

            mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas mCanvas = new Canvas(mBitmap);
            Paint mPaint = new Paint();
            mPaint.setTextSize(80);
            mPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

            //ADD GIVEN TEXT COLOR
            mPaint.setColor(textColor);

            //ADD GIVEN BACKGROUND COLOR
            mCanvas.drawColor(backgroundColor);


            //ADD GIVEN IMAGE
            Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tree);
            int origWidth1 = imageBitmap.getWidth();
            int origHeight1 = imageBitmap.getHeight();
            final int destWidth1 = 940;
            Bitmap scaledImageBitmap;

            if(origWidth1 > destWidth1){
                int destHeight = origHeight1/( origWidth1 / destWidth1 ) ;
                if(destHeight > 545){
                    destHeight = 545;
                }
                scaledImageBitmap = Bitmap.createScaledBitmap(imageBitmap, destWidth1, destHeight, false);
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                scaledImageBitmap.compress(Bitmap.CompressFormat.PNG,70 , outStream);
            } else {
                scaledImageBitmap = imageBitmap;
            }

            //ADD GIVEN OVERLAY
            int origWidth2 = overlayBitmap.getWidth();
            int origHeight2 = overlayBitmap.getHeight();
            final int destWidth2 = 750;
            Bitmap scaledOverlayBitmap;

            if(origWidth2 > destWidth2){
                int destHeight = origHeight2/( origWidth2 / destWidth2 ) ;
                if(destHeight > 575){
                    destHeight = 575;
                }
                scaledOverlayBitmap = Bitmap.createScaledBitmap(overlayBitmap, destWidth2, destHeight, false);
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                scaledOverlayBitmap.compress(Bitmap.CompressFormat.PNG,70 , outStream);
            } else {
                scaledOverlayBitmap = overlayBitmap;
            }

            mCanvas.drawBitmap(scaledImageBitmap, 40, 30, null);
            mCanvas.drawBitmap(scaledOverlayBitmap, 140, 30, null);

            //ADD GIVEN TEXT
            mCanvas.drawText("Fröhliche Weihnachten", 60, 430, mPaint);
            mCanvas.drawText("von "+ textName, 170, 530, mPaint);

            ImageView test = (ImageView) findViewById(R.id.created_card);
            test.setImageBitmap(mBitmap);
        }


}

