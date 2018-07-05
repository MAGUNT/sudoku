package com.cenfotec.melvin.bll;

import android.support.annotation.NonNull;


import com.cenfotec.melvin.domain.NormalSudokuRules;
import com.cenfotec.melvin.domain.SudokuDigits;
import com.cenfotec.melvin.domain.SudokuRules;
import com.cenfotec.melvin.domain.SudokuSolver;
import com.cenfotec.melvin.entities.SudokuCellEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SudokuBoard {

    private final List<SudokuCellEntity> board;
    private final SudokuRules rules;

    public static SudokuBoard createBoard(@NonNull final List<SudokuCellEntity> board) {
        SudokuRules rules = NormalSudokuRules.INSTANCE;
        return new SudokuBoard(board, rules);
    }

    private SudokuBoard(@NonNull final List<SudokuCellEntity> board, @NonNull final SudokuRules rules) {
        if(board.size() !=  rules.squaresSize()) {
            throw new IllegalArgumentException();
        }
        this.rules = rules;
        this.board = board;
    }

    private List<SudokuDigits> createBoard(@NonNull final SudokuRules util) {
       return Stream.iterate(SudokuDigits.EMPTY, s -> s)
                .limit(rules.squaresSize())
                .collect(Collectors.toList());
    }

    public List<Integer> add(final int position, final String value) {
        SudokuDigits digit = SudokuDigits.fromText(value);
        List<Integer> list = getInvalid(position, digit);
        SudokuCellEntity cell =  board.get(position);
        if(cell.isEnable() &&  list.isEmpty()) {
            board.get(position).setDigitEnum(digit);
        }
        return list;
    }

    public boolean solve() {
        List<SudokuDigits> mapedBoard = board
                .stream()
                .map(SudokuCellEntity::getDigitEnum)
                .collect(Collectors.toList());
        Optional<List<SudokuDigits>> solved =
                new SudokuSolver(rules, mapedBoard).solve();


        solved.ifPresent( s ->  IntStream.range(0, rules.squaresSize())
                .peek(i -> board.get(i).setDigitEnum(s.get(i)))
                .forEach(i -> board.get(i).setEnable(false)));
        return solved.isPresent();
    }


    private List<Integer> getInvalid(final int position, final SudokuDigits digit) {
        return rules.getPeers(position)
                .filter(s -> digit != SudokuDigits.EMPTY)
                .filter(s -> board.get(s).getDigitEnum() == digit)
                .boxed()
                .collect(Collectors.toList());
    }

    public String getValue(final int position) {
        return board.get(position).getDigitEnum().getSudokuText();
    }

    public boolean isEnable(final int position) {
        return board.get(position).isEnable();
    }

    public int size() {
        return board.size();
    }

    public List<SudokuCellEntity> getBoard() {
        return board;
    }


}
