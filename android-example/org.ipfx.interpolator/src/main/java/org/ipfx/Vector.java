/**
* Copyright (C) 2015 DSIDEX, Inc.
*/

package org.ipfx;

public class Vector {

  public float x;
  public float y;

  public Vector(float x, float y){
    this.x = x;
    this.y = y;
  }

  public static Vector add(Vector v1, Vector v2) {
    return new Vector(v1.x + v2.x, v1.y + v2.y);
  }

  public static Vector sub(Vector v1, Vector v2) {
    return new Vector(v1.x - v2.x, v1.y - v2.y);
  }

  public static Vector mul(Vector v1, Vector v2) {
    return new Vector(v1.x * v2.x, v1.y * v2.y);
  }

  public static Vector addC(Vector v1, float C) {
    return new Vector(v1.x + C, v1.y + C);
  }

  public static Vector mullC(Vector v1, float C) {
    return new Vector(v1.x * C, v1.y * C);
  }

  public static Vector center(Vector v1, Vector v2) {
    return new Vector(0.5f * (v1.x + v2.x), 0.5f * (v1.y + v2.y));
  }

  public static Vector slide(Vector v1, Vector v2, float i) {
    float o = 1 - i;
    return new Vector(v1.x * o + v2.x * i, v1.y * o + v2.y * i);
  }

  public static float mod(Vector v) {
    return (float)Math.sqrt(v.x * v.x + v.y * v.y);
  }

  public static float k(Vector v) {
    if (v.x != 0) {
      return v.y / v.x;
    } else {
      return 0;
    }
  }

  public static float kd(Vector v) {

    if (v.x != 0) {
      float vk = v.y / v.x;
      return (float)Math.sqrt(1 + vk * vk);
    } else {
      return 0;
    }

  }

}