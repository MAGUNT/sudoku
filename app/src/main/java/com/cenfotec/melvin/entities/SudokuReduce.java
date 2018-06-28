package com.cenfotec.melvin.entities;

import com.cenfotec.melvin.sudoku.Sudoku;

public class SudokuReduce {

    private long time;
    private long moveCount;
    private String id;


    public SudokuReduce() {
        setTime(0);
        setMoveCount(0);
        setId(null);
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(long moveCount) {
        this.moveCount = moveCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
