package com.example.data.repository

import app.cash.paging.PagingSource
import app.cash.paging.PagingState
import com.example.data.database.Character
import com.example.data.datasource.local.CharacterLocalDataSource
import com.example.data.datasource.local.CharacterLocalDataSourceImpl
import com.example.data.datasource.remote.CharacterRemoteDataSource
import com.example.data.datasource.remote.CharacterRemoteDataSourceImpl
import com.example.data.mapper.CharacterMapper

class CharacterPagingSource internal constructor(
    remoteDataSource: CharacterRemoteDataSourceImpl,
    localDataSource: CharacterLocalDataSourceImpl,
    private val mapper: CharacterMapper
) : PagingSource<Int, Character>(),
    CharacterRemoteDataSource by remoteDataSource,
    CharacterLocalDataSource by localDataSource {


    override fun getRefreshKey(state: PagingState<Int, Character>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(PAGE_LIMIT)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(PAGE_LIMIT)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        try {
            val key: Int = params.key ?: 0
            if (key < 0) return emptyPrevResult()
            val response = getCharacters(key, limit = PAGE_LIMIT)
            val results = response.data?.results
            requireNotNull(results)
            val total = response.data.total ?: Int.MAX_VALUE
            val nextKey = key.plus(PAGE_LIMIT)
            val characters = results.map(mapper)
            update(characters)
            val data = selectBookmarksById(characters.map { it.id })
            return LoadResult.Page(
                data = data,
                prevKey = key.minus(PAGE_LIMIT),
                nextKey = if (nextKey < total) nextKey else null
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    private fun emptyPrevResult() =
        LoadResult.Page(
            data = emptyList<Character>(),
            prevKey = null,
            nextKey = 0
        )

    companion object {
        const val PAGE_LIMIT = 20
    }
}