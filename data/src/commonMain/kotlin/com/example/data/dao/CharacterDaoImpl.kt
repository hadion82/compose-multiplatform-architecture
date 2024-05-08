package com.example.data.dao

import app.cash.paging.PagingSource
import app.cash.sqldelight.paging3.QueryPagingSource
import com.example.data.database.Character
import com.example.data.database.LocalDatabase
import com.example.data.entity.BookmarkEntity
import com.example.data.model.MarvelCharacter
import com.example.shared.di.scope.SingleToneScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import me.tatarka.inject.annotations.Inject

@Inject
class CharacterDaoImpl(
    val database: LocalDatabase
) : CharacterDao, LocalDatabase by database {
    override suspend fun insertOrReplace(values: List<Character>) =
        characterQueries.transaction {
            values.forEach { entity ->
                characterQueries.insertOrReplace(
                    id = entity.id,
                    name = entity.name,
                    description = entity.description,
                    thumbnail = entity.thumbnail,
                    urlCount = entity.urlCount,
                    comicCount = entity.comicCount,
                    storyCount = entity.storyCount,
                    eventCount = entity.eventCount,
                    seriesCount = entity.seriesCount
                )
            }
        }

    override suspend fun update(values: List<Character>) =
        characterQueries.transaction {
            values.forEach { entity ->
                characterQueries.update(
                    id = entity.id,
                    name = entity.name,
                    description = entity.description,
                    thumbnail = entity.thumbnail,
                    urlCount = entity.urlCount,
                    comicCount = entity.comicCount,
                    storyCount = entity.storyCount,
                    eventCount = entity.eventCount,
                    seriesCount = entity.seriesCount
                )
            }
        }

    override suspend fun updateMark(id: Int, mark: Boolean) =
        characterQueries.updateMark(mark, id)

    override suspend fun selectIds(ids: List<Int>): List<Int> =
        characterQueries.selectIds(ids).executeAsList()

    override suspend fun selectCharactersById(ids: List<Int>): List<Character> =
        characterQueries.selectCharactersById(ids).executeAsList()

    override fun selectCharacters(): PagingSource<Int, Character> =
        QueryPagingSource(
            countQuery = characterQueries.countCharacters(),
            transacter = characterQueries,
            context = Dispatchers.IO,
            queryProvider = characterQueries::selectCharacters
        )

    override fun selectBookmarks(): PagingSource<Int, Character> =
        QueryPagingSource(
            countQuery = characterQueries.countBookmarks(),
            transacter = characterQueries,
            context = Dispatchers.IO,
            queryProvider = characterQueries::selectBookmarks
        )
}