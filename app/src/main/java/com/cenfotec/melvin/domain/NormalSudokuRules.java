
package com.cenfotec.melvin.domain;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author melvin
 */
public class NormalSudokuRules implements SudokuRules {


    private final static String[] ROW_SECTORS   = {"123", "456", "789"};
    private final static String[] COL_SECTORS   = {"ABC", "DEF", "GHI"};
    private final static String ROWS     = "123456789";
    private final static String COLS     = "ABCDEFGHI";
    private final static int CELLS_COUNT = ROWS.length() * COLS.length();

    private final static String DIGITS  = "123456789";


    private final List<String> squares;
    private final List<Set<String>> unitList;
    private final Map<String, List<Set<String>>> unitTable;
    private final Map<String, Set<String>> peerTable;

    private static NormalSudokuRules instance = new  NormalSudokuRules();

    public static NormalSudokuRules getInstance() { return instance; }


    public NormalSudokuRules() {
        this.squares   = crossList(ROWS, COLS);
        this.unitList  = initUnitList(ROWS, COLS);
        this.unitTable = createUnitTable(squares, unitList);
        this.peerTable = createPeerTable(unitTable, squares);

    }

    private void cross(Collection<String> col, String s, String r) {
        for(int i = 0; i <  s.length(); ++i) {
            for(int j = 0; j <  r.length(); ++j) {
                col.add("" + s.charAt(i) + r.charAt(j));
            }
        }
    }

    private List<String> crossList(String s, String r) {
        List<String> list = new ArrayList<>();
        cross(list, s, r);
        return Collections.unmodifiableList(list);
    }

    private Set<String> cross(String s, String r) {
        Set<String> set = new HashSet<>();
        cross(set, s, r);
        return Collections.unmodifiableSet(set);
    }

    private List<Set<String>> initUnitList(final String rows, final String cols) {
        List<Set<String>> initUnitList = new ArrayList<>();
        for (int i = 0; i <  cols.length(); ++i) {
            initUnitList.add(cross(rows, String.valueOf(cols.charAt(i))));
        }
        for (int i = 0; i <  rows.length(); ++i) {
            initUnitList.add(cross(String.valueOf(rows.charAt(i)), cols));
        }
        for (String rowSquare: ROW_SECTORS) {
            for (String colsSquare: COL_SECTORS) {
                initUnitList.add(cross(rowSquare, colsSquare ));
            }
        }
        return Collections.unmodifiableList(initUnitList);
    }

    private Map<String, List<Set<String>>> createUnitTable(
            List<String> squares,
            List<Set<String>> unitList) {

        Map<String, List<Set<String>>> initUnitTable = new HashMap<>();
        for (String square: squares) {
            List<Set<String>> list = new ArrayList<>();
            for (Set<String> u : unitList) {
                if(u.contains(square)) {
                    list.add(u);
                }
            }

            initUnitTable.put(square,
                    Collections.unmodifiableList(list));
        }
        return initUnitTable;
    }

    private Map<String, Set<String>> createPeerTable(
            final Map<String, List<Set<String>>> unitTable,
            final List<String> squares) {

        Map<String, Set<String>> initPeerTable = new HashMap<>();
        for (String square: squares) {
            Set<String> peers = new HashSet<>();
            for(Set<String> unit: unitTable.get(square)) {
                peers.addAll(unit);
            }
            peers.remove(square);
            initPeerTable.put(square,
                    Collections.unmodifiableSet(peers));

        }
        return initPeerTable;
    }

    @Override
    public List<String> getSquares() {
        return squares;
    }

    @Override
    public List<Set<String>> getUnitList() {
        return unitList;
    }

    @Override
    public List<Set<String>> getUnits(final String square) {
        return unitTable.get(square);
    }

    @Override
    public Set<String> getPeers(final String square) {
        return peerTable.get(square);
    }

    @Override
    public String getDigits() {
        return DIGITS;
    }

    @Override
    public Map<String, String> createPosibilityTable() {
        Map<String, String> table = new HashMap<>();
        for(String sq : squares) {
            table.put(sq, DIGITS);
        }
        return table;
    }

    public String squareFromNumber(final int pos) {
        if(pos < 0  && pos > CELLS_COUNT) {
            throw new IllegalArgumentException("Invalid position");
        }
        return  "" + ROWS.charAt(pos / COLS.length())
                + COLS.charAt(pos % ROWS.length());
    }


    public int numberFromSquare(final String pos) {
        if(pos.length() != 2) {
            throw new IllegalArgumentException("Invalid position");
        }
        return  ((pos.charAt(0) - ROWS.charAt(0)) * COLS.length())
                + (pos.charAt(1) - COLS.charAt(0));
    }

}
