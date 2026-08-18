package com.example.tictactoe.UiComponents



import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoe.Data.GameMode
import com.example.tictactoe.R

@OptIn(ExperimentalMaterial3Api::class)
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
    CenterAlignedTopAppBar(
        title = {

            Text(
                text = currentStatus,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()


            )
        }
    )


}

