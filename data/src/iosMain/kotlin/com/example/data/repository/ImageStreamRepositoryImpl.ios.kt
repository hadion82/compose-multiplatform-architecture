package com.example.data.repository

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.request.CachePolicy
import com.example.shared.di.scope.SingleToneScope
import com.example.shared.platform.media.MediaUtils
import me.tatarka.inject.annotations.Inject
import okio.use

@SingleToneScope
@Inject
actual class ImageStreamRepositoryImpl : ImageStreamRepository {
    actual override suspend fun saveImage(url: String, dir: String, fileName: String) {
        val imageLoader = ImageLoader.Builder(PlatformContext.INSTANCE)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build()
        imageLoader.diskCache?.openSnapshot(url).use { snapshot ->
            snapshot?.data?.let { path ->
                MediaUtils.copyToFile(path.toString(), dir, fileName)
            }
        }
    }
}