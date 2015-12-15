/**
 * Copyright (C) 2015 DSIDEX, Inc.
 */

package org.ipfx.example.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import org.ipfx.Interpolator;

public class InterpolatorGraph extends View{

  private Interpolator interpolator = null;
  private Paint gridPaint;
  private Paint gridPaint2;
  private Paint paint;
  private Paint progressPaint;

  private float y0;
  private float y1;
  private float height;
  private float ky = 0;

  private float progress = 0;

  /**
   * minimum to use while drawing
   */

  private float valueMin = 0;

  /**
   * maximum to use while drawing
   */

  private float valueMax = 0;

  public InterpolatorGraph(Context context) {
    super(context);
    init();
  }

  public InterpolatorGraph(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public InterpolatorGraph(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init(){

    gridPaint = new Paint();
    gridPaint.setColor(0xFF666666);
    gridPaint.setStrokeWidth(1);

    gridPaint2 = new Paint();
    gridPaint2.setColor(0xFFAAAAAA);
    gridPaint2.setStrokeWidth(1);

    paint = new Paint();
    paint.setColor(0xFF0000FF);
    paint.setStrokeWidth(3);

    progressPaint = new Paint();
    progressPaint.setColor(0xFF220000);
    progressPaint.setStrokeWidth(1);

  }

  @Override
  public void onLayout(boolean changed, int l, int t, int r, int b){
    super.onLayout(changed, l, t, r, b);
    y0 = 2;
    y1 = getHeight() - 2;
    height = y1 - y0;
    if(valueMax != valueMin) {
      ky = height / (valueMax - valueMin);
    }
  }

  public void setProgress(float progress){
    this.progress = progress;
    invalidate();
  }

  public void setInterpolatorData(String data){
    interpolator = Interpolator.parseUrl(data);
    findMinMax();
    invalidate();
  }

  public Interpolator getInterpolator(){
    return interpolator;
  }

  private void findMinMax(){

    if(interpolator != null){

      // resolution at which find min, max
      int resolution = 100;

      float deltaX = 1f / (float)resolution;
      float x = 0;

      valueMin = 1000;
      valueMax = - 1000;

      for(int i = 0; i <= resolution; i ++){
        float y = interpolator.calc(x);

        if(y < valueMin){
          valueMin = y;
        }

        if(y > valueMax){
          valueMax = y;
        }

        x = x + deltaX;
      }


    }

    if(valueMax == valueMin){
      valueMax = 1;
      valueMin = -1;
    }

    ky = height / (valueMax - valueMin);

  }

  private float getScreenY(float y){
    return y0 + height - (y - valueMin) * ky;
  }

  @Override
  public void onDraw(Canvas canvas){

    if(interpolator != null){

      float x = 0;
      float deltaX = 1f / (float) getWidth();

      float points[] = new float[(getWidth() + 1) * 4];

      float y = getScreenY(interpolator.calc(0));

      points[0] = 0;
      points[1] = y;

      for(int i = 1; i < getWidth() + 1; i++){

        y = getScreenY(interpolator.calc(x));

        int index = i * 4;

        points[index - 2] = i;
        points[index -1] = y;

        points[index] = i;
        points[index + 1] = y;

        x = x + deltaX;

      }

      y = getScreenY(interpolator.calc(1));
      points[points.length - 2] = getWidth();
      points[points.length - 1] = y;

      y = getScreenY(0);
      canvas.drawLine(0, y, getWidth(), y, gridPaint);
      y = getScreenY(1);
      canvas.drawLine(0, y, getWidth(), y, gridPaint2);
      y = getScreenY(-1);
      canvas.drawLine(0, y, getWidth(), y, gridPaint2);
      canvas.drawLines(points, paint);

      if(progress > 0 && progress < 1){
        x = progress * getWidth();
        canvas.drawLine(x, 0, x, getHeight(), progressPaint);
      }

    }

  }

}