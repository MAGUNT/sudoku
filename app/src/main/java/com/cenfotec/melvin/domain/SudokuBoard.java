package com.cenfotec.melvin.domain;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

//re implementar con enum set
public class SudokuBoard {

    private final List<SudokuDigits> board;
    private final SudokuRules rules;

    public SudokuBoard() {
        this.rules  = NormalSudokuRules.INSTANCE;
       this.board = createBoard(this.rules);
    }

    private List<SudokuDigits> createBoard(SudokuRules util) {
       return Stream.iterate(SudokuDigits.EMPTY, s -> s)
                .limit(rules.squaresSize())
                .collect(Collectors.toList());
    }

    public List<Integer> add(int position, String value) {
        SudokuDigits digit = SudokuDigits.fromText(value);
        List<Integer> list  = getInvalid(position, digit);
        if(digit == SudokuDigits.EMPTY || list.isEmpty()) {
            board.set(position, digit);
        }
        return list;
    }

    private List<Integer> getInvalid(final int position, final SudokuDigits digit) {
        return rules.getPeers(position)
                .filter(s -> digit != SudokuDigits.EMPTY)
                .filter(s -> board.get(s) == digit)
                .boxed()
                .collect(Collectors.toList());

    }

    public String getValue(int position) {
        return board.get(position).getSudokuText();
    }

    public int size() {
        return board.size();
    }

}
