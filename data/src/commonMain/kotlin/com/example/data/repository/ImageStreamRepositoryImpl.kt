package com.example.data.repository

import com.example.shared.di.scope.SingleToneScope
import me.tatarka.inject.annotations.Inject

@SingleToneScope
@Inject
expect class ImageStreamRepositoryImpl constructor(): ImageStreamRepository {
    override suspend fun saveImage(url: String, dir: String, fileName: String)
}