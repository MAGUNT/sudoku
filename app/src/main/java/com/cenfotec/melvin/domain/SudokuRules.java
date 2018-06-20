package com.cenfotec.melvin.domain;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/** Mejorar esta interfaze para no tener que exponer el estado*/
public interface SudokuRules {
    IntStream getSquares();
    Stream<IntStream> getUnitList();
    Stream<IntStream> getUnits(final int square);
    IntStream getPeers(final int square);
    EnumSet<SudokuDigits> getDigits();
    int squaresSize();
}
