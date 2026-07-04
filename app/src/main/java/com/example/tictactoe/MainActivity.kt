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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    val buttonTexts = remember { mutableStateListOf("", "", "", "", "", "", "", "", "") }
    var currentPlayer by remember { mutableStateOf("X") }
    var winner by remember { mutableStateOf("") }
    var btnCount by remember { mutableStateOf(0) }
    val onClickAction: (Int) -> Unit =  { index ->
        println("Button $index clicked")
        while (buttonTexts[index].isEmpty()) {
            buttonTexts[index] = currentPlayer
        }
        btnCount++
        if (checkForWin(currentPlayer, buttonTexts)){
           winner = currentPlayer

        }
        currentPlayer = if (currentPlayer == "X") "O" else "X"
    }
    if (btnCount == 9 || winner != ""){
        Box(
            modifier = Modifier
        ){
            Text(
                text = if ( winner != "") "$winner wins" else "Draw",
                fontSize = 17.sp,

                color = Color.White,
                modifier = Modifier
                    .padding(top = 15.dp)

                    .fillMaxWidth()
                    .background(color = Color.Gray)

            )
        }
    }
    Box(
        modifier = Modifier

            .padding(16.dp)
    ){
        Board(modifier, onClickAction, buttonTexts)
        Button(
            onClick =
                {
                    for (i in 0 until buttonTexts.size){
                        buttonTexts[i] = ""
                    }
                    btnCount = 0
                    winner = ""

                },
            modifier = Modifier
                .padding(bottom = 40.dp)
                .align(Alignment.BottomCenter)
        ){
            Text(
                text = "Restart",
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(12.dp)
            )
        }
    }

}


@Composable
fun Board(modifier: Modifier, onClickAction: (Int) -> Unit, buttonTexts: List<String>) {
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
                    SingleCell(onClickAction, (row * 3 ) + col, buttonTexts)
                }
            }

        }
    }
}

@Composable
fun SingleCell(onClickAction: (Int) -> Unit, buttonIndex: Int, buttonTexts: List<String>) {
    Button(
        onClick = { onClickAction(buttonIndex) },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
        shape = RoundedCornerShape(8.dp),
//        enabled = checkForWin,
        modifier = Modifier
            .padding(2.dp)
            .size(90.dp)
        ) {
        Text(text = buttonTexts[buttonIndex])
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
        TicTacToe()
    }
}