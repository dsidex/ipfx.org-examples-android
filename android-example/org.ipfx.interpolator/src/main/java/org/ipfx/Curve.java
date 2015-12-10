/**
 * Copyright (C) 2015 DSIDEX, Inc.
 */

package org.ipfx;

public class Curve implements Function{

  private Vector p1;
  private Vector p2;
  private float dx;
  private float m1;
  private float m2;
  private float k1;
  private float k2;
  private float d1;
  private float d2;

  public Curve(Vector p1, Vector p2, Vector v1, Vector v2) {
    compile(p1, p2, v1, v2);
  }

  @Override
  public void compile(Vector p1, Vector p2, Vector v1, Vector v2) {
    this.p1 = p1;
    this.p2 = p2;
    dx = p2.x - p1.x;
    m1 = Vector.mod(v1);
    m2 = Vector.mod(v2);
    k1 = Vector.k(v1);
    k2 = Vector.k(v2);
    d1 = Vector.kd(v1);
    d2 = Vector.kd(v2);
  }

  @Override
  public float calc(float x) {
    if (dx == 0) {
      return p1.y;
    }

    float i = x;
    float o = 1 - i;

    float yv1 = k1 * i;
    float yv2 = -k2 * o;

    float f1 = m1 * o / (1 + d1 * i);
    float f2 = m2 * i / (1 + d2 * o);

    float F = f1 + f2 + 1;
    float pc1 = f1 / F;
    float pc2 = f2 / F;

    float y1 = p1.y + yv1;
    float y2 = p2.y + yv2;
    float y3 = p1.y * o + p2.y * i;

    return y1 * pc1 + y2 * pc2 + y3 / F;
  }

}
