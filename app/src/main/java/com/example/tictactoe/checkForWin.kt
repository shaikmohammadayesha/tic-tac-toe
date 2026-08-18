package com.example.tictactoe

fun checkForWin(currentPlayer: String, buttonTexts: List<String>, allCellCombination: List<List<Int>>): Boolean{
    //012 036 048
    //345 147 246
    //678 258
    for(line in allCellCombination){
        val targetPlayedCount = line.count {index -> buttonTexts[index] == currentPlayer}
        if (targetPlayedCount == 3){
            return true
        }
    }
    return false
}