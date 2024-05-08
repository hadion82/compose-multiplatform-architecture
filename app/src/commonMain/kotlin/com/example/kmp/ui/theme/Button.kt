package com.example.kmp.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SimpleButton(
    onClick: () -> Unit,
    text: String,
    top: Float = .0f,
    start: Float = .0f,
    end: Float = .0f,
    bottom: Float = .0f
) =
    Button(
        onClick = onClick,
        Modifier.padding(
            top = top.dp,
            start = start.dp,
            end = end.dp,
            bottom = bottom.dp
        )
    ) {
        Text(text)
    }