package com.cenfotec.melvin.sudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.cenfotec.melvin.com.cenfotec.melvin.views.ItemOffsetDecoration;
import com.cenfotec.melvin.com.cenfotec.melvin.views.MySudokuAdapter;
import com.cenfotec.melvin.domain.SudokuBoard;
import com.cenfotec.melvin.domain.ManySolutionsSudokuGenerator;
import com.cenfotec.melvin.domain.SudokuGenerator;

public class Sudoku extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SudokuBoard sudokuBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(this, 9);
        mRecyclerView.setLayoutManager(mLayoutManager);

        sudokuBoard = SudokuBoard.generateBoard();
        mAdapter = new MySudokuAdapter(sudokuBoard, mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        mRecyclerView.addItemDecoration(itemDecoration);
    }

    public void solve(View view) {
        if(sudokuBoard.solve()) {
            mAdapter.notifyDataSetChanged();
            return;
        }
        Toast toast = Toast.makeText(this,
                R.string.not_solvable, Toast.LENGTH_LONG);
        toast.setGravity(
                Gravity.CENTER | Gravity.TOP,
                0, 0);
        toast.show();
    }
}
