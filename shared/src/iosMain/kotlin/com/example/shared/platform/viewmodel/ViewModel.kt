package com.example.shared.platform.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

actual open class ViewModel actual constructor() {
    actual val viewModelScope: CoroutineScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main.immediate
    )

    actual open fun onCleared() {
        viewModelScope.cancel()
    }
}