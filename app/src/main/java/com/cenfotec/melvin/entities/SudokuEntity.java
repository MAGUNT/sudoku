package com.cenfotec.melvin.entities;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;
@IgnoreExtraProperties
public class SudokuEntity implements Serializable {


    private String id;

    private List<SudokuCellEntity> sudokuBoard;
    private long time;
    private long moveCount;

    public SudokuEntity() {
        this(null);
    }

    public SudokuEntity(List<SudokuCellEntity> sudoku) {
        setSudokuBoard(sudoku);
        setTime(0);
        setMoveCount(0);
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

    @Exclude
    public String getId() {
        return id;
    }

    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    public List<SudokuCellEntity> getSudokuBoard() {
        return sudokuBoard;
    }

    public void setSudokuBoard(List<SudokuCellEntity> sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
    }
}
