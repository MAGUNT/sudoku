package com.cenfotec.melvin.entities;

import com.google.firebase.firestore.Exclude;

import java.util.Locale;

public class SudokuReduce {

    private long time;
    private long moveCount;
    private String id;

    @Exclude
    private String formatTime;


    public SudokuReduce() { }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
        this.formatTime = calculateFormatedTime();
    }

    public long getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(long moveCount) {
        this.moveCount = moveCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Exclude
    private String calculateFormatedTime() {
        final long MILI_TO_SEC_FACTOR = 1000;
        final long SEC_MIN_CYCLE      = 60;
        long second = (time / MILI_TO_SEC_FACTOR) % SEC_MIN_CYCLE;
        long minute = (time / (MILI_TO_SEC_FACTOR * SEC_MIN_CYCLE)) % SEC_MIN_CYCLE;
        long hour   = time / (MILI_TO_SEC_FACTOR * SEC_MIN_CYCLE * SEC_MIN_CYCLE);

       return String.format(Locale.US,
               "%02d:%02d:%02d",
               hour, minute, second);
    }

    @Exclude
    public String getFormatTime() {
        return formatTime;
    }
}
