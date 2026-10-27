package com.example.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.data.datasource.local.CharacterLocalDataSourceImpl
import com.example.data.mapper.CharacterItemMapper
import com.example.domain.model.CharacterItem
import com.example.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class CharacterRepositoryImpl (
    private val localDataSource: CharacterLocalDataSourceImpl,
    private val characterMediator: CharacterMediator,
    private val mapper: CharacterItemMapper
) : CharacterRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun loadPagingData(): Flow<PagingData<CharacterItem>> {
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
        ).flow.map { pagingData -> pagingData.map(mapper) }
    }

    companion object {
        const val PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 20
    }
}
