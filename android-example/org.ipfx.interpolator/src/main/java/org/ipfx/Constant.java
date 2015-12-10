/**
 * Copyright (C) 2015 DSIDEX, Inc.
 */

package org.ipfx;

public class Constant implements Function{

  private float v;

  public Constant(Vector p1) {
    compile(p1, null, null, null);
  }

  @Override
  public void compile(Vector p1, Vector p2, Vector v1, Vector v2) {
    v = p1.y;
  }

  @Override
  public float calc(float x) {
    return v;
  }

}