package com.cenfotec.melvin.bll;

import com.cenfotec.melvin.dal.SudokuRepo;
import com.cenfotec.melvin.domain.ManySolutionsSudokuGenerator;
import com.cenfotec.melvin.domain.NormalSudokuRules;
import com.cenfotec.melvin.domain.SudokuDigits;
import com.cenfotec.melvin.domain.SudokuGenerator;
import com.cenfotec.melvin.domain.SudokuRules;
import com.cenfotec.melvin.entities.SudokuCellEntity;
import com.cenfotec.melvin.entities.SudokuEntity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SudokuManager {


    public static Task<SudokuEntity> generateSudoku() {

        SudokuRules rules = NormalSudokuRules.INSTANCE;
        SudokuGenerator generator = new ManySolutionsSudokuGenerator(rules);
        List<SudokuCellEntity> board = generator.generateSudoku().stream()
                .map(d -> new SudokuCellEntity(d, d == SudokuDigits.EMPTY))
                .collect(Collectors.toList());

        return SudokuRepo.saveSudoku(new SudokuEntity(board))
                .continueWithTask(docRef -> docRef.getResult().get())
                .continueWith(SudokuManager::toSudokuEnitity);
    }



    private static SudokuEntity toSudokuEnitity(Task<DocumentSnapshot> docTask) {
        DocumentSnapshot doc  = docTask.getResult();
        SudokuEntity sudoku = doc.toObject(SudokuEntity.class);
        sudoku.setId(doc.getId());
        return null;
    }


    public static Task<SudokuEntity> getSudokuById(String id) {
        return SudokuRepo.getSudoku(id)
                .continueWith(SudokuManager::toSudokuEnitity);
    }

}
