package com.example.tictactoe.UiComponents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tictactoe.R

@Composable
fun Reset(modifier: Modifier,onResetClick: () -> Unit){
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