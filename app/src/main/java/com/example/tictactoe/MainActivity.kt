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

@Composable
fun TicTacToe(
    modifier: Modifier = Modifier
){
    val gameMode: String? = null
    val playerX = R.drawable.icons8_x_64
    val playerO = R.drawable.icons8_o_64
    val gameCells = remember { mutableStateListOf("", "", "", "", "", "", "", "", "") }
    var currentPlayer by remember { mutableStateOf("X") }
    var winner by remember { mutableStateOf("") }
    var cellsPlayed by remember { mutableIntStateOf(0) }
    var cellsEnabled by remember { mutableStateOf(true) }
    val onResetClick: () -> Unit = {
        for (i in gameCells.indices){
            gameCells[i] = ""
        }
        
        cellsPlayed = 0
        winner = ""
        cellsEnabled = true

    }
    val onCellClick: (Int) -> Unit =  { index ->
        println("Button $index clicked")
        if (gameCells[index].isEmpty()) {
            gameCells[index] = currentPlayer
            cellsPlayed++
            if (checkForWin(currentPlayer, gameCells)){
                winner = currentPlayer
                cellsEnabled = false
            }
            currentPlayer = if (currentPlayer == "X") "O" else "X"

        }

    }

    if (cellsPlayed == 9 || winner != "" ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Text(
                text = if ( winner != "") "🎉 $winner wins" else "Draw",
                fontSize = 20.sp,
                color = Color.Green,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()


            )

//            Reset( "Replay", modifier = Modifier) {
//                onResetClick()
//            }
        }
    }

    Box(
        modifier = Modifier

            .padding(16.dp)
    ){
        Board(modifier, onCellClick, gameCells, cellsEnabled, playerX, playerO)
       if (gameMode.isNullOrEmpty()){
           Row(
               modifier = modifier
                   .fillMaxWidth()

           ){

               Button(
                   onClick = {/*toDo*/},
                   modifier = Modifier.weight(1f),
                   shape = RoundedCornerShape(5.dp)
               ){
                   Text(text="Human")
               }
               Button(
                   onClick = {/*toDo*/},
                   modifier = Modifier.weight(1f),
                   shape = RoundedCornerShape(5.dp)
               ){
                   Text(text = "Computer")
               }
           }
       }
        Reset( "Reset",
            modifier = Modifier
            .align(Alignment.BottomCenter)
        ){
            onResetClick()
        }

    }

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
            .size(95.dp)
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
            .padding(bottom = 40.dp)
            .fillMaxWidth()
    ){
        Text(
            text = restart,
            fontSize = 18.sp,
            modifier = Modifier
                .padding(12.dp)
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
        TicTacToe(Modifier.fillMaxSize())
    }
}