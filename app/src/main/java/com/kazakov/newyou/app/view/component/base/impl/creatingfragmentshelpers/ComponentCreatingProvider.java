package com.kazakov.newyou.app.view.component.base.impl.creatingfragmentshelpers;

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


public class ComponentCreatingProvider {

    private final int cellSize = 250;

    @Inject
    public ComponentCreatingProvider() {
    }

    public TextSwitcher createSimpleTextSwitcher(Context context, String text) {
        TextSwitcher simpleTextSwitcher = new TextSwitcher(context);
        simpleTextSwitcher.setInAnimation(AnimationUtils.loadAnimation(context,
                android.R.anim.fade_in));
        simpleTextSwitcher.setOutAnimation(AnimationUtils.loadAnimation(context,
                android.R.anim.fade_out));
        simpleTextSwitcher.setFactory(() -> {
            return createSimpleTextView(text, context);
        });
        simpleTextSwitcher.setCurrentText(text);
        return simpleTextSwitcher;
    }

    public TextView createSimpleTextView(String text, Context context) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        textView.setWidth(cellSize);
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
}
