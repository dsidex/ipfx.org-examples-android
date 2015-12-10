/**
 * Copyright (C) 2015 DSIDEX, Inc.
 */

package org.ipfx;

public interface Function {

  public static final int CURVE = 0;
  public static final int CURVE_LL = 1;
  public static final int CURVE_LR = 2;
  public static final int CURVE_T = 3;
  public static final int LINEAR = 4;
  public static final int HALF_SINE = 5;
  public static final int CONSTANT = 6;

  public void compile(Vector p1, Vector p2, Vector v1, Vector v2);
  public float calc(float x);


}