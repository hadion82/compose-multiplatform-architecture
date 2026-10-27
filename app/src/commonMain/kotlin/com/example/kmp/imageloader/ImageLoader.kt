package com.example.kmp.imageloader

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.memory.MemoryCache
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger

fun createImageLoader(
    context: PlatformContext,
    debug: Boolean = false
) = ImageLoader.Builder(context)
    .components {
        add(KtorNetworkFetcherFactory())
    }
    .memoryCachePolicy(CachePolicy.ENABLED)
    .memoryCache {
        MemoryCache.Builder()
            .maxSizePercent(context, percent = 0.3)
            .build()
    }
    .diskCachePolicy(CachePolicy.ENABLED)
    .crossfade(true)
    .apply { if(debug) logger(DebugLogger())}
    .build()
