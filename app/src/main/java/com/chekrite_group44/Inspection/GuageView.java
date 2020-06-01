/*
 * Date: 2020.5.10
 * This file is created by Kai.
 * Summary:
 */

package com.chekrite_group44.Inspection;

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

import com.chekrite_group44.AssetProperties.InspectionBand;
import com.chekrite_group44.AssetProperties.Inspection_gauge;
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
    int bitmap_width = 150;
    int bitmap_height = 120;
    //
    Inspection_gauge mGauge;
    int marks_count;
    int num_bands;
    float lower_step;
    float upper_step;
    String flag = "";
    //
    int y = 0;
    public GuageView(Context context, Inspection_gauge gauge) {
        super(context);
        mContext = context;
        Resources res = getResources();
        bitmap = BitmapFactory.decodeResource(res, R.drawable.thumb_gauge);
        resized = Bitmap.createScaledBitmap(bitmap, bitmap_width, bitmap_height, true);
        // initial vars
        mGauge = gauge;
        this.marks_count = gauge.getMarks_count();
        num_bands = gauge.getMarks_count();
        lower_step = gauge.getLower().floatValue();
        upper_step = gauge.getUpper().floatValue();
        // initial flag value
        switch (mGauge.getNeedle_labels()){
            case "label":
                flag = mGauge.getBands().get(0).getLabel();
                break;
            case "value":
                flag = String.valueOf(current_value);
                break;
            default:
                // status and None
                if(mGauge.getBands().get(0).getStatus() == 0){
                    flag = "Fail";
                }else if(mGauge.getBands().get(0).getStatus() == 1){
                    flag = "Pass";
                }else if(mGauge.getBands().get(0).getStatus() == 2){
                    flag = "Fixed";
                }else if(mGauge.getBands().get(0).getStatus() == 3){
                    flag = "Caution";
                }
                break;
        }

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
        Her_Line.setStrokeWidth(8);
        try {
            Log.d(TAG, "Current Val: "+current_value);
            y = range.get(current_value);
        }catch (ArrayIndexOutOfBoundsException e){
            y = range.get(0);
        }

        // draw line
        canvas.drawLine( -50, y,150,y,Her_Line);
        //draw thumb
        canvas.drawBitmap(resized, bitmap_width/1.1f, y - bitmap_height/2.0f, null);
        // draw rectangle
        Retan.setStyle(Paint.Style.STROKE);
        Retan.setColor(Color.BLACK);
        Retan.setStrokeWidth(15);
        canvas.drawRect(-200,-30 + y, -50, 30 + y, Retan);
        Retan.setStyle(Paint.Style.FILL);
        Retan.setColor(getResources().getColor(R.color.gold));
        canvas.drawRect(-200,-30 + y, -50, 30 + y, Retan);
        Retan.setColor(Color.WHITE);
        Retan.setStyle(Paint.Style.FILL);
        Retan.setTextSize(40);
        if (flag.length() <= 5)
            canvas.drawText(flag, -170, 15+y, Retan);
        else
            canvas.drawText(flag, -195, 15+y, Retan);
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
                if (current_value >= lower_step && current_value <= upper_step) {
                    setProgress(current_value);
                }else if (current_value > num_bands){
                    current_value = num_bands;
                } else{
                    current_value = (int)lower_step;
                }
                switch (mGauge.getNeedle_labels()){
                    case "label":
                        for(int i = 0; i < mGauge.getBands().size(); i++){
                            int prev = 0;
                            if(i != 0)
                                prev = mGauge.getBands().get(i-1).getUpper_step();
                            if (current_value >= prev
                                    && current_value <= mGauge.getBands().get(i).getUpper_step()){
                                flag = mGauge.getBands().get(i).getLabel();
                            }
                        }
                        break;
                    case "value":
                        flag = String.valueOf(current_value);
                        break;
                    default:
                        // status and None
                        for(int i = 0; i < mGauge.getBands().size(); i++){
                            int prev = 0;
                            if(i != 0)
                                prev = mGauge.getBands().get(i-1).getUpper_step();
                            if (current_value >= prev
                                    && current_value < mGauge.getBands().get(i).getUpper_step()){
                                if(mGauge.getBands().get(i).getStatus() == 0){
                                    flag = "Fail";
                                }else if(mGauge.getBands().get(i).getStatus() == 1){
                                    flag = "Pass";
                                }else if(mGauge.getBands().get(i).getStatus() == 2){
                                    flag = "Fixed";
                                }else if(mGauge.getBands().get(i).getStatus() == 3){
                                    flag = "Caution";
                                }
                            }
                        }
                        break;
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
        int width = (int)(Canvas_width*0.15);
        int height = Canvas_height/(num_bands+2);
        int x_offset = (Canvas_width / 2) - (width / 2);  // plot in the center
        int y_offset = 0;
        int y_height = canvas.getHeight();
        // full height
        RectF rectF = new RectF(0, 0, width, height);
        // half of height
        RectF r = new RectF(0, 0, width, Math.round(height/2.f));
        // plot from bottom and center
        canvas.translate(x_offset, y_height-(height*2)-y_offset);
        // first object coordinate
        updateY(y_height-(height*2)-y_offset);

        // start to plot
        int ibands = 0;
        int prev = 0;
        for (int i = 0; i< mGauge.getBands().size(); i++){
            if(i != 0)
                 prev = mGauge.getBands().get(i-1).getUpper_step();
            for (int j = 0; j < mGauge.getBands().get(i).getUpper_step() - prev; j ++){
                if(ibands == 0){
                    // first item
                    // plot first half of oval
                    InspectionBand band = mGauge.getBands().get(i);
                    // set up color
                    FullPaint.setColor(Color.parseColor(band.getColor()));
                    // half circle
                    canvas.drawArc(rectF, 0, 180, true, FullPaint);
                    canvas.drawArc(rectF, 0, 180, false, Stroke);
                    // half triangle
                    canvas.drawRect(r, FullPaint);    // fill
                    canvas.drawLine(0, 0, 0, height/2.f, Line);
                    canvas.drawLine(width, 0, width, height/2.f, Line);
                    canvas.translate(0, -height);
                    updateY(height);
                }else if (ibands == num_bands - 1){
                    // the last item
                    InspectionBand band = mGauge.getBands().get(i);
                    // set up color
                    FullPaint.setColor(Color.parseColor(band.getColor()));
                    // half triangle
                    Line.setStrokeWidth(10);
                    canvas.drawLine(0, height, width / 2.0f, height, Line);
                    Line.setStrokeWidth(5);
                    canvas.translate(0, height/2.0f);
                    canvas.drawRect(r, FullPaint);    // fill
                    canvas.drawLine(0, 0, 0, height/2.0f, Line);
                    canvas.drawLine(width, 0, width, height/2.0f, Line);
                    // half circle
                    canvas.translate(0, -height/2.0f);
                    canvas.drawArc(rectF, -180, 180, true, FullPaint);
                    canvas.drawArc(rectF, -180, 180, false, Stroke);
                    updateY(height);
                }else {
                    InspectionBand band = mGauge.getBands().get(i);
                    FullPaint.setColor(Color.parseColor(band.getColor()));
                    canvas.drawRect(rectF, FullPaint);
                    canvas.drawLine(0, 0, 0, height, Line);
                    canvas.drawLine(width, 0, width, height, Line);
                    canvas.drawLine(0, height, width / 2.0f, height, Line);
                    canvas.translate(0, -height);
                    updateY(height); // canvas start
                }
                ibands+=1;
            }
        }
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