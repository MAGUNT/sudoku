package com.cenfotec.melvin.com.cenfotec.melvin.views;


import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;

import com.cenfotec.melvin.domain.SudokuBoard;

import java.util.List;

public class MySudokuAdapter extends RecyclerView.Adapter<MySudokuAdapter.ViewHolder> {

    private final SudokuBoard sudokuBoard;
    private final RecyclerView.LayoutManager gridLayoutManager;

    public MySudokuAdapter(SudokuBoard sudokuBoard,
                           RecyclerView.LayoutManager gridLayoutManager) {

        this.sudokuBoard = sudokuBoard;
        this.gridLayoutManager = gridLayoutManager;
    }

    @Override
    public MySudokuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        ViewHolder vh = new ViewHolder(
                new SudokuBox(parent.getContext()),
                new MyCustomSudokuBoxListener(gridLayoutManager));
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.myCustomSudokuBoxListener.updatePosition(position);
        holder.mEditText.setText(sudokuBoard.getValue(position));
        holder.mEditText.update(position, sudokuBoard.isEnable(position));
    }

    @Override
    public int getItemCount() {
        return sudokuBoard.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public SudokuBox mEditText;
        public MyCustomSudokuBoxListener myCustomSudokuBoxListener;

        public ViewHolder(View v, MyCustomSudokuBoxListener myCustomSudokuBoxListener) {
            super(v);

            this.mEditText = (SudokuBox) v;
            this.myCustomSudokuBoxListener = myCustomSudokuBoxListener;
            this.mEditText.addTextChangedListener(myCustomSudokuBoxListener);
        }
    }

    private class MyCustomSudokuBoxListener implements TextWatcher {
        private int position;
        private final RecyclerView.LayoutManager layoutManager;

        public MyCustomSudokuBoxListener(RecyclerView.LayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }
        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) { }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) { }

        @Override
        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            SudokuBox sudokuBox = getSudokuBox(position);
            if(sudokuBox == null
                    || sudokuBoard.getValue(position).equals(text)) {
                return;
            }
            List<Integer> invalid = sudokuBoard
                    .add(position, text);
            if(!text.equals("0")
                    && invalid.isEmpty()) {
                return;
            }
            sudokuBox.getText().clear();
            animateInvalidSudokuCells(invalid);
        }

        private SudokuBox getSudokuBox(final int position) {
            return (SudokuBox) layoutManager.findViewByPosition(position);
        }

        private void animateInvalidSudokuCells(List<Integer> invalid) {
            for (Integer p : invalid ) {
                getSudokuBox(p).animateInValid();
            }
            getSudokuBox(position).animateInValid();
        }
    }
}