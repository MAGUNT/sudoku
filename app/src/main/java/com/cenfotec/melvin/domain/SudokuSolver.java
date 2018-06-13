package com.cenfotec.melvin.domain;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class SudokuSolver {

    private final SudokuRules util;
    private Map<String, String> table;

    public SudokuSolver(SudokuRules util, String grid) {
        this(util, parseGrid(grid, util));
    }

    public SudokuSolver(SudokuRules util, Map<String, Character> grid) {
        this.util  = util;
        this.table = propagateConstrains(grid);
    }

    private static Map<String, Character> parseGrid(String grid, SudokuRules util) {
        Map<String, Character> map = new HashMap();
        int i = 0;
        for(String square : util.getSquares()) {
            while(i < grid.length() &&
                    !Character.isDigit(grid.charAt(i)) &&
                    grid.charAt(i) != '.'
                    &&   grid.charAt(i) != '0') {
                ++i;
            }
            if( i >=  grid.length()) {
                throw new IllegalArgumentException();
            }
            map.put(square,  grid.charAt(i++));
        }
        return map;
    }


    private Map<String, String> propagateConstrains(Map<String, Character> grid) {
        Map<String, String> table = util.createPosibilityTable();
        for (Map.Entry<String, Character> e : grid.entrySet()) {
            if(e.getValue() != '0' && Character.isDigit(e.getValue())
                    && set(table, e.getKey(), e.getValue()) == null) {
                return null;
            }
        }
        return table;
    }

    private Map<String, String> set(Map<String, String> table,
                                    String square,
                                    char digit) {

        String remove = table.get(square);
        for(int i = 0; i < remove.length(); ++i) {
            char d = remove.charAt(i);
            if(digit != d && !delete(table, square, d)) {
                return null;
            }
        }
        return table;
    }

    public Map<String, String> solve() {
        Map<String, String> solved = solve(table);
        return solved == null ? new Hashtable<String, String>() : solved;
    }

    private Map<String, String> solve(Map<String, String> table) {
        if(table == null || isSolve(table)) {
            return table;
        }
        String square = minEntry(table);
        String value  = table.get(square);
        for(char c : shuffleArray(value)) {
            Map<String, String> copy = new HashMap<>(table);
            Map<String, String> solved =
                    solve(set(copy, square, c));
            if(solved != null) {
                return solved;
            }
        }
        return null;
    }

    private char[] shuffleArray(final String string) {
        final Random random = new Random();
        char[] array = string.toCharArray();
        for (int i = array.length - 1; i > 0; i--) {
            swap(array, random.nextInt(i + 1), i);
        }
        return array;
    }

    private void swap(char[] array, int i, int j) {
        char temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private String minEntry(
            Map<String, String> table) {
        int min = Integer.MAX_VALUE;
        String minEntry = null;
        for(Map.Entry<String, String> e : table.entrySet()) {
            final int length = e.getValue().length();
            if(length >  1 && length < min) {
                min      = length;
                minEntry = e.getKey();
            }
        }
        return minEntry;
    }

    private boolean isSolve(Map<String, String> table) {
        for(Map.Entry<String, String> e : table.entrySet()) {
            if(e.getValue().length() !=  1) {
                return false;
            }
        }
        return true;
    }

    private boolean delete(Map<String, String> table,
                           String square,
                           char digit) {

        String sdigit = String.valueOf(digit);
        if(!table.get(square).contains(sdigit)) {
            return true;
        }

        table.put(square, table.get(square).replace(sdigit, ""));

        if(table.get(square).length() == 0) {
            return false;
        }
        else if(table.get(square).length() == 1) {
            char lastDigit = table.get(square).charAt(0);
            for (String peer : util.getPeers(square)) {
                if (!delete(table, peer, lastDigit)) {
                    return false;
                }
            }
        }
        for (Set<String> u : util.getUnits(square)) {
            int counter = 0;
            String last = "";
            for (String s : u) {
                if(table.get(s).contains(sdigit)) {
                    ++counter;
                    last = s;
                }
            }
            if(counter == 0
                    ||(counter == 1
                    && set(table, last, digit) == null)) {
                return false;
            }
        }

        return true;
    }
}
