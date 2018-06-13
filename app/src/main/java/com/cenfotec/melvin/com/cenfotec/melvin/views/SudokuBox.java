package com.cenfotec.melvin.com.cenfotec.melvin.views;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.cenfotec.melvin.sudoku.R;

import java.lang.reflect.Field;

public class SudokuBox extends AppCompatEditText {


    private static final int ANIMATION_DURATION = 500;
    private static final int COLUMNS = 9;

    private final ValueAnimator animator;

    public SudokuBox(Context context, int pos) {
        super(context);

        final int color = calculateBackGroundColor(context, pos);
        final int invalidColor = ContextCompat.getColor(context, R.color.colorPrimary);
        animator = animateEditText(this, color, invalidColor);

        this.setIncludeFontPadding(false);
        setBackgroundColor(color);
        setGravity(Gravity.CENTER_HORIZONTAL
                | Gravity.CENTER_VERTICAL);

        setInputType(InputType.TYPE_CLASS_NUMBER
                | InputType.TYPE_NUMBER_VARIATION_PASSWORD );

        initTextStyles(context);
    }

    private void initTextStyles(Context context) {
        setFilters(new InputFilter[]{ new InputFilter.LengthFilter(1) });
        setTransformationMethod(new NumericKeyBoardTransformationMethod());
        changeCursorColor();
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        setTextColor(ContextCompat.getColor(context, R.color.white));
    }

    private int calculateBackGroundColor(Context context, int pos) {
        int rowOffset = (pos / COLUMNS) / 3;
        int colOffset = (pos % COLUMNS) / 3;

        return (((rowOffset + colOffset) % 2) ==  0)
                ? ContextCompat.getColor(context, R.color.colorAccent)
                : ContextCompat.getColor(context, R.color.colorPrimaryDark);
    }

    private void changeCursorColor() {
        try {
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(this, R.drawable.text_cursor_style);
        } catch (NoSuchFieldException n) {
        } catch (IllegalAccessException e) { }
    }

    private ValueAnimator animateEditText(final TextView editText,
                                          final int tileColor,
                                          final int invalidColor) {

        ValueAnimator colorAnimation = ValueAnimator
                .ofArgb(tileColor, invalidColor);
        colorAnimation.setDuration(ANIMATION_DURATION);
        colorAnimation.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                editText.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimation.setRepeatCount(1);
        return colorAnimation;
    }

    public void animateInValid() {
        animator.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    private class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return source;
        }
    }
}
