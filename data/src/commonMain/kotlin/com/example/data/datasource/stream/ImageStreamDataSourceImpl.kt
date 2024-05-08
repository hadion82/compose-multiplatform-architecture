package com.example.data.datasource.stream

import com.example.shared.di.scope.SingleToneScope
import me.tatarka.inject.annotations.Inject

@SingleToneScope
@Inject
expect class ImageStreamDataSourceImpl : ImageStreamDataSource {
    override suspend fun preload(url: String)
}