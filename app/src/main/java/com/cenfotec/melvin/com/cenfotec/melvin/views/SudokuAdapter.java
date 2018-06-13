package com.cenfotec.melvin.com.cenfotec.melvin.views;


import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cenfotec.melvin.domain.SudokuBoard;
import com.cenfotec.melvin.sudoku.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SudokuAdapter extends BaseAdapter {

    private Context mContext;
    private final SudokuBoard sb;
    private final List<SudokuBox> list;

    public SudokuAdapter(Context c, SudokuBoard sb) {
        mContext = c;
        this.sb = sb;
        this.list = createEditTextList();
    }

    private List<SudokuBox> createEditTextList() {
        List<SudokuBox> list = new ArrayList<>();
        for (int i = 0; i < sb.cellCount(); ++i) {
            final SudokuBox editText = new SudokuBox(mContext, i);
            editText.addTextChangedListener(
                    new SudokuCellValidator(editText, i, sb, mContext, list));
            list.add(editText);
        }
        return list;
    }

    public boolean solve() {
        Map<Integer, String> map =  sb.solve();
       for(Map.Entry<Integer, String>  e : map.entrySet()) {
           list.get(e.getKey()).setText(e.getValue());
       }
       return !map.isEmpty();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return list.get(position);
    }
}
