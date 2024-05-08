package com.example.data.datasource.stream

import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.request.ImageRequest
import com.example.shared.di.scope.SingleToneScope
import com.example.shared.platform.startup.androidContext
import me.tatarka.inject.annotations.Inject

@SingleToneScope
@Inject
actual class ImageStreamDataSourceImpl : ImageStreamDataSource,
    ImageLoader by SingletonImageLoader.get(androidContext)
{
    actual override suspend fun preload(url: String) {
        enqueue(ImageRequest.Builder(androidContext)
            .data(url).build())
    }
}