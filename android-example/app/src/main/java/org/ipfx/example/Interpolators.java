/**
 * Copyright (C) 2015 DSIDEX, Inc.
 */

package org.ipfx.example;

public class Interpolators {

  public static final InterpolatorInfo[] interpolators =
    {
      new InterpolatorInfo("Linear", "?p=7ffffffe&l=4"),
      new InterpolatorInfo("Accelerate", "?p=7ffffffe&l=0986f78e85bf345a5"),
      new InterpolatorInfo("Decelerate", "?p=7ffffffe&l=0a4fabbf368ab8903"),
      new InterpolatorInfo("Accelerate Decelerate 1", "?p=7ffffffe&l=0894d6fed76759063"),
      new InterpolatorInfo("Accelerate Decelerate 2", "?p=7ffffffe8043c07a&l=16cf9612b295cca0f9"),
      new InterpolatorInfo("Anticipate", "?p=7ffffffe&l=08f8b933e5d370710"),
      new InterpolatorInfo("Overshoot", "?p=7ffffffe&l=0a1f4fa3770286db6"),
      new InterpolatorInfo("Anticipate Overshoot", "?p=7ffffffe8043bf83&l=094b991516a634216094dcbf416a3f6f92"),
      new InterpolatorInfo("Bounce", "?p=7ffffffe575efffe9e02fffed8f6fffe&l=2f13c5ac138b3d6ad338a5a77a8386807d6e"),
      new InterpolatorInfo("Cycle 1", "?p=7fff7fff&l=5aa4eaaa9"),
      new InterpolatorInfo("Cycle 2", "?p=7fff7fff80437fff&l=59532aaa95949355a7"),
      new InterpolatorInfo("Cycle 3", "?p=7fff7fff40007fff80437fffbfff7fff&l=58a93aaa958a5a55a758a71aa5758b0455a7"),
    };

  public static class InterpolatorInfo{

    public final String name;
    public final String data;

    public InterpolatorInfo(String name, String data){
      this.name = name;
      this.data = data;
    }

  }

}