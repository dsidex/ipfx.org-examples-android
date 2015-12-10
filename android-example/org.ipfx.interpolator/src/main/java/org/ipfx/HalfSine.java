/**
 * Copyright (C) 2015 DSIDEX, Inc.
 */

package org.ipfx;

public class HalfSine implements Function{

  private Vector p1;
  private Vector p2;
  private float dx;
  private float dx1;
  private float k1;
  private float k2;
  private float step1;
  private float step2;

  public HalfSine(Vector p1, Vector p2, Vector v1) {
    compile(p1, p2, v1, null);
  }

  @Override
  public void compile(Vector p1, Vector p2, Vector v1, Vector v2) {

    this.p1 = p1;
    this.p2 = p2;
    dx = p2.x - p1.x;

    if (dx > 0) {

      dx1 = v1.x / dx;
      float dx2 = 1 - dx1;

      step1 = 0.5f * (float)Math.PI / dx1;
      step2 = 0.5f * (float)Math.PI / dx2;

      k1 = v1.y;
      k2 = p1.y + v1.y - p2.y;

    }

  }

  @Override
  public float calc(float x) {
    if (dx == 0) {
      return p1.y;
    }

    if (x < dx1) {
      return p1.y + k1 * (float)Math.sin(x * step1);
    } else {
      return p2.y + k2 * (float)Math.sin((x - dx1) * step2 + 0.5 * Math.PI);
    }
  }

}