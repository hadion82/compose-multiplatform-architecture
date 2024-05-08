package com.example.kmp.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow

@Composable
actual fun <T> StateFlow<T>.collectAsStateWithMultiplatform(): State<T> =
    this.collectAsStateWithLifecycle()