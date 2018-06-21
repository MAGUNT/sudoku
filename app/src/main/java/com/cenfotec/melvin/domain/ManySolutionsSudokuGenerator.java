package com.cenfotec.melvin.domain;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ManySolutionsSudokuGenerator implements SudokuGenerator{

    private static final int MIN_SIZE = 54 ;
    private static final int MAX_SIZE = 64;
    private final SudokuSolver solver;
    private final List<Integer> positions;

    public ManySolutionsSudokuGenerator(@NonNull SudokuRules rules) {
        this.solver =  new SudokuSolver(rules);
        this.positions = IntStream.range(0, rules.squaresSize())
                .boxed().collect(Collectors.toList());
    }

    // cuando genere sudokus que garantizen una unica solucion,
    // podria guardar la solucion. Pero esta solucion aun no lo garantiza.
    @Override
    public List<SudokuDigits> generateSudoku() {

        final List<SudokuDigits> solved = solver.solve().get();
        final int size = new Random().nextInt((MAX_SIZE - MIN_SIZE) + 1) + MIN_SIZE;

        Collections.shuffle(positions);
        positions.stream()
                .limit(size)
                .forEach(i -> solved.set(i, SudokuDigits.EMPTY));
        return solved;
    }
}
