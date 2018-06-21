package com.cenfotec.melvin.domain;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SudokuSolver {

    private final SudokuRules rules;
    private final List<EnumSet<SudokuDigits>> table;

    public SudokuSolver(SudokuRules rules, @NonNull String grid) {
        this(rules, parseGrid(grid, rules));
    }

    public SudokuSolver(@NonNull SudokuRules rules,
                        @NonNull List<SudokuDigits> grid) {
        this.rules = rules;
        this.table = propagateConstrains(grid);
    }

    public SudokuSolver(@NonNull SudokuRules rules) {
        this.rules = rules;
        this.table = createPosibilityTable();
    }

    List<EnumSet<SudokuDigits>> createPosibilityTable() {
        return Stream.iterate(rules.getDigits(), s -> s)
                .limit(rules.squaresSize())
                .collect(Collectors.toList());
    }

    private static List<SudokuDigits> parseGrid(String grid, SudokuRules rules) {
        List<SudokuDigits> parseGrid = grid.chars()
                .mapToObj(c -> (char) c)
                .filter(c -> (Character.isDigit(c) && c != '0') || c == '.' || c == '0')
                .map(SudokuDigits::fromText)
                .limit(rules.squaresSize())
                .collect(Collectors.toList());

        if (parseGrid.size() < rules.squaresSize()) {
            throw new IllegalArgumentException();
        }
        return parseGrid;
    }

    private List<EnumSet<SudokuDigits>> propagateConstrains(List<SudokuDigits> grid) {
        List<EnumSet<SudokuDigits>> tableValues = createPosibilityTable();
        boolean isValid = IntStream.range(0, grid.size())
                .allMatch(i -> grid.get(i) == SudokuDigits.EMPTY
                        || set(tableValues, i, grid.get(i)) != null);


        return isValid ? tableValues : null;
    }

    private List<EnumSet<SudokuDigits>> set(
            List<EnumSet<SudokuDigits>> table,
            int square,
            SudokuDigits digit) {

        EnumSet<SudokuDigits> remove = table.get(square);
        boolean isSet = remove.stream()
                .filter(d -> digit != d)
                .allMatch(d -> delete(table, square, d));

        return isSet ? table : null;
    }

    public Optional<List<SudokuDigits>> solve() {
         return Optional.ofNullable(solve(table))
                 .map(sudoku -> sudoku.stream()
                         .map(d -> d.stream().findAny().get())
                         .collect(Collectors.toList()));
    }

    private List<EnumSet<SudokuDigits>> solve(List<EnumSet<SudokuDigits>> table) {
        if (table == null || isSolve(table)) {
            return table;
        }
        int square = minEntry(table);
        for (SudokuDigits c : shuffle(table.get(square))) {
            List<EnumSet<SudokuDigits>> copy = new ArrayList<>(table);
            List<EnumSet<SudokuDigits>> solved
                    = solve(set(copy, square, c));
            if (solved != null) {
                return solved;
            }
        }
        return null;
    }

    private List<SudokuDigits> shuffle(EnumSet<SudokuDigits> set) {
        List<SudokuDigits> list = new ArrayList<>(set);
        Collections.shuffle(list);
        return list;
    }

    private int minEntry(List<EnumSet<SudokuDigits>> table) {
        return IntStream.range(0, table.size())
                .filter(i -> table.get(i).size() > 1)
                .reduce((min, i) -> {
                    int minSize = table.get(min).size();
                    int current = table.get(i).size();
                    return current < minSize ? i : min;
                }).getAsInt();
    }

    private boolean isSolve(List<EnumSet<SudokuDigits>> table) {
        return table.stream().allMatch(d -> d.size() == 1);
    }

    private boolean delete(
            List<EnumSet<SudokuDigits>> table,
            int square,
            SudokuDigits digit) {

        if (!table.get(square).contains(digit)) {
            return true;
        }
        EnumSet<SudokuDigits> digits
                = EnumSet.copyOf(table.get(square));
        digits.remove(digit);
        table.set(square, digits);

        return !digits.isEmpty()
                && deleteFromPeers(table, square)
                && rules.getUnits(square)
                .allMatch(u -> unitCheck(table, u, digit));
    }

    private boolean unitCheck(
            List<EnumSet<SudokuDigits>> table,
            IntStream unit,
            SudokuDigits digit) {


        List<Integer> posible = unit
                .filter(s -> table.get(s).contains(digit))
               .limit(2)
                .boxed()
               .collect(Collectors.toList());

        return !(posible.isEmpty()
                || (posible.size() == 1
                && set(table, posible.get(0), digit) == null));
    }

    private boolean deleteFromPeers(
            List<EnumSet<SudokuDigits>> table,
            int square) {
        EnumSet<SudokuDigits> digits = table.get(square);
        if (digits.size() != 1) {
            return true;
        }
        SudokuDigits lastDigit = digits.stream()
                .findAny().get();
        return rules.getPeers(square)
                .allMatch(p -> delete(table, p, lastDigit));
    }

}
