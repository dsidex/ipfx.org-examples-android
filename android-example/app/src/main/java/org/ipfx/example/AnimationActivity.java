/**
 * Copyright (C) 2015 DSIDEX, Inc.
 */

package org.ipfx.example;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import org.ipfx.Interpolator;
import org.ipfx.example.ui.InterpolatorGraph;


public class AnimationActivity extends Activity {

  public static final String EXTRA_INTERPOLATOR_DATA = "interpolator_data";

  private RadioGroup group;
  private Button playButton;
  private InterpolatorGraph graph;
  private View testLayout;
  private View testView;
  private SeekBar durationSeekBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_anim_layout);

    graph = (InterpolatorGraph)findViewById(R.id.graph);
    testLayout = findViewById(R.id.test_layout);
    testView = findViewById(R.id.test_view);


    group = (RadioGroup) findViewById(R.id.anim_radio_group);
    durationSeekBar = (SeekBar) findViewById(R.id.duration_seek_bar);

    playButton = (Button) findViewById(R.id.play_button);

    Intent intent = getIntent();
    if(intent.getExtras() != null){
      graph.setInterpolatorData(intent.getExtras().getString(EXTRA_INTERPOLATOR_DATA, ""));
    }

    playButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        playAnimation();
      }
    });

    testView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        playAnimation();
      }
    });

  }

  private void playAnimation(){

    int id = group.getCheckedRadioButtonId();

    switch (id){
      case R.id.anim_x:
        playXAnimation();
        break;
      case R.id.anim_y:
        playYAnimation();
        break;
      case R.id.anim_rotate:
        playRotateAnimation();
        break;
      case R.id.anim_scale:
        playScaleAnimation();
        break;
    }

  }

  private void playXAnimation(){

    int layoutWidth = testLayout.getWidth();
    int viewWidth = testView.getWidth();

    int lw2 = layoutWidth / 2;
    int vw2 = viewWidth / 2;

    final Interpolator interpolator = graph.getInterpolator();
    ObjectAnimator animator = ObjectAnimator.ofFloat(testView, View.X, lw2 - vw2, layoutWidth - viewWidth);
    animator.setDuration(1000 + durationSeekBar.getProgress());

    animator.setInterpolator(new TimeInterpolator() {
      @Override
      public float getInterpolation(float input) {
        graph.setProgress(input);
        return interpolator.calc(input);
      }
    });
    animator.start();

  }

  private void playYAnimation(){

    int layoutHeight = testLayout.getHeight();
    int viewHeight = testView.getHeight();

    int lh2 = layoutHeight / 2;
    int vh2 = viewHeight / 2;

    final Interpolator interpolator = graph.getInterpolator();
    ObjectAnimator animator = ObjectAnimator.ofFloat(testView, View.Y, lh2 - vh2, 0);
    animator.setDuration(1000 + durationSeekBar.getProgress());

    animator.setInterpolator(new TimeInterpolator() {
      @Override
      public float getInterpolation(float input) {
        graph.setProgress(input);
        return interpolator.calc(input);
      }
    });
    animator.start();

  }

  private void playRotateAnimation(){

    final Interpolator interpolator = graph.getInterpolator();
    ObjectAnimator animator = ObjectAnimator.ofFloat(testView, View.ROTATION, 0, 360);
    animator.setDuration(1000 + durationSeekBar.getProgress());

    animator.setInterpolator(new TimeInterpolator() {
      @Override
      public float getInterpolation(float input) {
        graph.setProgress(input);
        return interpolator.calc(input);
      }
    });
    animator.start();

  }

  private void playScaleAnimation(){

    final Interpolator interpolator = graph.getInterpolator();
    ObjectAnimator animatorX = ObjectAnimator.ofFloat(testView, View.SCALE_X, 0, 1);
    animatorX.setDuration(1000 + durationSeekBar.getProgress());

    animatorX.setInterpolator(new TimeInterpolator() {
      @Override
      public float getInterpolation(float input) {
        graph.setProgress(input);
        return interpolator.calc(input);
      }
    });

    ObjectAnimator animatorY = ObjectAnimator.ofFloat(testView, View.SCALE_Y, 0, 1);
    animatorY.setDuration(1000 + durationSeekBar.getProgress());

    animatorY.setInterpolator(new TimeInterpolator() {
      @Override
      public float getInterpolation(float input) {
        return interpolator.calc(input);
      }
    });

    AnimatorSet anim = new AnimatorSet();
    anim.play(animatorX).with(animatorY);
    anim.start();

  }

}