package com.cenfotec.melvin.com.cenfotec.melvin.views;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.cenfotec.melvin.domain.SudokuBoard;

import java.util.List;

public final class SudokuCellValidator implements TextWatcher {
    private final SudokuBox textView;
    private final int pos;
    private final SudokuBoard sb;
    private final Context context;
    private final List<SudokuBox> grid;

    public SudokuCellValidator(final SudokuBox textView,
                               final int pos,
                               final SudokuBoard sb,
                               final Context context,
                               final List<SudokuBox> grid) {
        this.textView = textView;
        this.pos      = pos;
        this.sb       = sb;
        this.context  = context;
        this.grid     = grid;
    }


    @Override
    final public void afterTextChanged(final Editable s) {
        String text = textView.getText().toString();
        if(text.isEmpty()) {
            sb.remove(pos);
            return;
        }
        List<Integer>  invalid = sb.add(pos, text.charAt(0));
        if(!text.equals("0")
                && invalid.isEmpty()) {
            return;
        }
        textView.setText("");
        for (Integer pos : invalid ) {
            grid.get(pos).animateInValid();
        }
        textView.animateInValid();
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) {  }
}