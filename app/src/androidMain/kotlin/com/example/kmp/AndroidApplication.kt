package com.example.kmp

import android.app.Application
import android.util.Log
import androidx.core.os.BuildCompat
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import com.example.kmp.di.ApplicationComponent
import com.example.kmp.di.ApplicationComponentProvider
import com.example.kmp.di.create
import com.example.kmp.imageloader.createImageLoader
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class AndroidApplication: Application(), ApplicationComponentProvider, SingletonImageLoader.Factory {

override val component: ApplicationComponent by lazy {
    ApplicationComponent::class.create(this)
}
    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog())
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader =
        createImageLoader(context)
}