package com.cenfotec.melvin.bll;

import com.cenfotec.melvin.dal.SudokuRepo;
import com.cenfotec.melvin.domain.ManySolutionsSudokuGenerator;
import com.cenfotec.melvin.domain.NormalSudokuRules;
import com.cenfotec.melvin.domain.SudokuDigits;
import com.cenfotec.melvin.domain.SudokuGenerator;
import com.cenfotec.melvin.domain.SudokuRules;
import com.cenfotec.melvin.entities.SudokuCellEntity;
import com.cenfotec.melvin.entities.SudokuEntity;
import com.cenfotec.melvin.entities.SudokuReduce;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SudokuManager {


    public static Task<SudokuEntity> createSudoku() {
       return Tasks.call(SudokuManager::generateSudoku)
                .continueWithTask(SudokuManager::saveSudoku);
    }

    private static Task<SudokuEntity> saveSudoku(Task<List<SudokuCellEntity>> boardTask) {
        List<SudokuCellEntity> board = boardTask.getResult();
       return SudokuRepo.saveSudoku(new SudokuEntity(board));
    }

    private static List<SudokuCellEntity> generateSudoku() {
        SudokuRules rules = NormalSudokuRules.INSTANCE;
        SudokuGenerator generator = new ManySolutionsSudokuGenerator(rules);
        return generator.generateSudoku().stream()
                .map(d -> new SudokuCellEntity(d, d == SudokuDigits.EMPTY))
                .collect(Collectors.toList());
    }

    public static Task<Void> updateSudoku(SudokuEntity sudokuEntity) {
       return SudokuRepo.updateSudoku(sudokuEntity);
    }


    public static Task<SudokuEntity> getSudokuById(String id) {
        return SudokuRepo.getSudoku(id);
    }

    public Task<List<SudokuReduce>> getAlSudokus() {
        return SudokuRepo.getAllSudokus();
    }
}
