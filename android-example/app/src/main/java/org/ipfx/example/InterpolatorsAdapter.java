/**
 * Copyright (C) 2015 DSIDEX, Inc.
 */

package org.ipfx.example;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ipfx.example.ui.InterpolatorGraph;

public class InterpolatorsAdapter extends RecyclerView.Adapter<InterpolatorsAdapter.ViewHolder> {

  public InterpolatorsAdapter() {

  }

  @Override
  public InterpolatorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
    ViewHolder vh = new ViewHolder(v);
    return vh;
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.setData(Interpolators.interpolators[position]);
  }

  // Return the size of your dataset (invoked by the layout manager)
  @Override
  public int getItemCount() {
    return Interpolators.interpolators.length;
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {

    public final Context context;
    public final TextView text;
    public final InterpolatorGraph graph;
    public String data = null;

    public ViewHolder(View itemView) {
      super(itemView);

      context = itemView.getContext();
      text = (TextView) itemView.findViewById(R.id.text);
      graph = (InterpolatorGraph) itemView.findViewById(R.id.graph);

      itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

          Intent intent = new Intent(context, AnimationActivity.class);
          intent.putExtra(AnimationActivity.EXTRA_INTERPOLATOR_DATA, data); //Optional parameters
          context.startActivity(intent);

        }
      });

    }

    public void setData(Interpolators.InterpolatorInfo data){
      text.setText(data.name);
      graph.setInterpolatorData(data.data);
      this.data = data.data;
    }

  }

}