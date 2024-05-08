package com.example.data.mapper

import com.example.data.entity.BookmarkEntity
import com.example.data.model.MarvelCharacter
import com.example.shared.mapper.Mapper
import me.tatarka.inject.annotations.Inject

@Inject
class BookMarkEntityMapper (): Mapper<MarvelCharacter, BookmarkEntity> {
    override fun invoke(data: MarvelCharacter): BookmarkEntity =
        BookmarkEntity(
            id = data.id,
            name = data.name,
            description = data.description,
            thumbnail = data.thumbnail,
            urlCount = data.urlCount,
            comicCount = data.comicCount,
            storyCount = data.storyCount,
            eventCount = data.eventCount,
            seriesCount = data.seriesCount,
            mark = false
        )
}