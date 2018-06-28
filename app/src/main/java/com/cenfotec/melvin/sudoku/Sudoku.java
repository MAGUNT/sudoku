package com.cenfotec.melvin.sudoku;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Toast;

import com.cenfotec.melvin.bll.SudokuManager;
import com.cenfotec.melvin.views.ItemOffsetDecoration;
import com.cenfotec.melvin.views.MySudokuAdapter;
import com.cenfotec.melvin.bll.SudokuBoard;
import com.cenfotec.melvin.entities.SudokuEntity;
import com.google.android.gms.tasks.Task;

import static android.os.SystemClock.elapsedRealtime;

public class Sudoku extends AppCompatActivity {

    private static String SUDOKU = "SUDOKU";

    private String id;
    private Chronometer chronometer;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SudokuBoard sudokuBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        chronometer = findViewById(R.id.simpleChronometer);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(this, 9);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        mRecyclerView.addItemDecoration(itemDecoration);
        initBoard();

    }

    @Override
    protected void onPause() {
        super.onPause();

        if(id == null) {
            return;
        }
        SudokuEntity entity = new SudokuEntity(sudokuBoard.getBoard());
        entity.setId(this.id);
        entity.setTime(getTime());
        entity.setMoveCount(sudokuBoard.getMoveCount());

        SudokuManager.updateSudoku(entity);
    }


    private void initBoard() {

        String id = getIntent().getStringExtra(SUDOKU);
        SudokuManager.getSudokuById(id)
                .addOnCompleteListener(this::createGrid);
    }

    public void createGrid(Task<SudokuEntity> sudokuBoard) {
        SudokuEntity entity =  sudokuBoard.getResult();
        this.id = entity.getId();
        this.sudokuBoard = SudokuBoard.createBoard(entity.getSudokuBoard(),
                entity.getMoveCount());
        mAdapter = new MySudokuAdapter(this.sudokuBoard, mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        chronometer.setBase(SystemClock.elapsedRealtime() - entity.getTime());
        chronometer.start();
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

    public long getTime() {
        return SystemClock.elapsedRealtime() - chronometer.getBase();
    }

}
