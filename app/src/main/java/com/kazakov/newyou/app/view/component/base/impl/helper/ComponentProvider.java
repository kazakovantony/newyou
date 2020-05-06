package com.kazakov.newyou.app.view.component.base.impl.helper;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextSwitcher;
import android.widget.TextView;

import javax.inject.Inject;


public class ComponentProvider {

    private static final int CELL_SIZE = 250;

    @Inject
    public ComponentProvider() {
    }

    public TextSwitcher createSimpleTextSwitcher(Context context, String text) {
        TextSwitcher simpleTextSwitcher = new TextSwitcher(context);
        simpleTextSwitcher.setInAnimation(AnimationUtils.loadAnimation(context,
                android.R.anim.fade_in));
        simpleTextSwitcher.setOutAnimation(AnimationUtils.loadAnimation(context,
                android.R.anim.fade_out));
        simpleTextSwitcher.setFactory(() -> createSimpleTextView(text, context));
        simpleTextSwitcher.setCurrentText(text);
        return simpleTextSwitcher;
    }

    public TextView createSimpleTextView(String text, Context context) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        textView.setWidth(CELL_SIZE);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setTextColor(Color.BLACK);
        return textView;
    }

    public Button createInvisibleButton(Context context, int resist) {
        Button button = new Button(context);
        button.setEnabled(false);
        button.setBackgroundResource(resist);
        button.setVisibility(View.INVISIBLE);
        return button;
    }

    public void setViabilityOfButton(ViewGroup viewGroup, int index, boolean status, int visibility) {
        viewGroup.getChildAt(index).setEnabled(status);
        viewGroup.getChildAt(index).setVisibility(visibility);
    }

    public String getTextFromTextSwithcerFromTableRow(ViewGroup view , int index){
        TextSwitcher textSwitcher = (TextSwitcher) view.getChildAt(index);
        TextView textView = (TextView) textSwitcher.getCurrentView();
        return textView.getText().toString();
    }

    public String getTextFromTestView(ViewGroup view, int index){
        return ((TextView)view.getChildAt(index)).getText().toString();
    }
}
