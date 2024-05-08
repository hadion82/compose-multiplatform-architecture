package com.example.data.dao

import com.example.data.database.LocalDatabase
import com.example.data.database.PagingKey
import com.example.shared.di.scope.SingleToneScope
import me.tatarka.inject.annotations.Inject

@Inject
class PagingKeyDaoImpl(private val database: LocalDatabase) :
    PagingKeyDao, LocalDatabase by database {
    override suspend fun insertOrReplace(value: PagingKey) =
        pagingKeyQueries.insertOrReplace(value.type, value.key)

    override suspend fun selectKey(value: String): PagingKey? =
        pagingKeyQueries.selectKey(value).executeAsOneOrNull()
}