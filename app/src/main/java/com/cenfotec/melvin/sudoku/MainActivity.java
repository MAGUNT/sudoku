package com.cenfotec.melvin.sudoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;


import com.cenfotec.melvin.bll.SudokuManager;


public class MainActivity extends AppCompatActivity {

    private static String SUDOKU = "SUDOKU";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void toSudoku(View view) {
        SudokuManager.generateSudoku()
                .addOnSuccessListener(this, sudoku -> {
                    Intent intent = new Intent(this, Sudoku.class);
                    intent.putExtra(SUDOKU, sudoku.getId());
                    this.startActivity(intent);
                });
    }


}
