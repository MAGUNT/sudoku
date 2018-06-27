package com.cenfotec.melvin.dal;

import com.cenfotec.melvin.entities.SudokuEntity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SudokuRepo {

    private static String SUDOKU_COLLECTION = "sudoku";
    private static FirebaseFirestore db     = FirebaseFirestore.getInstance();


    public static Task<DocumentReference> saveSudoku(SudokuEntity sudoku) {
        return db.collection(SUDOKU_COLLECTION).add(sudoku);
    }

    public Task<Void> updateSudoku(SudokuEntity sudoku) {
        return db.collection(SUDOKU_COLLECTION)
                .document(sudoku.getId())
                .set(sudoku);
    }

    public static Task<DocumentSnapshot>  getSudoku(String id) {

        return db.collection(SUDOKU_COLLECTION)
                .document(id).get();
    }
}
