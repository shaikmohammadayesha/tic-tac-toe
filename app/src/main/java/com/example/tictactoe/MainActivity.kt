package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tictactoe.Data.GameMode
import com.example.tictactoe.UiComponents.Board
import com.example.tictactoe.UiComponents.Reset
import com.example.tictactoe.UiComponents.statusBar
import com.example.tictactoe.ui.theme.TicTacToeTheme


/*  App Layout
*
* status Bar
*
* singleCell, Board,
*
* game mode selection
*
* reset
*
* */

/*
data -> gameMode

values -> strings.xml (for winner, select game mode, draw, button headings/txt)

drawable -> playerX and playerO images

Ui component --> cell, board, buttons(reset, human, computer), status bar

logic --> check for gamemode selected --> check for win --> computer game logic -> enable and disable cells & buttons logic --> reset

 */


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToeTheme {
                TicTacToe(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun TicTacToe(
    modifier: Modifier = Modifier
){
      AppLayout()
}

@Composable
private fun AppLayout(){
    var gameMode: GameMode? by remember { mutableStateOf(null) }
    val gameLogic by remember { mutableStateOf(ComputerGameLogic()) }
    val playerX = R.drawable.icons8_x_64
    val playerO = R.drawable.icons8_o_64
    val gameCells = remember { mutableStateListOf("", "", "", "", "", "", "", "", "") }
    var currentPlayer by remember { mutableStateOf("X") }
    var winner by remember { mutableStateOf("") }
    var cellsPlayed by remember { mutableIntStateOf(0) }
    var boardCellsEnabled by remember { mutableStateOf(false) }
    var cmpMoveCell by remember { mutableIntStateOf(0) }

    val rows = listOf(
        listOf(0,1,2),
        listOf(3,4,5),
        listOf(6,7,8)
    )
    val cols = listOf(
        listOf(0,3,6),
        listOf(1,4,7),
        listOf(2,5,8)
    )
    val diaogs = listOf(
        listOf(0,4,8),
        listOf(2,4,6)
    )

    val allCellCombination = rows + cols + diaogs

    fun playTheCell(index: Int, player: String) {
        if (gameCells[index].isEmpty()) {
            gameCells[index] = player
            cellsPlayed++
            if (checkForWin(player, gameCells, allCellCombination)){
                winner = player
                boardCellsEnabled = false

            }
        }
    }

    val onResetClick: () -> Unit = {
        for (i in gameCells.indices){
            gameCells[i] = ""
        }
        gameMode = null
        cellsPlayed = 0
        winner = ""
        boardCellsEnabled = false
    }

    val onCellClick: (Int) -> Unit =  { index ->
         playTheCell(index, currentPlayer)
         if (gameMode == GameMode.COMPUTER && winner == "") {
             cmpMoveCell = gameLogic.nxtMove(gameCells, if (currentPlayer == "X") "O" else "X", currentPlayer, allCellCombination)
             playTheCell(cmpMoveCell, if (currentPlayer == "X") "O" else "X")
         } else {
             currentPlayer = if (currentPlayer == "X") "O" else "X"
         }

    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
        ){
            statusBar(gameMode, winner, cellsPlayed, currentPlayer, playerX, playerO)
//            Text(
//                text = stringResource(statusBar(gameMode, winner, cellsPlayed, currentPlayer, playerX, playerO)),
//                fontSize = 20.sp,
//                color = Color.Green,
//                textAlign = TextAlign.Center,
//                modifier = Modifier
//                    .padding(15.dp)
//                    .fillMaxWidth()
//                    .align(Alignment.BottomCenter)
//
//            )
        }
        Box(
            modifier = Modifier
                .weight(3f)
                .fillMaxSize()
        ){
            Board(Modifier, onCellClick, gameCells, boardCellsEnabled, playerX, playerO)
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ){
            Column() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()

                ){

                    Button(
                        onClick = {
                            gameMode = GameMode.HUMAN
                            boardCellsEnabled = true

                        },
                        modifier = Modifier.weight(1f).padding(start = 5.dp,  end = 5.dp, top = 20.dp),
                        shape = RoundedCornerShape(5.dp),
                        enabled = (gameMode == null)
                    ){
                        Text(text="Human")
                    }
                    Button(
                        onClick = {
                            gameMode = GameMode.COMPUTER
                            boardCellsEnabled = true
                        },
                        modifier = Modifier.weight(1f).padding(start = 5.dp,  end = 5.dp, top = 20.dp),
                        shape = RoundedCornerShape(5.dp),
                        enabled = (gameMode == null)
                    ){
                        Text(text = "Computer")
                    }
                }

                Reset(
                    modifier = Modifier
                ){
                    onResetClick()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TicTacToeTheme {
        TicTacToe(Modifier.fillMaxSize())
    }
}
