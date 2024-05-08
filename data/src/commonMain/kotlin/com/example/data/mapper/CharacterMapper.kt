package com.example.data.mapper

import com.example.data.database.Character
import com.example.data.model.CharacterData
import com.example.shared.mapper.Mapper
import me.tatarka.inject.annotations.Inject

@Inject
class CharacterMapper: Mapper<CharacterData, Character> {
    override fun invoke(data: CharacterData): Character =
        Character(
            id = data.id ?: -1,
            name = data.name,
            description = data.description,
            thumbnail = data.thumbnail?.let {
                "${it.path?.toHttpsFormat()}.${it.extension}"
            },
            urlCount = data.urls?.size ?: 0,
            comicCount = data.comics?.items?.size ?: 0,
            storyCount = data.stories?.items?.size ?: 0,
            eventCount = data.events?.items?.size ?: 0,
            seriesCount = data.series?.items?.size ?: 0,
            mark = false
        )

    private fun String.toHttpsFormat() =
        if (this.contains("https")) this else
            replace("http", "https")
}