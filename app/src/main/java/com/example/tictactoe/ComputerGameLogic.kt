package com.example.tictactoe



class ComputerGameLogic {

    fun nxtMove(gameCells: List<String>, cmp: String, person: String, allCellCombination: List<List<Int>>): Int {
        //Check for winning move
        val winMove: Int? = possibleWinBlockCheck(gameCells,cmp, allCellCombination)

        if (winMove != null){
            return winMove
        }

        //if no winning move then check for blocking the opponents winning move
        val blockMove: Int? = possibleWinBlockCheck(gameCells,person, allCellCombination)
        if (blockMove != null){
            return blockMove
        }

        //check for the center cell
        if (gameCells[4].isEmpty()){
            return 4
        }

        // if center cell is occupied then check for any random empty cell
        return findEmptyCell(gameCells)
    }

    private fun possibleWinBlockCheck (gameCells: List<String>, targetPlayer: String, allCells: List<List<Int>>) : Int? {
        for (cellCombination in allCells){
            val targetPlayedCount = cellCombination.count { index -> gameCells[index] == targetPlayer}
            val emptyCellsCount = cellCombination.count { index -> gameCells[index].isEmpty() }

            if (targetPlayedCount == 2 && emptyCellsCount == 1){
                return cellCombination.first { gameCells[it].isEmpty() }
            }
        }
        return null
    }

    private fun findEmptyCell(
        gameCells: List<String>
    ): Int {
        val emptyIndices = gameCells.indices.filter { index -> gameCells[index].isEmpty() }

        return if (emptyIndices.isNotEmpty()) emptyIndices.random() else 0
    }

}