package com.cenfotec.melvin.domain;

import android.support.annotation.NonNull;
import android.util.TimingLogger;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SudokuBoard {

    private final List<SudokuCell> board;
    private final SudokuRules rules;

    public static SudokuBoard generateBoard() {
        SudokuRules rules = NormalSudokuRules.INSTANCE;
        SudokuGenerator generator = new ManySolutionsSudokuGenerator(rules);
        return new SudokuBoard(generator.generateSudoku(), rules);
    }

    public static SudokuBoard createBoard(@NonNull final List<SudokuDigits> board) {
        SudokuRules rules = NormalSudokuRules.INSTANCE;
        return new SudokuBoard(board, rules);
    }

    private SudokuBoard(@NonNull final List<SudokuDigits> board, final SudokuRules rules) {
        if(board.size() !=  rules.squaresSize()) {
            throw new IllegalArgumentException();
        }
        this.rules = rules;
        this.board = board.stream()
                .map(d -> new SudokuCell(d, d == SudokuDigits.EMPTY))
                .collect(Collectors.toList());
    }

    private List<SudokuDigits> createBoard(final SudokuRules util) {
       return Stream.iterate(SudokuDigits.EMPTY, s -> s)
                .limit(rules.squaresSize())
                .collect(Collectors.toList());
    }

    public List<Integer> add(final int position, final String value) {
        SudokuDigits digit = SudokuDigits.fromText(value);
        List<Integer> list  = getInvalid(position, digit);
        if(digit == SudokuDigits.EMPTY || list.isEmpty()) {
            board.get(position).setDigit(digit);
        }
        return list;
    }

    public boolean solve() {
        List<SudokuDigits> mapedBoard = board
                .stream()
                .map(SudokuCell::getDigit)
                .collect(Collectors.toList());
        Optional<List<SudokuDigits>> solved =
                new SudokuSolver(rules, mapedBoard).solve();


        solved.ifPresent( s ->  IntStream.range(0, rules.squaresSize())
                .peek(i -> board.get(i).setDigit(s.get(i)))
                .forEach(i -> board.get(i).setEnable(false)));
        return solved.isPresent();
    }


    private List<Integer> getInvalid(final int position, final SudokuDigits digit) {
        return rules.getPeers(position)
                .filter(s -> digit != SudokuDigits.EMPTY)
                .filter(s -> board.get(s).getDigit() == digit)
                .boxed()
                .collect(Collectors.toList());
    }

    public String getValue(final int position) {
        return board.get(position).getDigit().getSudokuText();
    }

    public boolean isEnable(final int position) {
        return board.get(position).isEnable();
    }

    public int size() {
        return board.size();
    }

    private final class SudokuCell {
        private SudokuDigits digit;
        private boolean isEnable;

        private SudokuCell(@NonNull SudokuDigits digit, boolean isEnable) {
            this.digit    = digit;
            this.isEnable = isEnable;
        }


        public SudokuDigits getDigit() {
            return digit;
        }

        public void setDigit(final SudokuDigits digit) {
            if(!isEnable()) {
                return;
            }
            this.digit = digit;
        }

        public boolean isEnable() {
            return isEnable;
        }

        public void setEnable(final boolean enable) {
            isEnable = enable;
        }
    }
}
