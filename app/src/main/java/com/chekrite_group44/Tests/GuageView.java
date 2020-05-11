/*
 * Date: 2020.5.10
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Tests;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Collections;
import com.chekrite_group44.R;

public class GuageView extends androidx.appcompat.widget.AppCompatSeekBar {
    private static final String TAG = "GuageView";
    Context mContext;
    ArrayList<Integer> range = new ArrayList<>();
    Paint Her_Line = new Paint();
    Paint Retan = new Paint();
    int current_value = 0;
    int current_status = 0;
    Bitmap bitmap;
    Bitmap resized;
    int bitmap_width = 230;
    int bitmap_height = 150;
    //
    int marks_count;
    int lower_step;
    int upper_step;
    String lower_color;
    String upper_color;
    int lower_status;
    int upper_status;
    public GuageView(Context context, int marks_count, int lower_step, int upper_step,
                     String lower_color, String upper_color, int lower_status, int upper_status) {
        super(context);
        mContext = context;
        Resources res = getResources();
        bitmap = BitmapFactory.decodeResource(res, R.drawable.thumb_v1);
        resized = Bitmap.createScaledBitmap(bitmap, bitmap_width, bitmap_height, true);
        // initial vars
        this.marks_count = marks_count;
        this.lower_step = lower_step;
        this.upper_step = upper_step;
        this.lower_color = lower_color;
        this.upper_color = upper_color;
        this.lower_status = lower_status;
        this.upper_status = upper_status;
    }

    public GuageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public GuageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    public synchronized void setProgress(int progress)  // it is necessary for calling setProgress on click of a button
    {
        super.setProgress(progress);
        onSizeChanged(getWidth(), getHeight(), 0, 0);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
    }

    protected synchronized void onDraw(Canvas canvas) {
        DrawGauge(canvas);
        Her_Line.setColor(Color.BLACK);
        Her_Line.setStrokeWidth(5);
        int y = range.get(current_value);
        // draw line
        canvas.drawLine( -50, y,150,y,Her_Line);
        //draw thumb
        canvas.drawBitmap(resized, bitmap_width/3f, y - bitmap_height/2.0f - 4, null);
        // draw rectangle
        Retan.setStyle(Paint.Style.STROKE);
        Retan.setColor(Color.BLACK);
        Retan.setStrokeWidth(10);
        canvas.drawRect(-150,-25 + y, -50, 25 + y, Retan);
        Retan.setStyle(Paint.Style.FILL);
        Retan.setColor(getResources().getColor(R.color.gold));
        Retan.setStrokeWidth(5);
        canvas.drawRect(-150,-25 + y, -50, 25 + y, Retan);
        Retan.setColor(Color.WHITE);
        Retan.setStyle(Paint.Style.FILL);
        Retan.setTextSize(40);
        if (current_status == 0){
            canvas.drawText("Fail", -140, 15+y, Retan);
        }else {
            canvas.drawText("Pass", -145, 15+y, Retan);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                current_value = marks_count - (int) (marks_count * event.getY() / getHeight());
                // set bound for value

                if (current_value > lower_step) {
                    setProgress(current_value);
                    current_status = upper_status;
                }else{
                    setProgress(current_value);
                    current_status = lower_status;
                }
                onSizeChanged(getWidth(), getHeight(), 0, 0);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }
    private void DrawGauge(Canvas canvas){
        // Draw Background Gauge
        // fill
        range.clear();
        Paint LowerPaint = new Paint();
        LowerPaint.setStyle(Paint.Style.FILL);
        LowerPaint.setColor(Color.parseColor(lower_color));
        // fill
        Paint UpperPaint = new Paint();
        UpperPaint.setStyle(Paint.Style.FILL);
        UpperPaint.setColor(Color.parseColor(upper_color));
        //Stroke
        Paint Stroke = new Paint();
        Stroke.setStyle(Paint.Style.STROKE);
        Stroke.setColor(Color.BLACK);
        Stroke.setStrokeWidth(5);
        // horizontal line
        Paint Hori_Line = new Paint();
        Hori_Line.setColor(Color.BLACK);
        Hori_Line.setStrokeWidth(5);
        // vertical line
        Paint Ver_Line = new Paint();
        Ver_Line.setColor(Color.BLACK);
        Ver_Line.setStrokeWidth(5);

        int Canvas_width = canvas.getWidth();

        int width = 90;
        int height = 120;
        int y_offset = 130;
        int x_offset = (Canvas_width / 2) - (width / 2);  // plot in the center
        int y_height = canvas.getHeight();
        Log.d(TAG, "height: "+y_height);
        // plot from bottom and center
        canvas.translate(x_offset, y_height - y_offset);
        // first object coordinate
        updateY(y_height -y_offset);
        // plot first half of oval
        RectF rectF = new RectF(0, 0, width, height);
        canvas.translate(0, -height/2.0f);
        canvas.drawArc(rectF, 0, 180, true, LowerPaint);
        canvas.drawArc(rectF, 0, 180, false, Stroke);


        for (int i = 0; i< 4; i++){
            if(i == 0) {
                // first rectangle only draw half of height
                RectF r = new RectF(0, 0, width, height/2.0f);
                canvas.drawRect(r, LowerPaint);    // fill
                canvas.drawLine(0, 0, 0, height/2.0f, Hori_Line);
                canvas.drawLine(width, 0, width, height/2.0f, Hori_Line);
                updateY(height); // canvas start
            }else{
                canvas.translate(0, -height);
                canvas.drawRect(rectF, LowerPaint);
                canvas.drawLine(0, 0, 0, height, Hori_Line);
                canvas.drawLine(width, 0, width, height, Hori_Line);
                canvas.drawLine(0, height, width / 2.0f, height, Ver_Line);
                updateY(height); // canvas start
            }
        }

        for (int i = 0; i< 6; i++){
            if (i == 5) {
                // last rectangle only draw half of height
                canvas.translate(0, -height/2.0f);
                RectF r = new RectF(0, 0, width, height/2.0f);
                canvas.drawRect(r, UpperPaint);    // fill
                canvas.drawLine(0, 0, 0, height/2.0f, Her_Line);
                canvas.drawLine(width, 0, width, height/2.0f, Her_Line);
                canvas.drawLine(0, height/2.0f,
                        width/2.0f, height/2.0f, Ver_Line);

            }else {
                canvas.translate(0, -height);
                canvas.drawRect(rectF, UpperPaint);    // fill
                canvas.drawLine(0, 0, 0, height, Her_Line);
                canvas.drawLine(width, 0, width, height, Her_Line);
                canvas.drawLine(0, height, width/2.0f, height, Ver_Line);
                updateY(height); // canvas start
            }
        }
        canvas.translate(0,-height/2.0f);
        canvas.drawArc (rectF, -180, 180, true, UpperPaint);
        canvas.drawArc(rectF, -180, 180, false, Stroke);
        updateY(height); // canvas start
        
        //reset y to (layout center , 0)
        // get the last item's coordinate
        canvas.translate(0, -range.get(range.size()-1));
    }
    private void updateY(int y){
        if(range.size() == 0){
            range.add(y);
        }else{
            // descending order
            range.add(range.get(range.size()-1)-y);
        }

    }
}