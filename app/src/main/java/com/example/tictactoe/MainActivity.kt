package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
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

enum class GameMode{
    HUMAN, COMPUTER, NULL
}


@Composable
fun TicTacToe(
    modifier: Modifier = Modifier
){
      AppLayout()
//    var gameMode by remember { mutableStateOf(GameMode.NULL) }
//    val playerX = R.drawable.icons8_x_64
//    val playerO = R.drawable.icons8_o_64
//    val gameCells = remember { mutableStateListOf("", "", "", "", "", "", "", "", "") }
//    var currentPlayer by remember { mutableStateOf("X") }
//    var winner by remember { mutableStateOf("") }
//    var cellsPlayed by remember { mutableIntStateOf(0) }
//    var boardCellsEnabeled by remember { mutableStateOf(false) }
//    var cmp by remember { mutableStateOf("X")}
//    var person by remember {mutableStateOf("O")}
//
//    val onResetClick: () -> Unit = {
//        for (i in gameCells.indices){
//            gameCells[i] = ""
//        }
//        gameMode = GameMode.NULL
//        boardCellsEnabeled = false
//        cellsPlayed = 0
//        winner = ""
//
//    }
//    val onCellClick: (Int) -> Unit =  { index ->
//        println("Button $index clicked")
//        if (gameCells[index].isEmpty()) {
//            gameCells[index] = currentPlayer
//            cellsPlayed++
//            if (checkForWin(currentPlayer, gameCells)){
//                winner = currentPlayer
//                boardCellsEnabeled = false
//            }
//            if (gameMode == GameMode.COMPUTER) {
//                nxtMove(gameCells, cmp, person)
//            }
//            currentPlayer = if (currentPlayer == "X") "O" else "X"
//
//        }
//
//    }
//
//
//    if (cellsPlayed == 9 || winner != "" || true ){
//        Column(
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(10.dp)
//        ) {
//            Text(
//                text = if ( winner != "") "🎉 $winner wins" else "Draw",
//                fontSize = 20.sp,
//                color = Color.Green,
//                textAlign = TextAlign.Center,
//                modifier = Modifier
//                    .padding(top = 15.dp)
//                    .fillMaxWidth()
//
//
//            )
//
//            Reset( "Replay", modifier = Modifier) {
//                onResetClick()
//            }
//        }
//    }

//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ){
//        Board(modifier, onCellClick, gameCells, boardCellsEnabeled, playerX, playerO)
//
//        Column(
//            verticalArrangement = Arrangement.Bottom,
//            modifier = modifier
//                .align(Alignment.BottomCenter)
//        ) {
//            Row(
//                modifier = modifier
//                    .fillMaxWidth()
//
//            ){
//
//                Button(
//                    onClick = {
//                        gameMode = GameMode.HUMAN
//                        boardCellsEnabeled = true
//
//                    },
//                    modifier = Modifier.weight(1f),
//                    shape = RoundedCornerShape(5.dp),
//                    enabled = if (gameMode == GameMode.NULL) true else false
//                ){
//                    Text(text="Human")
//                }
//                Button(
//                    onClick = {
//                        gameMode = GameMode.COMPUTER
//                        boardCellsEnabeled = true
//                    },
//                    modifier = Modifier.weight(1f),
//                    shape = RoundedCornerShape(5.dp),
//                    enabled = if (gameMode == GameMode.NULL) true else false
//                ){
//                    Text(text = "Computer")
//                }
//            }
//
//            Reset( "Reset",
//                modifier = Modifier
//            ){
//                onResetClick()
//            }
//        }

//    }

}


