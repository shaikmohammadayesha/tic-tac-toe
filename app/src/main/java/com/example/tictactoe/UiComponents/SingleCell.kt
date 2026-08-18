package com.example.tictactoe.UiComponents

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

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
                contentDescription = "Player ${gameCells[buttonIndex]}",
            )
        }
    }
}