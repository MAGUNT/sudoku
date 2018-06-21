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
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.cenfotec.melvin.sudoku.R;

import java.lang.reflect.Field;

public class SudokuBox extends AppCompatEditText {


    private static final int ANIMATION_DURATION = 500;
    private static final int COLUMNS = 9;

    private ValueAnimator animator;
    private final Context context;

    public SudokuBox(Context context) {
        super(context);
        this.context = context;
        setIncludeFontPadding(false);
        setGravity(Gravity.CENTER_HORIZONTAL
                | Gravity.CENTER_VERTICAL);

        setInputType(InputType.TYPE_CLASS_NUMBER
                | InputType.TYPE_NUMBER_VARIATION_PASSWORD
                | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS );
        setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        initTextStyles(context);
    }

    private void initTextStyles(Context context) {
        setFilters(new InputFilter[]{ new InputFilter.LengthFilter(1) });
        setTransformationMethod(new NumericKeyBoardTransformationMethod());
        changeCursorColor();
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
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

    public void update(int pos, boolean isEnable) {
        int color = calculateBackGroundColor(context, pos);
        if(!isEnable) {
            color = manipulateColor(color, 1.15f);
        }
        final int invalidColor = ContextCompat.getColor(context, R.color.colorPrimary);
        animator = animateEditText(this, color, invalidColor);
        setBackgroundColor(color);
        setEnabled(isEnable);
    }

    public static int manipulateColor(int color, float factor) {
        final int max = 255;
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r, max),
                Math.min(g, max),
                Math.min(b, max));
    }

    private ValueAnimator animateEditText(final TextView editText,
                                          final int tileColor,
                                          final int invalidColor) {

        ValueAnimator colorAnimation = ValueAnimator
                .ofArgb(tileColor, invalidColor);
        colorAnimation.setDuration(ANIMATION_DURATION);
        colorAnimation.addUpdateListener(
                animator -> editText.setBackgroundColor((int) animator.getAnimatedValue()));
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
