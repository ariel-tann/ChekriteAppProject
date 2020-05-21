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

import com.chekrite_group44.Asset_Properties.Inspection_band;
import com.chekrite_group44.Asset_Properties.Inspection_gauge;
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
    int bitmap_width = 220;
    int bitmap_height = 150;
    //
    Inspection_gauge mGauge;
    int marks_count;
    int num_bands;
    int l_width;
    int l_height;
    int lower_step;
    int upper_step;
    String lower_color;
    String upper_color;
    int lower_status;
    int upper_status;
    //
    int y = 0;
    public GuageView(Context context, Inspection_gauge gauge, int l_width, int l_height) {
        super(context);
        mContext = context;
        Resources res = getResources();
        bitmap = BitmapFactory.decodeResource(res, R.drawable.thumb_v1);
        resized = Bitmap.createScaledBitmap(bitmap, bitmap_width, bitmap_height, true);
        // initial vars
        mGauge = gauge;
        this.marks_count = gauge.getMarks_count();
        num_bands = gauge.getMarks_count();
        this.l_height = l_height;
        this.l_width = l_width;
//        this.lower_step = lower_step;
//        this.upper_step = upper_step;
//        this.lower_color = lower_color;
//        this.upper_color = upper_color;
//        this.lower_status = lower_status;
//        this.upper_status = upper_status;

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

//        Her_Line.setColor(Color.BLACK);
//        Her_Line.setStrokeWidth(5);
//        try {
//            y = range.get(current_value);
//        }catch (ArrayIndexOutOfBoundsException e){
//            Log.d(TAG, "range: "+range.toString()+" current val: "+current_value);
//            y = range.get(0);
//        }
//
//        // draw line
//        canvas.drawLine( -50, y,150,y,Her_Line);
//        //draw thumb
//        canvas.drawBitmap(resized, bitmap_width/2.5f, y - bitmap_height/2.0f - 4, null);
//        // draw rectangle
//        Retan.setStyle(Paint.Style.STROKE);
//        Retan.setColor(Color.BLACK);
//        Retan.setStrokeWidth(10);
//        canvas.drawRect(-150,-25 + y, -50, 25 + y, Retan);
//        Retan.setStyle(Paint.Style.FILL);
//        Retan.setColor(getResources().getColor(R.color.gold));
//        Retan.setStrokeWidth(5);
//        canvas.drawRect(-150,-25 + y, -50, 25 + y, Retan);
//        Retan.setColor(Color.WHITE);
//        Retan.setStyle(Paint.Style.FILL);
//        Retan.setTextSize(40);
//        if (current_status == 0){
//            canvas.drawText("Fail", -140, 15+y, Retan);
//        }else {
//            canvas.drawText("Pass", -145, 15+y, Retan);
//        }
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
//                current_value = marks_count - (int) (marks_count * event.getY() / getHeight());
//                // set bound for value
//                if (current_value >= 0 && current_value <= upper_step) {
//                    if (current_value > lower_step) {
//                        setProgress(current_value);
//                        current_status = upper_status;
//                    } else {
//                        setProgress(current_value);
//                        current_status = lower_status;
//                    }
//                }else if (current_value > upper_step){
//                    current_value = upper_step;
//                } else{
//                    current_value = 0;
//                }
//                onSizeChanged(getWidth(), getHeight(), 0, 0);
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
        //
        Paint FullPaint = new Paint();
        FullPaint.setStyle(Paint.Style.FILL);
        //Stroke
        Paint Stroke = new Paint();
        Stroke.setStyle(Paint.Style.STROKE);
        Stroke.setColor(Color.BLACK);
        Stroke.setStrokeWidth(5);
        // Line
        Paint Line = new Paint();
        Line.setColor(Color.BLACK);
        Line.setStrokeWidth(5);

        int Canvas_width = canvas.getWidth();
        int Canvas_height = canvas.getHeight();
        int y_offset = 100;
        int width = Math.round(Canvas_width*0.15f);
        int height = Math.round(Canvas_height/num_bands);
        int x_offset = (Canvas_width / 2) - (width / 2);  // plot in the center
        int y_height = canvas.getHeight();
        // full height
        RectF rectF = new RectF(0, 0, width, height);
        // half of height
        RectF r = new RectF(0, 0, width, height/2.0f);
        // plot from bottom and center
        canvas.translate(x_offset, y_height - y_offset);
        // first object coordinate
        updateY(y_height - y_offset);
        Log.d(TAG, height +" "+ y_offset +" "+ y_height);
        // start to plot
        int ibands = 0;
        for (int i = 0; i< mGauge.getBands().size(); i++){
            int prev = 0;
            if(i != 0)
                 prev = mGauge.getBands().get(i-1).getUpper_step();
            for (int j = 0; j < mGauge.getBands().get(i).getUpper_step() - prev; j ++){
                if(ibands == 0){
                    // first item
                    // plot first half of oval
                    Inspection_band band = mGauge.getBands().get(i);
                    // set up color
                    FullPaint.setColor(Color.parseColor(band.getColor()));
                    // half circle
                    canvas.drawArc(rectF, 0, 180, true, FullPaint);
                    canvas.drawArc(rectF, 0, 180, false, Stroke);
//                    canvas.translate(0, -height/2.0f);
                    // half triangle
                    canvas.drawRect(r, FullPaint);    // fill
                    canvas.drawLine(0, 0, 0, height/2.0f, Line);
                    canvas.drawLine(width, 0, width, height/2.0f, Line);
                    canvas.translate(0, -height);
                    updateY(height);
                }else if (ibands == num_bands){
//                    // the last item
//                    Inspection_band band = mGauge.getBands().get(i);
//                    // set up color
//                    FullPaint.setColor(Color.parseColor(band.getColor()));
//                    // half triangle
//                    canvas.drawRect(r, FullPaint);    // fill
//                    canvas.drawLine(0, 0, 0, height/2.0f, Line);
//                    canvas.drawLine(width, 0, width, height/2.0f, Line);
//                    canvas.translate(0, -height/2.0f);
//                    // half circle
//                    canvas.drawArc(rectF, 0, 180, true, FullPaint);
//                    canvas.drawArc(rectF, 0, 180, false, Stroke);
//                    canvas.translate(0, -height/2.0f);
//                    updateY(height);
                }else {
//                    Inspection_band band = mGauge.getBands().get(i);
//                    FullPaint.setColor(Color.parseColor(band.getColor()));
//                    canvas.drawRect(rectF, FullPaint);
//                    canvas.drawLine(0, 0, 0, height, Line);
//                    canvas.drawLine(width, 0, width, height, Line);
//                    canvas.drawLine(0, height, width / 2.0f, height, Line);
//                    canvas.translate(0, -height);
//                    updateY(height); // canvas start
                }
                ibands+=1;
            }
        }
        //reset y to (layout center , 0)
        // get the last item's coordinate
        //canvas.translate(0, -range.get(range.size()-1));
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