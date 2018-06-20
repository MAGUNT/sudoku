
package com.cenfotec.melvin.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author melvin
 */
public enum NormalSudokuRules implements SudokuRules {

    INSTANCE;

    private final int ROW_COL_SIZE      = 9;
    private final int NUMBER_OF_SQUARES = ROW_COL_SIZE * ROW_COL_SIZE;

    private final List<Integer>            squares;
    private final List<Set<Integer>>       unitList;
    private final List<List<Set<Integer>>> unitTable;
    private final List<Set<Integer>>       peerTable;


    NormalSudokuRules() {
        squares   = IntStream
                .range(0, NUMBER_OF_SQUARES)
                .boxed()
                .collect(Collectors.toList());

        unitList  = createUnits();
        unitTable = unitTable(squares, unitList);
        peerTable = peers(squares, unitTable);

    }


    @Override
    public Stream<IntStream> getUnits(int integer) {
        return  unitTable.get(integer).stream()
                .map( s -> s.stream().mapToInt(Integer::intValue));
    }


    @Override
    public IntStream getPeers(int integer) {
        return peerTable.get(integer).stream()
                .mapToInt(Integer::intValue);

    }

    @Override
    public IntStream getSquares() {
        return IntStream.range(0, NUMBER_OF_SQUARES);
    }

    private List<Set<Integer>> createUnits() {
        Stream<IntStream> cols = rowsCols()
                .mapToObj(i -> cross(rowsCols(), IntStream.of(i)));
        Stream<IntStream> rows = rowsCols()
                .mapToObj(i -> cross(IntStream.of(i), rowsCols()));

        List<List<Integer>> sectorList = unitSectors();
        Stream<IntStream> sectors = sectorList
                .stream()
                .flatMap(r -> sectorList.stream()
                        .map( r2 -> cross(intStream(r), intStream(r2))));

        return Stream.concat(sectors, Stream.concat(cols, rows))
                .map(unit -> unit.boxed()
                        .collect(Collectors.toSet()))
                .collect(Collectors.toList());
    }

    private List<List<Integer>> unitSectors() {
        return Arrays.asList(
                toList(IntStream.range(0, 3)),
                toList(IntStream.range(3, 6)),
                toList(IntStream.range(6, ROW_COL_SIZE)));
    }

    private List<Integer> toList(IntStream stream) {
        return stream.boxed().collect(Collectors.toList());
    }

    private IntStream intStream(List<Integer> s) {
        return s.stream().mapToInt(i -> i);
    }

    private List<List<Set<Integer>>> unitTable(
            List<Integer> squares,
            List<Set<Integer>> units) {
        return squares.stream()
                .map(i -> units.stream()
                        .filter(u -> u.contains(i))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private List<Set<Integer>> peers(
            List<Integer> squares,
            List<List<Set<Integer>>> unitTable) {
        return squares.stream()
                .map(i -> unitTable.get(i)
                        .stream()
                        .flatMap(Set::stream)
                        .filter(s -> !s.equals(i))
                        .collect(Collectors.toSet()))
                .map(Collections::unmodifiableSet)
                .collect(Collectors.toList());
    }

    private  IntStream cross(IntStream rows, IntStream cols) {
        List<Integer> list = cols
                .boxed()
                .collect(Collectors.toList());
        return rows
                .flatMap(r -> list
                        .stream()
                        .mapToInt(c -> calculatePos(r, c)));
    }

    private int calculatePos(final int row, final int col) {
        return row * ROW_COL_SIZE + col;
    }

    private IntStream rowsCols() {
        return IntStream.range(0, ROW_COL_SIZE);
    }

    @Override
    public Stream<IntStream> getUnitList() {
        return unitList.stream()
                .map( s -> s.stream().mapToInt(Integer::intValue));
    }


    @Override
    public EnumSet<SudokuDigits> getDigits() {
        return EnumSet.range(SudokuDigits.ONE, SudokuDigits.NINE);
    }

    @Override
    public int squaresSize() {
        return NUMBER_OF_SQUARES;
    }
}
