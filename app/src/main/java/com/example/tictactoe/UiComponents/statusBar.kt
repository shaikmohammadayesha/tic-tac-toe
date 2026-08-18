package com.example.tictactoe.UiComponents



import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.tictactoe.R
import com.example.tictactoe.Data.GameMode

@Composable
fun statusBar(
    gameMode: GameMode?,
    winner: String,
    cellsPlayed: Int,
    currentPlayer: String,
    playerX: Int,
    playerO: Int,
    modifier: Modifier = Modifier


) {

    val currentStatus = when {
        gameMode == null -> stringResource(R.string.select_game_mode)
        winner.isNotEmpty() -> stringResource(R.string.winner_is, winner)
        cellsPlayed == 9 -> stringResource(R.string.draw)
        else -> stringResource(R.string.current_player, currentPlayer)
    }
    Text(
        text = currentStatus,
        fontSize = 20.sp,
        color = Color.Green,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()



    )


}

