package com.example.kmp.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.StateFlow

@Composable
expect fun <T> StateFlow<T>.collectAsStateWithMultiplatform(): State<T>