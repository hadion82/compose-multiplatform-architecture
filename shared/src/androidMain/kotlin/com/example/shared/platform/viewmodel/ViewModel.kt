package com.example.shared.platform.viewmodel

import kotlinx.coroutines.CoroutineScope
import androidx.lifecycle.ViewModel as AndroidPlatformViewModel
import androidx.lifecycle.viewModelScope as AndroidPlatformViewModelScope

actual open class ViewModel actual constructor() : AndroidPlatformViewModel() {

    actual val viewModelScope: CoroutineScope = AndroidPlatformViewModelScope

    public actual override fun onCleared() {
        super.onCleared()
    }
}