/**
 * Copyright (C) 2015 DSIDEX, Inc.
 */

package org.ipfx;

import java.util.ArrayList;
import java.util.List;

public class Interpolator {

  private final List<Vector> points;
  private final List<Line> lines;

  private Interpolator(List<Vector> points, List<Line> lines){
    this.points = points;
    this.lines = lines;
  }

  /**
   * parse URL to get encoded points and lines data
   * it can be passed with or without host
   * it should start with '?' char
   * ex.
   * http://ipfx.org/?p=7ffffffe&l=09c287fff63d67fff
   * or
   * ?p=7ffffffe&l=09c287fff63d67fff
   * both will work
   *
   * @param url - data encoded in the url
   * @return
   */

  public static Interpolator parseUrl(String url){

    Data data = Data.parseUrl(url);

    if(data != null) {

      ArrayList<Vector> points = new ArrayList<Vector>();
      points.addAll(data.getPoints());

      ArrayList<Line> lines = new ArrayList<Line>();

      int i = 0;
      for (Data.Line l : data.getLines()) {

        Vector p1 = points.get(i);
        Vector p2 = points.get(i + 1);

        Line line = Line.create(p1, p2, l.v1, l.v2, l.f);

        if (line == null) {
          return null;
        } else {
          lines.add(line);
        }

        i++;
      }

      return new Interpolator(points, lines);

    }else{
      return null;
    }

  }

  /**
   * calculate Interpolator function
   * @param x -
   * @return - F(x) if x belongs to [0, 1], 0 otherwise
   */

  public float calc(float x){

    if(x >= 0 && x <= 1){

      for(int i = 0; i < points.size() - 1; i++){
        Vector p1 = points.get(i);
        Vector p2 = points.get(i + 1);

        if(x >= p1.x && x <= p2.x){
          return lines.get(i).calc(x - p1.x);
        }

      }

    }

    return 0;

  }

  private static class Line{

    private Function func;
    private float step;

    private Line(Function func, float step){
      this.func = func;
      this.step = step;
    }

    public static Line create(Vector p1, Vector p2, Vector v1, Vector v2, int f) {

      Function func;

      switch (f) {
        case Function.CURVE:
          func = new Curve(p1, p2, v1, v2);
          break;
        case Function.CURVE_LL:
          v1 = new Vector(0, 0);
          func = new Curve(p1, p2, v1, v2);
          break;
        case Function.CURVE_LR:
          v2 = new Vector(0, 0);
          func = new Curve(p1, p2, v1, v2);
          break;
        case Function.CURVE_T:
          v2 = Vector.sub(Vector.add(p1, v1), p2);
          func = new Curve(p1, p2, v1, v2);
          break;
        case Function.LINEAR:
          func = new Linear(p1, p2);
          break;
        case Function.HALF_SINE:
          func = new HalfSine(p1, p2, v1);
          break;
        case Function.CONSTANT:
          func = new Constant(p1);
          break;

        default:
          return null;

      }

      float dx = p2.x - p1.x;
      float step;
      if (dx > 0){
        step = 1 / dx;
      } else {
        step = 0;
      }

      return new Line(func, step);

    }

    public float calc(float x){
      return func.calc(x * step);
    }

  }

}
