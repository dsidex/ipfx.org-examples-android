/**
 * Copyright (C) 2015 DSIDEX, Inc.
 */

package org.ipfx;

public class Linear implements Function{

  private float k;
  private Vector p1;
  private Vector p2;

  public Linear(Vector p1, Vector p2) {
    compile(p1, p2, null, null);
  }

  @Override
  public void compile(Vector p1, Vector p2, Vector v1, Vector v2) {
    this.p1 = p1;
    this.p2 = p2;
    float dx = p2.x - p1.x;
    if (dx > 0) {
      k = Vector.k(Vector.sub(p2, p1)) * dx;
    }
  }

  @Override
  public float calc(float x) {
    return p1.y + k * x;
  }

}