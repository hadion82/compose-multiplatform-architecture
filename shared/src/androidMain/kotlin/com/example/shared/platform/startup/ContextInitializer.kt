package com.example.shared.platform.startup

import android.content.Context
import androidx.startup.Initializer

lateinit var androidContext: Context
    private set

object AppContext

class ContextInitializer: Initializer<AppContext> {
    override fun create(context: Context): AppContext {
        androidContext = context.applicationContext
        return AppContext
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}