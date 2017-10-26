package otaking.com.statisticscalculator.common.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import otaking.com.statisticscalculator.R;

/**
 * Created by federico.serron on 24/10/2017.
 *
 * Clase customizada para incluir un SeekBar y un Layout para las marcas
 */

public class SeekBarLayout extends LinearLayout {

    private int maxCount;
    private int textColor;
    private LinearLayout mSeekLin;
    private SeekBar mSeekBar;

    public SeekBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SeekBarLayout,
                0, 0);

        try {
            maxCount = a.getInteger(R.styleable.SeekBarLayout_maxCount, 5);
            textColor = a.getInteger(R.styleable.SeekBarLayout_textColor, Color.DKGRAY);
        } finally {
            a.recycle();
        }

        init();
    }

    private void init(){
        //Sets the orientation
        this.setOrientation(LinearLayout.VERTICAL);

        //Creates the SeekBar
        mSeekBar = new SeekBar(getContext());
        mSeekBar.setMax(maxCount - 1);
        this.addView(mSeekBar);

        // Add LinearLayout for labels below SeekBar
        addLabelsBelowSeekBar();

    }

    private void addLabelsBelowSeekBar() {
        mSeekLin = new LinearLayout(getContext());
        mSeekLin.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        mSeekLin.setLayoutParams(params);

        //Spacing
        //if (maxCount < 10){ //While only one number for each step
            mSeekLin.setPadding(10, 0, 10, 0);
            params.setMargins(35, 10, 35, 0);
        /*} else {
            mSeekLin.setPadding(10, 0, 10, 0);
            params.setMargins(35, 10, 35, 0);
        }*/

        for (int count = 0; count < maxCount; count++) {
            TextView textView = new TextView(getContext());
            //if (maxCount > 9) textView.setTextSize(10.0f);
            textView.setText(String.valueOf(count + 1));
            textView.setTextColor(textColor);
            textView.setGravity(Gravity.START);
            mSeekLin.addView(textView);
            textView.setLayoutParams((count == maxCount - 1) ? getLayoutParams(0.0f) : getLayoutParams(1.0f));
        }

        this.addView(mSeekLin);
    }

    LinearLayout.LayoutParams getLayoutParams(float weight) {
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, weight);
    }

    private void cleanNumbering(){
        this.removeView(mSeekLin);
        invalidate();
        requestLayout();
    }

    public void resizeSeekBar(int maxCount){
        this.setMaxCount(maxCount);
        this.mSeekBar.setMax(maxCount - 1);
        this.cleanNumbering();
        this.addLabelsBelowSeekBar();
        invalidate();
        requestLayout();
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
        invalidate();
        requestLayout();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        invalidate();
        requestLayout();
    }

    public LinearLayout getContainerLayout() {
        return mSeekLin;
    }

    public void setContainerLayout(LinearLayout mSeekLin) {
        this.mSeekLin = mSeekLin;
        invalidate();
        requestLayout();
    }

    public SeekBar getSeekBar() {
        return mSeekBar;
    }

    public void setSeekBar(SeekBar mSeekBar) {
        this.mSeekBar = mSeekBar;
        invalidate();
        requestLayout();
    }

}
