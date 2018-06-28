package com.cenfotec.melvin.dal;

import com.cenfotec.melvin.entities.SudokuEntity;
import com.cenfotec.melvin.entities.SudokuReduce;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SudokuRepo {

    private static String SUDOKU_COLLECTION = "sudoku";
    private static FirebaseFirestore db     = FirebaseFirestore.getInstance();


    public static Task<SudokuEntity> saveSudoku(SudokuEntity sudoku) {
        return db.collection(SUDOKU_COLLECTION).add(sudoku)
                .continueWithTask(docRef -> docRef.getResult().get())
                .continueWith(SudokuRepo::toSudokuEnitity);
    }

    public static Task<Void> updateSudoku(SudokuEntity sudoku) {
        return db.collection(SUDOKU_COLLECTION)
                .document(sudoku.getId())
                .set(sudoku);
    }

    public static Task<SudokuEntity>  getSudoku(String id) {

        return db.collection(SUDOKU_COLLECTION)
                .document(id)
                .get()
                .continueWith(SudokuRepo::toSudokuEnitity);
    }

    private static SudokuEntity toSudokuEnitity(Task<DocumentSnapshot> docTask) {
        DocumentSnapshot doc  = docTask.getResult();
        SudokuEntity sudoku = doc.toObject(SudokuEntity.class);
        sudoku.setId(doc.getId());
        return sudoku;
    }



    public static Task<List<SudokuReduce>> getAllSudokus() {
        return db.collection(SUDOKU_COLLECTION).get()
                .continueWith(SudokuRepo::mapQuery);
    }

    private static List<SudokuReduce> mapQuery(Task<QuerySnapshot> task) {
        List<SudokuReduce> list = new ArrayList<>();
        for (DocumentSnapshot document : task.getResult()) {
            SudokuReduce sudoku = document.toObject(SudokuReduce.class);
            sudoku.setId(document.getId());
            list.add(sudoku);
        }
        return list;
    }
}
