package com.cenfotec.melvin.domain;

public enum SudokuDigits {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    EMPTY("");

    private final static SudokuDigits[] values = SudokuDigits.values();
    private final String sudokuText;

    SudokuDigits(String sudokuText) {
        this.sudokuText = sudokuText;
    }

    public String getSudokuText() {
        return sudokuText;
    }

    public static SudokuDigits fromText(final String text) {
        if (text.isEmpty() || text.length() > 1) {
            return EMPTY;
        }
        return fromText(text.charAt(0));
    }

    public static SudokuDigits fromText(final char c) {
        if (!Character.isDigit(c) || c == '0') {
            return EMPTY;
        }
        return values[c - '1'];
    }

    public static SudokuDigits fromInt(final int c) {
        return fromText((char) c);
    }
}
