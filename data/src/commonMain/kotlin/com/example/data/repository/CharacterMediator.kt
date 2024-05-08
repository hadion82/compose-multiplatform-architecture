package com.example.data.repository

import androidx.paging.ExperimentalPagingApi
import app.cash.paging.LoadType
import app.cash.paging.PagingState
import app.cash.paging.RemoteMediator
import com.example.data.database.Character
import com.example.data.database.PagingKey
import com.example.data.datasource.local.CharacterLocalDataSource
import com.example.data.datasource.local.CharacterLocalDataSourceImpl
import com.example.data.datasource.local.PagingKeyDataSource
import com.example.data.datasource.local.PagingKeyDataSourceImpl
import com.example.data.datasource.remote.CharacterRemoteDataSource
import com.example.data.datasource.remote.CharacterRemoteDataSourceImpl
import com.example.data.datasource.stream.ImageStreamDataSource
import com.example.data.datasource.stream.ImageStreamDataSourceImpl
import com.example.data.mapper.CharacterMapper
import com.example.data.model.CharacterData
import me.tatarka.inject.annotations.Inject

@Inject
@OptIn(ExperimentalPagingApi::class)
class CharacterMediator(
    remoteDataSource: CharacterRemoteDataSourceImpl,
    localDataSource: CharacterLocalDataSourceImpl,
    bookmarkKeyDataSource: PagingKeyDataSourceImpl,
    streamDataSource: ImageStreamDataSourceImpl,
    private val characterMapper: CharacterMapper
) : RemoteMediator<Int, Character>(),
    CharacterRemoteDataSource by remoteDataSource,
    CharacterLocalDataSource by localDataSource,
    PagingKeyDataSource by bookmarkKeyDataSource,
    ImageStreamDataSource by streamDataSource {

    companion object {
        private const val START_KEY = 0
        private const val PAGE_LIMIT = 20
        private const val KEY_TYPE = "bookmark"
    }

    private var totalCount = Int.MAX_VALUE
    private var key = START_KEY

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {
        val savedKey = selectKey(KEY_TYPE)?.key ?: START_KEY
        key = when (loadType) {
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.APPEND -> {
                if (savedKey < totalCount) savedKey else
                    return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.REFRESH -> {
                START_KEY
            }
        }
        val response = getCharacters(key, PAGE_LIMIT)

        return try {
            val results = response.data?.results
            requireNotNull(results)
            totalCount = response.data.total ?: Int.MAX_VALUE
            val nextKey = key.plus(CharacterPagingSource.PAGE_LIMIT)
            val (new, downloaded) = partitionByDownloaded(results)
            preloadImage(new)
            updatePagingKey(nextKey)
            updateCharacter(new, downloaded)
            MediatorResult.Success(
                endOfPaginationReached = (nextKey >= totalCount)
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun partitionByDownloaded(data: List<CharacterData>): Pair<List<Character>, List<Character>> {
        val characters = data.map(characterMapper)
        val responseIds = characters.map { it.id }
        val savedIds = selectIds(responseIds)
        return characters.partition { savedIds.contains(it.id).not() }
    }

    private suspend fun preloadImage(data: List<Character>) {
        for(character in data) character.thumbnail?.let { url -> preload(url) }
    }

    private suspend fun updatePagingKey(key: Int) =
        insertKey(PagingKey(KEY_TYPE, key))

    private suspend fun updateCharacter(new: List<Character>, downloaded: List<Character>) {
        update(downloaded)
        insertOrReplace(new)
    }
}