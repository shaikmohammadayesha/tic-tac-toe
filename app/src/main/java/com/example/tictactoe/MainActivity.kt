package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

//enum class GameMode{
//    HUMAN, COMPUTER, NULL
//}


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
            Text(
                text = statusBar(gameMode, winner, cellsPlayed, currentPlayer, playerX, playerO),
                fontSize = 20.sp,
                color = Color.Green,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)

            )
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

private fun statusBar(gameMode: GameMode?, winner: String, cellsPlayed: Int, currentPlayer: String, playerX: Int, playerO: Int): String {
    return if (gameMode == null) {
        "Select Game Mode"
    } else if (winner != "") {
        if (winner == "X") "X wins" else "O wins"
    } else if (cellsPlayed == 9){
        "Draw"
    }else{
        "${if (currentPlayer == "X") "X" else "O"} Player"
    }
}

@Composable
private fun Board(modifier: Modifier, onClickAction: (Int) -> Unit, gameCells: List<String>, btnsEnabeled: Boolean, playerX: Int, playerO: Int) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ){
        repeat(3) {
                row ->
            Row(
                modifier = Modifier,
            ) {
                repeat(3){
                        col ->
                    SingleCell(onClickAction, (row * 3 ) + col, gameCells, btnsEnabeled, playerX, playerO)
                }
            }

        }
    }
}

@Composable
private fun SingleCell(onClickAction: (Int) -> Unit, buttonIndex: Int, gameCells: List<String>, btnsEnabeled: Boolean, playerX: Int, playerO: Int) {
    Button(
        onClick = { onClickAction(buttonIndex) },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Gray,
            disabledContainerColor = Color.LightGray
        ),
        shape = RoundedCornerShape(5.dp),
        enabled = btnsEnabeled && gameCells[buttonIndex].isEmpty(),
        modifier = Modifier
            .padding(2.dp)
            .size(100.dp)
    ) {
        if (gameCells[buttonIndex].isNotEmpty()){
            val playerImg = if (gameCells[buttonIndex] == "X") playerX else playerO
            val playerColor = if (gameCells[buttonIndex] == "X") Color.Red else Color.Blue
            Icon(
                painter = painterResource(id = playerImg),
                tint = playerColor,
                contentDescription = "Player ${gameCells[buttonIndex]}",
            )
        }
    }
}

@Composable
private fun Reset(modifier: Modifier,onResetClick: () -> Unit){
    Button(
        onClick = { onResetClick()},
        shape = RoundedCornerShape(5.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(25.dp)
    ){
        Icon(
            painter = painterResource(id = R.drawable.replay),
            contentDescription = "Replay",
            modifier = Modifier
                .size(25.dp)

        )
    }
}




private fun checkForWin(currentPlayer: String, buttonTexts: List<String>, allCellCombination: List<List<Int>>): Boolean{
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TicTacToeTheme {
        TicTacToe(Modifier.fillMaxSize())
    }
}
