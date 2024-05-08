package com.example.shared.platform.context

import android.content.Context
import me.tatarka.inject.annotations.Inject

actual interface PlatformContext {
    fun getContext(): Context
}

@Inject
class AndroidContext(
    private val context: Context
): PlatformContext {
    override fun getContext(): Context = context
}