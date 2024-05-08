package com.example.domain.mapper

import com.example.data.database.Character
import com.example.data.entity.BookmarkEntity
import com.example.data.model.CharacterItem
import com.example.shared.mapper.Mapper
import me.tatarka.inject.annotations.Inject

@Inject
class CharacterItemMapper : Mapper<Character, CharacterItem> {
    override fun invoke(data: Character): CharacterItem =
        CharacterItem(
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