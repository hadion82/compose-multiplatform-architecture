package com.example.data.repository

import com.example.domain.repository.ImageStreamRepository
import coil3.SingletonImageLoader
import com.example.shared.di.scope.SingleToneScope
import com.example.shared.platform.media.MediaUtils
import com.example.shared.platform.startup.androidContext
import me.tatarka.inject.annotations.Inject

@SingleToneScope
@Inject
actual class ImageStreamRepositoryImpl: ImageStreamRepository {
    actual override suspend fun saveImage(url: String, dir: String, fileName: String) {
        val imageLoader = SingletonImageLoader.get(androidContext)
        imageLoader.diskCache?.openSnapshot(url).use {snapshot ->
            snapshot?.data?.let {path ->
                MediaUtils.copyToFile(path.toString(), dir, fileName)
            }
        }
    }
}
