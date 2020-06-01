/*
 * Date: 2020.5.27
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
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import com.chekrite_group44.R;

import java.util.ArrayList;

public class TickView extends View {
    private static final String TAG = "TickView";
    int count = 0;
    Bitmap bitmap;
    Bitmap thumb;
    Bitmap resized;
    int bitmap_width;
    int bitmap_height;
    int Width;
    int Height;
    PathMeasure pm;
    float pathLength;
    Path sPath;
    float[] pos = {0f,0f};
    int step;
    boolean last_item = false;
    ArrayList<Point> points = new ArrayList<>();
    Point current_point = new Point(0,0);
    Paint pathPaint;
    TickListener mListener;
    public TickView(Context context, TickListener listener) {
        super(context);
        Resources res = getResources();
        bitmap = BitmapFactory.decodeResource(res, R.drawable.cancel_test_logo);
        thumb = BitmapFactory.decodeResource(res, R.drawable.cancel_thumb);

        pathPaint = new Paint();
        sPath = new Path();
        mListener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Height = getHeight();
        Width = getWidth();
        // plot tick
        bitmap_width = Math.round(Width/1f);
        bitmap_height = Math.round(Height/2f);
        resized = Bitmap.createScaledBitmap(bitmap, bitmap_width, bitmap_height, true);
        float centreX = (Width  - bitmap_width) /2f;
        float centreY = (Height - bitmap_height) /2f;
        canvas.drawBitmap(resized, centreX, centreY, null);
        // define path
        sPath.moveTo(Width*0.15f, Height*0.523f);
        sPath.lineTo(Width*0.359f, Height*0.65f);
        sPath.lineTo(Width*0.868f, Height*0.333f);
        pm = new PathMeasure(sPath, false);
        pathLength = pm.getLength();
        step = 10;
        points.clear();
        for(int i = 0; i<pathLength; i+=step){
            pm.getPosTan(i, pos, null);
            Point point = new Point();
            point.x = (int)pos[0];
            point.y = (int)pos[1];
            points.add(point);
        }
        if (count==0){
            current_point.x = points.get(0).x;
            current_point.y = points.get(0).y;
            count++;
        }
        // plot thumb
        bitmap_width = Math.round(Width/7f);
        bitmap_height = Math.round(Width/7f);
        resized = Bitmap.createScaledBitmap(thumb, bitmap_width, bitmap_height, true);
        centreX = current_point.x - bitmap_width/2f;
        centreY = current_point.y - bitmap_height/2f;
        canvas.drawBitmap(resized, centreX, centreY, null);
        //

        pathPaint.setColor(Color.TRANSPARENT);
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(10);
        canvas.drawPath(sPath, pathPaint);


    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        double dist = 1000;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < points.size(); i++) {
                    Point point = points.get(i);
                    double dist_tmp = Math.sqrt(Math.pow(point.x - event.getX(),2)+
                            Math.pow(point.y - event.getY(),2));
                    if(dist_tmp <= 40){
                        if(dist_tmp < dist) {
                            // get shortest distance between path and event.x & event.y
                            dist = dist_tmp;
                            current_point.x = point.x;
                            current_point.y = point.y;
                            if(i == points.size()-1){
                                last_item = true;
                                // trigger last item
                            }else{
                                last_item = false;
                            }
                        }
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                // Action up
                // if last item, then move to the last point
                // else go back to first position
                if(last_item==true){
                    current_point.x = points.get(points.size()-1).x;
                    current_point.y = points.get(points.size()-1).y;
                    mListener.Completed();
                }else {
                    current_point.x = points.get(0).x;
                    current_point.y = points.get(0).y;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }
}

