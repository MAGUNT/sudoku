package com.cenfotec.melvin.domain;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


public class SudokuBoard {

    private final char EMPTY = '.';
    private final Map<String, Character> board;
    private final SudokuRules util;

    public SudokuBoard() {
        this.util  = NormalSudokuRules.getInstance();
       this.board = createBoard(this.util);
    }

    private Map<String, Character> createBoard(SudokuRules util) {
        Map<String, Character> board = new HashMap<>();
        for(String square: util.getSquares()) {
            board.put(square, EMPTY);
        }
        return board;
    }

    public List<Integer> add(int position, Character value) {
        String mapPos = util.squareFromNumber(position);
        List<Integer> list  = isValid(mapPos, value);
        if(list.isEmpty()) {
            board.put(mapPos, value);
        }
        return list;
    }

    public void remove(int position) {
        String mapPos = util.squareFromNumber(position);
        board.put(mapPos, EMPTY);
    }

    private List<Integer> isValid(String position, final Character value) {
        List<Integer> invalid = new ArrayList();
        for(String pos : util.getPeers(position)) {
            if(board.get(pos) == value) {
                invalid.add(util.numberFromSquare(pos));
            }
        }
        return invalid;
    }

    public Map<Integer, String> solve() {
       Map<String, String> solved = new SudokuSolver(util, board).solve();
       Map<Integer, String> maped = new Hashtable<>();
       for(Map.Entry<String, String> e : solved.entrySet()) {
           maped.put(util.numberFromSquare(e.getKey()), e.getValue());
       }
       return maped;
    }

    public String toStringPos(int pos) {
        return util.squareFromNumber(pos);
    }
    public int cellCount() {
        return util.getSquares().size();
    }

}