fun statusBar(gameMode: GameMode, winner: String, cellsPlayed: Int, currentPlayer: String, playerX: Int, playerO: Int): String {
    if (gameMode == GameMode.NULL) {
        return "Select Game Mode"
    } else if (winner != "") {
        return if (winner == "X") "X wins" else "O wins"
    } else if (cellsPlayed == 9){
        return "Draw"
    }else {
        return "${if (currentPlayer == "X") "X" else "O"} Player"
    }
}
fun nxtMove(gameCells: List<String>, cmp: String, person: String): Int {
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
    var cellToPlay: Int = possibleHorizontalWin(gameCells,cmp, rows)?:
    possibleVerticalWin(gameCells,cmp, cols)?:
    possibleDiagonalWin(gameCells,cmp,diaogs)?:
    possibleHorizontalWin(gameCells,person, rows)?:
    possibleVerticalWin(gameCells,person, cols)?:
    possibleDiagonalWin(gameCells,person,diaogs)?:
    emptyCell(gameCells,rows)


    return cellToPlay
}

fun emptyCell(
    gameCells: List<String>,
    rows: List<List<Int>>
): Int {
    for (row in rows) {
        return row.first { index ->  gameCells[index].isEmpty()}
    }
    return 0
}


fun possibleHorizontalWin(buttonTexts: List<String>, currentPlayer: String, rows: List<List<Int>>): Int? {
    for (row in rows) {
        val rowValue = row.count {index -> buttonTexts[index] == currentPlayer}
        val emptyCells = row.count { index -> buttonTexts[index].isEmpty() }
        if (rowValue == 2 && emptyCells == 1){
            return row.first { index -> buttonTexts[index].isEmpty() }
        }
    }
    return null
}

fun possibleVerticalWin(buttonTexts: List<String>, currentPlayer: String, cols: List<List<Int>>): Int? {
    for (col in cols) {
        val colValue = col.count {index -> buttonTexts[index] == currentPlayer}
        val emptyCells = col.count { index -> buttonTexts[index].isEmpty() }
        if (colValue == 2 && emptyCells == 1){
            return col.first { index -> buttonTexts[index].isEmpty() }
        }
    }
    return null
}

fun possibleDiagonalWin(buttonTexts: List<String>, currentPlayer: String, diaogs: List<List<Int>>): Int? {
    for (diaog in diaogs) {
        val diaogValue = diaog.count {index -> buttonTexts[index] == currentPlayer}
        val emptyCells = diaog.count { index -> buttonTexts[index].isEmpty() }
        if (diaogValue == 2 && emptyCells == 1){
            return diaog.first { index -> buttonTexts[index].isEmpty() }
        }
    }
    return null
}
@Composable
fun Board(modifier: Modifier, onClickAction: (Int) -> Unit, gameCells: List<String>, btnsEnabeled: Boolean, playerX: Int, playerO: Int) {
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
fun SingleCell(onClickAction: (Int) -> Unit, buttonIndex: Int, gameCells: List<String>, btnsEnabeled: Boolean, playerX: Int, playerO: Int) {
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
//                tint = Color.Unspecified,
                contentDescription = "Player ${gameCells[buttonIndex]}",
            )
        }
    }
}

