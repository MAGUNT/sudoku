package com.cenfotec.melvin.views;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.cenfotec.melvin.entities.SudokuEntity;

import com.cenfotec.melvin.sudoku.R;

public class ListSudokuAdapter RecyclerView.Adapter<ListSudokuAdapter.ViewHolder> {

    private List<SudokuEntity> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ConstraintLayout layout;
        public ViewHolder(ConstraintLayout layout) {
            super(layout);
            this.layout = layout;
        }
    };

    // Provide a suitable constructor (depends on the kind of dataset)
    public ViewHolder(List<SudokuEntity> myDataset) {
        myDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListSudokuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        ConsTra v = () LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sudoku_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}