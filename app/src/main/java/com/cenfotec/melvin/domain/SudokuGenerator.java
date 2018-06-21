package com.cenfotec.melvin.domain;

import java.util.List;

@FunctionalInterface
public interface SudokuGenerator {
    List<SudokuDigits> generateSudoku();
}
