package com.cenfotec.melvin.sudoku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.cenfotec.melvin.com.cenfotec.melvin.views.SudokuAdapter;
import com.cenfotec.melvin.domain.SudokuBoard;

public class Sudoku extends AppCompatActivity {

    private SudokuAdapter sa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        this.sa = new SudokuAdapter(this, new SudokuBoard());
        GridView gridview =  findViewById(R.id.sudokuGridView);
        gridview.setAdapter(sa);

    }

    public void solve(View view) {

        if(sa.solve()) {
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
