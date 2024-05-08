package com.example.data.dao

import com.example.data.database.PagingKey


interface PagingKeyDao {

    suspend fun insertOrReplace(value: PagingKey)

    suspend fun selectKey(value: String): PagingKey?
}