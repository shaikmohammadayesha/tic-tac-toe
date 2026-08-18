package com.example.tictactoe.UiComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier



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