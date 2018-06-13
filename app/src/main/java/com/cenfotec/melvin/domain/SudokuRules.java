package com.cenfotec.melvin.domain;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SudokuRules {
    List<String> getSquares();
    List<Set<String>> getUnitList();
    List<Set<String>> getUnits(final String square);
    Set<String> getPeers(final String square);
    Map<String, String> createPosibilityTable();
    String getDigits();
    public String squareFromNumber(final int pos);
    public int numberFromSquare(final String pos);
}
