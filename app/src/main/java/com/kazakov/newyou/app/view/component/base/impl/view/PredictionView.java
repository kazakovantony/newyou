package com.kazakov.newyou.app.view.component.base.impl.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.kazakov.newyou.app.App;
import com.kazakov.newyou.app.R;
import com.kazakov.newyou.app.model.GymActivity;
import com.kazakov.newyou.app.model.json.PredictionResult;
import com.kazakov.newyou.app.service.PredictorService;
import com.kazakov.newyou.app.view.component.base.impl.creatingfragmentshelpers.ComponentCreatingProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class PredictionView extends Fragment {

    @Inject
    ComponentCreatingProvider componentCreatingProvider;

    private RelativeLayout currentFrame;
    private TableLayout tableLayout;
    private final int prevActivityIndex = 0;
    private final int nextActivityIndex = 2;
    private final int prevNumberOfReactsIndex = 4;
    private final int nextNumberOfRepastsIndex = 6;
    @Inject
    PredictorService predictionResults;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        App.getComponent(getContext()).inject(this);
        setHasOptionsMenu(true);
        currentFrame = (RelativeLayout) inflater.inflate(R.layout.fragment_prediction, container, false);
        tableLayout = currentFrame.findViewById(R.id.tableLayout);
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

        setToggleButtonListener(predictionResults);
        tableLayout.setShrinkAllColumns(true);
        predictionResults.forEach(p -> tableLayout.addView(addPredictionToRow(p)));
    }

    private TableRow addPredictionToRow(PredictionResult p) {
        TableRow tableRow = new TableRow(tableLayout.getContext());
        createActivityInfoCell(p.getActivity().toString(), tableRow);
        tableRow.addView(componentCreatingProvider.createSimpleTextView(p.getDuration(), tableRow.getContext()));
        createNumberOfRepeatCell(Integer.toString(p.getNumberOfRepeats()), tableRow);
        return tableRow;
    }

    private void createActivityInfoCell(String text, TableRow row) {
        List<String> texts = activityToStringList();
        TextSwitcher simpleTextSwitcher = componentCreatingProvider.createSimpleTextSwitcher(row.getContext(), text);
        Button btnNext = componentCreatingProvider.createInvisibleButton(row.getContext(), R.drawable.turn_right);
        btnNext.setOnClickListener(v -> moveTextRight(texts, simpleTextSwitcher));
        Button btnPrev = componentCreatingProvider.createInvisibleButton(row.getContext(), R.drawable.turn_left);
        btnPrev.setOnClickListener(v -> moveTextLeft(texts, simpleTextSwitcher));
        row.addView(btnPrev);
        row.addView(simpleTextSwitcher);
        row.addView(btnNext);

    }

    private void createNumberOfRepeatCell(String text, TableRow row) {
        TextSwitcher textSwitcher = componentCreatingProvider.createSimpleTextSwitcher(row.getContext(), text);
        Button btnPlus = componentCreatingProvider.createInvisibleButton(row.getContext(), R.drawable.turn_right);
        btnPlus.setOnClickListener(v -> setIncreasedNumbersOfRepeats(textSwitcher));
        Button btnMinus = componentCreatingProvider.createInvisibleButton(row.getContext(), R.drawable.turn_left);
        btnMinus.setOnClickListener(v -> setDecreasedNumbersOfRepeats(textSwitcher));
        row.addView(btnMinus);
        row.addView(textSwitcher);
        row.addView(btnPlus);
    }

    public void moveTextRight(List<String> texts, TextSwitcher textSwitcher) {
        int messageCount = texts.size();
        int realIndex = getActivityPosition(textSwitcher, texts);
        ++realIndex;
        if (realIndex == messageCount) realIndex = 0;
        textSwitcher.setText(texts.get(realIndex));
    }

    public void moveTextLeft(List<String> texts, TextSwitcher textSwitcher) {
        int messageCount = texts.size();
        int realIndex = getActivityPosition(textSwitcher, texts);
        --realIndex;
        if (realIndex < 0) realIndex = messageCount;
        textSwitcher.setText(texts.get(realIndex));
    }

    public void onToggleClicked(List<PredictionResult> results) {
        makeHidedButtonsInPredictionTableVisible(results);
    }

    private void setToggleButtonListener(List<PredictionResult> results) {
        final ToggleButton toggle = currentFrame.findViewById(R.id.buttonEdit);
        toggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                onToggleClicked(results);
            } else {
                onToggleUnClicked(results);
            }
        });
    }

    public void onToggleUnClicked(List<PredictionResult> results) {
        makeButtonsInPredictionTableInvisible(results);

    }

    private ArrayList<String> activityToStringList() {
        List<GymActivity> activities = Arrays.asList(GymActivity.values());
        List<String> str = activities.stream().map(Enum::toString).collect(Collectors.toList());
        return (ArrayList<String>) str;
    }

    private int getActivityPosition(TextSwitcher textSwitcher, List<String> activity) {
        TextView currentlyShownTextView = (TextView) textSwitcher.getCurrentView();
        String str = currentlyShownTextView.getText().toString();
        return activity.indexOf(str);
    }

    private void setIncreasedNumbersOfRepeats(TextSwitcher textSwitcher) {
        TextView currentlyShownTextView = (TextView) textSwitcher.getCurrentView();
        String str = currentlyShownTextView.getText().toString();
        int k = Integer.parseInt(str) + 1;
        textSwitcher.setCurrentText(String.valueOf(k));
    }

    private void setDecreasedNumbersOfRepeats(TextSwitcher textSwitcher) {
        TextView currentlyShownTextView = (TextView) textSwitcher.getCurrentView();
        String str = currentlyShownTextView.getText().toString();
        int k = Integer.parseInt(str) - 1;
        textSwitcher.setCurrentText(Integer.toString(k));
    }

    private void makeButtonsInPredictionTableInvisible(List<PredictionResult> results) {
        for (int i = 0; i < results.size(); i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            componentCreatingProvider.setViabilityOfButton(row, prevActivityIndex, false, View.INVISIBLE);
            componentCreatingProvider.setViabilityOfButton(row, nextActivityIndex, false, View.INVISIBLE);
            componentCreatingProvider.setViabilityOfButton(row, prevNumberOfReactsIndex, false, View.INVISIBLE);
            componentCreatingProvider.setViabilityOfButton(row, nextNumberOfRepastsIndex, false, View.INVISIBLE);
        }
    }

    private void makeHidedButtonsInPredictionTableVisible(List<PredictionResult> results) {
        for (int i = 0; i < results.size(); i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            componentCreatingProvider.setViabilityOfButton(row, prevActivityIndex, true, View.VISIBLE);
            componentCreatingProvider.setViabilityOfButton(row, nextActivityIndex, true, View.VISIBLE);
            componentCreatingProvider.setViabilityOfButton(row, prevNumberOfReactsIndex, true, View.VISIBLE);
            componentCreatingProvider.setViabilityOfButton(row, nextNumberOfRepastsIndex, true, View.VISIBLE);
        }
    }

}
