package com.kazakov.newyou.app.view.component.base.impl.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
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

import java.util.ArrayList;
import java.util.List;

public class PredictionView extends Fragment {

    private RelativeLayout currentFrame;
    private TableLayout tableLayout;
    private final int prevActivityIndex = 0;
    private final int nextActivityIndex = 2;
    private final int prevNumberOfReactsIndex = 4;
    private final int nextNumberOfRepastsIndex = 6;
    private final int cellSize = 250;
    private final int textSize = 17;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        App.getComponent(getContext()).inject(this);
        setHasOptionsMenu(true);
        currentFrame = (RelativeLayout) inflater.inflate(R.layout.fragment_prediction, container, false);
        tableLayout = currentFrame.findViewById(R.id.tableLayout);
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

    private void renderView(List<PredictionResult> predictionResult) {
        setToggleButtonListener(predictionResult);
        tableLayout.setShrinkAllColumns(true);
        for (PredictionResult p : predictionResult) {
            TableRow tableRow = addPredictionToRow(p);
            tableLayout.addView(tableRow);
        }
    }

    private TableRow addPredictionToRow(PredictionResult p) {
        TableRow tableRow = new TableRow(tableLayout.getContext());
        createActivityInfoCell(p.getActivity().toString(), tableRow);
        tableRow.addView(getDisabledTextViewForTableRow(p.getDuration(), tableRow));
        createNumberOfRepeatCell(Integer.toString(p.getNumber_of_repeats()), tableRow);
        return tableRow;
    }

    private TextView getDisabledTextViewForTableRow(String text, TableRow row) {
        TextView editText = new TextView(row.getContext());
        editText.setText(text);
        editText.setEnabled(false);
        editText.setWidth(cellSize);
        editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        editText.setTextColor(Color.BLACK);
        return editText;
    }

    private void createActivityInfoCell(String text, TableRow row) {

        String[] texts = activityToStringArray();
        TextSwitcher simpleTextSwitcher = createSimpleTextSwitcher(row.getContext());

        simpleTextSwitcher.setCurrentText(text);
        Button btnNext = new Button(row.getContext());
        btnNext.setEnabled(false);
        btnNext.setBackgroundResource(R.drawable.turn_right);
        btnNext.setOnClickListener(v -> moveTextRight(texts, simpleTextSwitcher));
        Button btnPrev = new Button(row.getContext());
        btnPrev.setBackgroundResource(R.drawable.turn_left);

        btnPrev.setOnClickListener(v -> moveTextLeft(texts, simpleTextSwitcher));
        row.addView(btnPrev);
        row.addView(simpleTextSwitcher);
        row.addView(btnNext);
        btnPrev.setVisibility(View.INVISIBLE);
        btnNext.setVisibility(View.INVISIBLE);

    }

    private void createNumberOfRepeatCell(String text, TableRow row) {
        TextSwitcher textSwitcher = createSimpleTextSwitcher(row.getContext());
        textSwitcher.setCurrentText(text);
        Button btnPlus = new Button(row.getContext());
        btnPlus.setBackgroundResource(R.drawable.turn_right);
        btnPlus.setVisibility(View.INVISIBLE);
        btnPlus.setOnClickListener(v -> setIncreasedNumbersOfRepeats(textSwitcher));
        Button btnMinus = new Button(row.getContext());
        btnMinus.setBackgroundResource(R.drawable.turn_left);
        btnMinus.setVisibility(View.INVISIBLE);
        btnMinus.setOnClickListener(v -> setDecreasedNumbersOfRepeats(textSwitcher));
        row.addView(btnMinus);
        row.addView(textSwitcher);
        row.addView(btnPlus);
    }

    public TextSwitcher createSimpleTextSwitcher(Context context) {
        TextSwitcher simpleTextSwitcher = new TextSwitcher(context);
        simpleTextSwitcher.setInAnimation(AnimationUtils.loadAnimation(context,
                android.R.anim.fade_in));
        simpleTextSwitcher.setOutAnimation(AnimationUtils.loadAnimation(context,
                android.R.anim.fade_out));
        simpleTextSwitcher.setFactory(() -> {
            TextView t = new TextView(context);
            t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
            t.setTextSize(textSize);
            t.setWidth(cellSize);
            t.setTextColor(Color.BLACK);
            return t;
        });
        return simpleTextSwitcher;
    }

    public void moveTextRight(String[] texts, TextSwitcher textSwitcher) {
        int messageCount = texts.length;
        int realIndex = getActivityPosition(textSwitcher, texts);
        ++realIndex;
        if (realIndex == messageCount)
            realIndex = 0;
        textSwitcher.setText(texts[realIndex]);

    }

    public void moveTextLeft(String[] texts, TextSwitcher textSwitcher) {
        int messageCount = texts.length - 1;
        int realIndex = getActivityPosition(textSwitcher, texts);
        --realIndex;
        if (realIndex < 0)
            realIndex = messageCount;
        textSwitcher.setText(texts[realIndex]);

    }

    public void onToggleClicked(List<PredictionResult> results) {
        makeHidedButtonsInPredictionTableEnable(results);
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
        setHidedButtonsToPredictionTable(results);

    }

    private String[] activityToStringArray() {
        GymActivity arr[] = GymActivity.values();
        String[] texts = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            texts[i] = arr[i].toString();
        }
        return texts;
    }

    private int getActivityPosition(TextSwitcher textSwitcher, String[] text) {
        int index = 0;
        TextView currentlyShownTextView = (TextView) textSwitcher.getCurrentView();
        String str = currentlyShownTextView.getText().toString();
        for (int i = 0; i < text.length; i++) {
            if (text[i].equals(str))
                index = i;
        }
        return index;
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

    private void setHidedButtonsToPredictionTable(List<PredictionResult> results) {
        for (int i = 0; i < results.size(); i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            row.getChildAt(prevActivityIndex).setEnabled(false);
            row.getChildAt(prevActivityIndex).setVisibility(View.INVISIBLE);
            row.getChildAt(nextActivityIndex).setEnabled(false);
            row.getChildAt(nextActivityIndex).setVisibility(View.INVISIBLE);
            row.getChildAt(prevNumberOfReactsIndex).setEnabled(false);
            row.getChildAt(prevNumberOfReactsIndex).setVisibility(View.INVISIBLE);
            row.getChildAt(nextNumberOfRepastsIndex).setEnabled(false);
            row.getChildAt(nextNumberOfRepastsIndex).setVisibility(View.INVISIBLE);
        }
    }

    private void makeHidedButtonsInPredictionTableEnable(List<PredictionResult> results) {
        for (int i = 0; i < results.size(); i++) {
            TableRow row = (TableRow) tableLayout.getChildAt(i);
            row.getChildAt(prevActivityIndex).setEnabled(true);
            row.getChildAt(prevActivityIndex).setVisibility(View.VISIBLE);
            row.getChildAt(nextActivityIndex).setEnabled(true);
            row.getChildAt(nextActivityIndex).setVisibility(View.VISIBLE);
            row.getChildAt(prevNumberOfReactsIndex).setEnabled(true);
            row.getChildAt(prevNumberOfReactsIndex).setVisibility(View.VISIBLE);
            row.getChildAt(nextNumberOfRepastsIndex).setEnabled(true);
            row.getChildAt(nextNumberOfRepastsIndex).setVisibility(View.VISIBLE);
        }
    }
}
