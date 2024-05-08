package com.example.kmp.imageloader

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.fetch.NetworkFetcher
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger

@OptIn(ExperimentalCoilApi::class)
fun createImageLoader(
    context: PlatformContext,
    debug: Boolean = false
) = ImageLoader.Builder(context)
    .components {
        add(NetworkFetcher.Factory())
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