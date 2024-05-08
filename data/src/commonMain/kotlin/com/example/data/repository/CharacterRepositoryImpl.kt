package com.example.data.repository

import androidx.paging.ExperimentalPagingApi
import app.cash.paging.Pager
import app.cash.paging.PagingConfig
import app.cash.paging.PagingData
import com.example.data.database.Character
import com.example.data.datasource.local.CharacterLocalDataSourceImpl
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@Inject
class CharacterRepositoryImpl (
    private val localDataSource: CharacterLocalDataSourceImpl,
    private val characterMediator: CharacterMediator
) : CharacterRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun loadPagingData(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = PREFETCH_DISTANCE
            ),
            remoteMediator = characterMediator,
            pagingSourceFactory = {
                localDataSource.selectCharacters()
            }
        ).flow
    }

    companion object {
        const val PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 20
    }
}