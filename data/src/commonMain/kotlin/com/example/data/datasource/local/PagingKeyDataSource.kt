package com.example.data.datasource.local

import com.example.data.database.PagingKey

interface PagingKeyDataSource {

    suspend fun insertKey(
        value: PagingKey
    )

    suspend fun selectKey(value: String): PagingKey?
}