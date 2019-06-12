package com.kazakov.newyou.app.view.component.base.impl.view;

import android.support.design.widget.TabItem;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kazakov.newyou.app.R;
import com.kazakov.newyou.app.model.PredictionResult;

import java.util.List;

public class PredictionView extends Fragment {

    FrameLayout currentFrame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        currentFrame = (FrameLayout) inflater.inflate(R.layout.fragment_prediction, container, false);
        renderView(null);
        return currentFrame;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        renderView(null);
        return true;
    }

    private void renderView(List<PredictionResult> predictionResults) {
        TextView textView = new TextView(getActivity());
        textView.append("hello");
        currentFrame.addView(textView);
    }
}
