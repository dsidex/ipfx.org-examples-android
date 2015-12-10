/**
 * Copyright (C) 2015 DSIDEX, Inc.
 */

package org.ipfx;

import java.util.LinkedList;
import java.util.List;

public class Data {

  private static final float GRID_X = 65535.0f;
  private static final float GRID_Y = 32767.0f;
  private static final float GRID_Y0 = 32767.0f;
  private static final float GRID_VX = 21844.0f;
  private static final float GRID_VY = 10922.0f;

  private List<Vector> points = null;
  private List<Line> lines = null;

  private Data(List<Vector> points, List<Line> lines){
    this.points = points;
    this.lines = lines;
  }

  public List<Vector> getPoints(){
    return points;
  }

  public List<Line> getLines(){
    return lines;
  }

  public static Data parseUrl(String url){
    int pdi = url.indexOf("?p=");
    int ldi = url.indexOf("&l=");

    if (ldi < pdi) {
      return null;
    }

    if(pdi == -1 && ldi == -1){
      return createBase();
    }

    String pdata = url.substring(pdi + 3, ldi);
    String ldata = url.substring(ldi + 3);

    if (checkData(pdata, ldata)) {
      List<Vector> points = getPointsData(pdata);
      List<Line> lines = getLinesData(ldata, points);
      return new Data(points, lines);
    }

    return null;
  }

  private static Data createBase(){

    List<Vector> points = new LinkedList<Vector>();
    List<Line> lines = new LinkedList<Line>();

    points.add(new Vector(0.0f, 0.0f));
    points.add(new Vector(1.0f, 0.0f));

    lines.add(new Line(new Vector(0.33f, 0), new Vector(-0.33f, 0), Function.CURVE));

    return new Data(points, lines);

  }

  private static boolean checkData(String pdata, String ldata){

    if(checkDataAlphabet(pdata) && checkDataAlphabet(ldata)){

      if(pdata.length() % 8 != 0){
        return false;
      }

      int pc = (int)Math.floor(pdata.length() / 8) + 1;

      Caret caret = new Caret(ldata, 0);

      int i = 0;
      while (i < pc - 1) {

        int f = readHexValue(caret, 1);

        switch (f) {
          case Function.CURVE :
            caret.pos = caret.pos + 16;
            break;
          case Function.CURVE_LL :
            caret.pos = caret.pos + 8;
            break;
          case Function.CURVE_LR :
            caret.pos = caret.pos + 8;
            break;
          case Function.CURVE_T :
            caret.pos = caret.pos + 8;
            break;
          case Function.LINEAR :
            //caret.pos = caret.pos + 0;
            break;
          case Function.HALF_SINE :
            caret.pos = caret.pos + 8;
            break;
          case Function.CONSTANT :
            //caret.pos = caret.pos + 0;
            break;

          default :
            return false;

        }

        if(caret.pos > ldata.length()){
          return false;
        }

        i++;

      }

      return caret.pos == ldata.length();


    }

    return false;

  }

  private static boolean checkDataAlphabet(String data){
    for(int i = 0; i < data.length(); i++){
      if(!inAlphabet(data.charAt(i))){
        return false;
      }
    }
    return true;
  }

  private static List<Vector> getPointsData(String data){

    List<Vector> points = new LinkedList<Vector>();
    Caret caret = new Caret(data, 0);

    Vector p0 = new Vector(0.0f, fromGridY(readHexValue(caret, 4)));
    Vector p2 = new Vector(1.0f, fromGridY(readHexValue(caret, 4)));

    points.add(p0);

    while(caret.pos < data.length()){
      points.add(new Vector(fromGridX(readHexValue(caret, 4)), fromGridY(readHexValue(caret, 4))));
    }

    points.add(p2);

    return points;

  }

  private static List<Line> getLinesData(String data, List<Vector> points){

    List<Line> lines = new LinkedList<Line>();
    Caret caret = new Caret(data, 0);

    int i = 0;

    while (caret.pos < data.length()) {

      int f = readHexValue(caret, 1);
      Vector v1 = null;
      Vector v2 = null;

      switch (f) {
        case Function.CURVE :
          v1 = new Vector(fromGridVX(readHexValue(caret, 4)), fromGridVY(readHexValue(caret, 4)));
          v2 = new Vector(fromGridVX(readHexValue(caret, 4)), fromGridVY(readHexValue(caret, 4)));
          break;
        case Function.CURVE_LL :
          v1 = new Vector(0, 0);
          v2 = new Vector(fromGridVX(readHexValue(caret, 4)), fromGridVY(readHexValue(caret, 4)));
          break;
        case Function.CURVE_LR :
          v1 = new Vector(fromGridVX(readHexValue(caret, 4)), fromGridVY(readHexValue(caret, 4)));
          v2 = new Vector(0, 0);
          break;
        case Function.CURVE_T :
          Vector p1 = points.get(i);
          Vector p2 = points.get(i + 1);
          v1 = new Vector(fromGridVX(readHexValue(caret, 4)), fromGridVY(readHexValue(caret, 4)));
          v2 = Vector.sub(Vector.add(p1, v1), p2);
          break;
        case Function.LINEAR :
          break;
        case Function.HALF_SINE :
          v1 = new Vector(fromGridVX(readHexValue(caret, 4)), fromGridVY(readHexValue(caret, 4)));
          break;
        case Function.CONSTANT :
          break;
      }

      lines.add(new Line(v1, v2, f));

      i++;

    }

    return lines;

  }

  private static float fromGridX(int x){
    return (float)x / GRID_X;
  }

  private static float fromGridY(int y){
    return ((float)y - GRID_Y0) / GRID_Y;
  }

  private static float fromGridVX(int vx){
    return ((float)vx - GRID_Y0) / GRID_VX;
  }

  private static float fromGridVY(int vy){
    return ((float)vy - GRID_Y0) / GRID_VY;
  }

  private static boolean inAlphabet(char c){
    return (c >= '0' && c <= '9') || (c >= 'a' && c <= 'f');
  }

  private static int readHexValue(Caret caret, int size){

    String vs = null;

    try {
      vs = caret.data.substring(caret.pos, caret.pos + size);
    }catch (IndexOutOfBoundsException e){
      vs = null;
    }
    caret.pos = caret.pos + size;

    if(vs != null){
      return Integer.parseInt(vs, 16);
    }else{
      return -1;
    }

  }

  private static class Caret{

    public String data;
    public int pos;

    public Caret(String data, int pos){
      this.data = data;
      this.pos = pos;
    }

  }

  public static class Line {
    public Vector v1;
    public Vector v2;
    public int f;

    public Line(Vector v1, Vector v2, int f){
      this.v1 = v1;
      this.v2 = v2;
      this.f = f;
    }

  }

}