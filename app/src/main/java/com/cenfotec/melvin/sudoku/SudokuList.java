package com.cenfotec.melvin.sudoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cenfotec.melvin.bll.SudokuBoard;
import com.cenfotec.melvin.dal.SudokuRepo;
import com.cenfotec.melvin.entities.SudokuEntity;
import com.cenfotec.melvin.entities.SudokuReduce;
import com.cenfotec.melvin.views.ItemOffsetDecoration;
import com.cenfotec.melvin.views.ListSudokuAdapter;
import com.cenfotec.melvin.views.MySudokuAdapter;

import java.util.List;

public class SudokuList extends AppCompatActivity {
    private static String SUDOKU = "SUDOKU";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<SudokuReduce> sudokuList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_list);


        mRecyclerView = findViewById(R.id.sudokuRecyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        mRecyclerView.addItemDecoration(itemDecoration);


        initList();
    }

    private void initList() {
        SudokuRepo.getAllSudokus()
                .addOnSuccessListener(this, this::onSuccess);
    }

    private void onSuccess(List<SudokuReduce> list) {
        this.sudokuList = list;
        mAdapter = new ListSudokuAdapter(list, this::onClickAdapterListener);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void onClickAdapterListener(View view) {
        int itemPosition = mRecyclerView.getChildLayoutPosition(view);
        String id = this.sudokuList.get(itemPosition).getId();
        Intent intent = new Intent(this, Sudoku.class);
        intent.putExtra(SUDOKU, id);
        this.startActivity(intent);
    }
}
