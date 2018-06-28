package com.cenfotec.melvin.sudoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;


import com.cenfotec.melvin.bll.SudokuManager;


public class MainActivity extends AppCompatActivity {

    private static String SUDOKU = "SUDOKU";
    private Button generate;
    private Button listar;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.listar = findViewById(R.id.listar);
        this.generate = findViewById(R.id.generar);
        this.progressBar = findViewById(R.id.processBar);
    }


    public void toSudoku(View view) {

        this.progressBar.setVisibility(View.VISIBLE);
        this.generate.setEnabled(false);
        this.listar.setEnabled(false);
        SudokuManager.createSudoku()
                .addOnSuccessListener(this, sudoku -> {
                    Intent intent = new Intent(this, Sudoku.class);
                    intent.putExtra(SUDOKU, sudoku.getId());
                    this.progressBar.setVisibility(View.INVISIBLE);
                    this.startActivity(intent);
                });
    }

    public void toSudokuList(View view) {
        Intent intent = new Intent(this, SudokuList.class);
        this.startActivity(intent);
    }


}
