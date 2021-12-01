package com.example.weihnachtskarten_generator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

public class CardView extends View {

    private int backgroundColor;
    private int mDrawColor;
    private Path mPath;
    private Paint mPaint;
    private Bitmap mBitmap;
    private Canvas mCanvas;

    public CardView(Context context) {
        super(context);
    }

    public void MyCanvasView(Context context, AttributeSet attributeSet) {

        mDrawColor = ResourcesCompat.getColor(getResources(),
               R.color.white, null);
        backgroundColor = ResourcesCompat.getColor(getResources(),
                R.color.black, null);

        // Holds the path we are currently drawing.
        mPath = new Path();
        // Set up the paint with which to draw.
        Paint mPaint = new Paint();
        mPaint.setColor(backgroundColor);
        // Smoothes out edges of what is drawn without affecting shape.
        mPaint.setAntiAlias(true);
        // Dithering affects how colors with higher-precision than the device
        // are down-sampled.
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE); // default: FILL
        mPaint.setStrokeJoin(Paint.Join.ROUND); // default: MITER
        mPaint.setStrokeCap(Paint.Cap.ROUND); // default: BUTT
        mPaint.setStrokeWidth(12); // default: Hairline-width (really thin)
    }

    @Override
    protected void onSizeChanged(int width, int height,
                                 int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        // Create bitmap, create canvas with bitmap, fill canvas with color.
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(mDrawColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // First, draw the bitmap as created.
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        // Then draw the path on top, styled by mPaint.
        canvas.drawPath(mPath, mPaint);
    }
}
