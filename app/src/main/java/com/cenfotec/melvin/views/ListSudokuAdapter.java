package com.cenfotec.melvin.views;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.cenfotec.melvin.entities.SudokuEntity;

import com.cenfotec.melvin.entities.SudokuReduce;
import com.cenfotec.melvin.sudoku.R;
import com.cenfotec.melvin.sudoku.Sudoku;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ListSudokuAdapter extends RecyclerView.Adapter<ListSudokuAdapter.ViewHolder> {

    private final List<SudokuReduce> mDataset;
    private final View.OnClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView id;
        public TextView moves;
        public TextView time;
        public ViewHolder(View layout) {
            super(layout);
            this.id = layout.findViewById(R.id.sudokuId2);
            this.moves = layout.findViewById(R.id.sudokuMoves);
            this.time = layout.findViewById(R.id.sudokuTime);
        }
    }

    public ListSudokuAdapter(final List<SudokuReduce> myDataset, final View.OnClickListener listener) {
        this.mDataset = myDataset;
        this.listener = listener;
    }

    @Override
    public ListSudokuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ConstraintLayout v = (ConstraintLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sudoku_list_item, parent, false);
        v.setOnClickListener(this.listener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.id.setText(mDataset.get(position).getId());
        holder.moves.setText(Long.toString(mDataset.get(position).getMoveCount()));
        holder.time.setText(Long.toString(mDataset.get(position).getTime()));

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}