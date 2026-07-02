package com.example.tictactoe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    val buttonTexts = remember { mutableStateListOf("", "", "", "", "", "","", "", "") }
    var currentPlayer: String = "X"
    val onClickAction: (Int) -> Unit =  { index ->
        println("Button $index clicked")
        while (buttonTexts[index].isEmpty()) {
            buttonTexts[index] = currentPlayer

        }
        currentPlayer = if (currentPlayer == "X") "O" else "X"
    }
    Board(modifier, onClickAction, buttonTexts)


    Button(
        onClick =
            { /*TODO*/ },

        ){
        Text(text = "Restart")
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
        modifier = Modifier
            .padding(2.dp)
            .size(90.dp)
        ) {
        Text(text = buttonTexts[buttonIndex])
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TicTacToeTheme {
        TicTacToe()
    }
}