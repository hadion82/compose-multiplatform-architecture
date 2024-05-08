package com.example.data.datasource.local

import com.example.data.dao.PagingKeyDao
import com.example.data.dao.PagingKeyDaoImpl
import com.example.data.database.PagingKey
import me.tatarka.inject.annotations.Inject

//@SingleToneScope
@Inject
class PagingKeyDataSourceImpl (
    private val dao: PagingKeyDaoImpl
) : PagingKeyDataSource {

    override suspend fun insertKey(value: PagingKey) =
        dao.insertOrReplace(value)

    override suspend fun selectKey(value: String) =
        dao.selectKey(value)
}