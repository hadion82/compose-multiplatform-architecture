package com.example.domain.mapper

import com.example.data.entity.BookmarkEntity
import com.example.data.model.CharacterItem
import com.example.shared.mapper.Mapper
import me.tatarka.inject.annotations.Inject

@Inject
class BookmarkEntityMapper() : Mapper<CharacterItem, BookmarkEntity> {
    override fun invoke(data: CharacterItem): BookmarkEntity =
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
            mark = data.mark
        )
}