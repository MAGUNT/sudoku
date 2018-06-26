package com.cenfotec.melvin.entities;

import com.cenfotec.melvin.domain.SudokuDigits;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class SudokuCellEntity implements Serializable {


    @Exclude
    private SudokuDigits digit;
    private boolean isEnable;

    public SudokuCellEntity(SudokuDigits digit, boolean isEnable) {
        this.digit    = digit;
        this.isEnable = isEnable;
    }

    public SudokuCellEntity() {
        this(null, false);
    }

    @Exclude
    public SudokuDigits getDigitEnum() {
        return digit;
    }

    @Exclude
    public void setDigitEnum(final SudokuDigits digit) {
        this.digit = digit;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(final boolean enable) {
        isEnable = enable;
    }


    public String getDigit(){
        if (digit == null){
            return null;
        }
        return digit.name();
    }


    public void setDigit(String digit){
        this.digit = (digit == null)
                ? null
                : SudokuDigits.valueOf(digit);
    }
}