@Composable
fun Reset(restart: String, modifier: Modifier,onResetClick: () -> Unit){
    Button(
        onClick = { onResetClick()},
        shape = RoundedCornerShape(5.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(25.dp)
    ){
//        Text(
//            text = restart,
//            fontSize = 18.sp,
//            modifier = Modifier
//                .padding(12.dp)
//        )
        Icon(
            painter = painterResource(id = R.drawable.replay),
            contentDescription = "Replay",
            modifier = Modifier
                .size(25.dp)

        )
    }
}

private fun checkForWin(currentPlayer: String, buttonTexts: List<String>): Boolean{
    //012 036 048
    //345 147 246
    //678 258
    if ( horizontalCheck(buttonTexts, currentPlayer) || verticalCheck(buttonTexts, currentPlayer) || diagonalCheck(buttonTexts, currentPlayer)){
        return true
    }else {
        return false
    }


}

private fun verticalCheck(buttonTexts: List<String>, currentPlayer: String): Boolean {
    if ((buttonTexts[0] == currentPlayer && buttonTexts[3] == currentPlayer && buttonTexts[6] == currentPlayer) ||
        (buttonTexts[1] == currentPlayer && buttonTexts[4] == currentPlayer && buttonTexts[7] == currentPlayer) ||
        (buttonTexts[2] == currentPlayer && buttonTexts[5] == currentPlayer && buttonTexts[8] == currentPlayer)){
        return true
    }
    else {
        return false
    }
}

private fun horizontalCheck(buttonTexts: List<String>, currentPlayer: String): Boolean {
    if ((buttonTexts[0] == currentPlayer && buttonTexts[1] == currentPlayer && buttonTexts[2] == currentPlayer) ||
     (buttonTexts[3] == currentPlayer && buttonTexts[4] == currentPlayer && buttonTexts[5] == currentPlayer) ||
            (buttonTexts[6] == currentPlayer && buttonTexts[7] == currentPlayer && buttonTexts[8] == currentPlayer)){
        return true
    }
    else {
        return false
    }
}

private fun diagonalCheck(buttonTexts: List<String>, currentPlayer: String): Boolean {
    if ((buttonTexts[0] == currentPlayer && buttonTexts[4] == currentPlayer && buttonTexts[8] == currentPlayer) ||
        (buttonTexts[2] == currentPlayer && buttonTexts[4] == currentPlayer && buttonTexts[6] == currentPlayer)){
        return true
    }else{
        return false
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TicTacToeTheme {
//        TicTacToe(Modifier.fillMaxSize())
        AppLayout()
    }
}

@Composable
fun AppLayout(){
    var gameMode by remember { mutableStateOf(GameMode.NULL) }
    val playerX = R.drawable.icons8_x_64
    val playerO = R.drawable.icons8_o_64
    val gameCells = remember { mutableStateListOf("", "", "", "", "", "", "", "", "") }
    var currentPlayer by remember { mutableStateOf("X") }
    var winner by remember { mutableStateOf("") }
    var cellsPlayed by remember { mutableIntStateOf(0) }
    var boardCellsEnabeled by remember { mutableStateOf(false) }
    var cmp by remember { mutableStateOf("X")}
    var person by remember {mutableStateOf("O")}

    val onResetClick: () -> Unit = {
        for (i in gameCells.indices){
            gameCells[i] = ""
        }

        cellsPlayed = 0
        winner = ""
        boardCellsEnabeled = true

    }
    val onCellClick: (Int) -> Unit =  { index ->
        println("Button $index clicked")
        if (gameCells[index].isEmpty()) {
            gameCells[index] = currentPlayer
            cellsPlayed++
            if (checkForWin(currentPlayer, gameCells)){
                winner = currentPlayer
                boardCellsEnabeled = false
            }
            if (gameMode == GameMode.COMPUTER) {
                nxtMove(gameCells, cmp, person)
            }
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
//                .fillMaxSize()
                .background(color = Color.Red)

        ){
            Column(
                modifier = Modifier
            ) {
                Text(
                    text = statusBar(gameMode, winner, cellsPlayed, currentPlayer, playerX, playerO),
                    fontSize = 20.sp,
                    color = Color.Green,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()

                )
            }
        }
        Box(
            modifier = Modifier
                .weight(3f)
                .fillMaxSize()
                .background(color = Color.Green)
        ){
            Board(Modifier, onCellClick, gameCells, boardCellsEnabeled, playerX, playerO)
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(color = Color.Blue)
        ){
            Column() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()

                ){

                    Button(
                        onClick = {
                            gameMode = GameMode.HUMAN
                            boardCellsEnabeled = true

                        },
                        modifier = Modifier.weight(1f).padding(start = 5.dp,  end = 5.dp, top = 20.dp),
                        shape = RoundedCornerShape(5.dp),
                        enabled = if (gameMode == GameMode.NULL) true else false
                    ){
                        Text(text="Human")
                    }
                    Button(
                        onClick = {
                            gameMode = GameMode.COMPUTER
                            boardCellsEnabeled = true
                        },
                        modifier = Modifier.weight(1f).padding(start = 5.dp,  end = 5.dp, top = 20.dp),
                        shape = RoundedCornerShape(5.dp),
                        enabled = if (gameMode == GameMode.NULL) true else false
                    ){
                        Text(text = "Computer")
                    }
                }

                Reset( "Reset",
                    modifier = Modifier
                ){
                    onResetClick()
                }
            }
        }
    }
}