package com.example.data.mapper

import com.example.data.database.Character
import com.example.domain.model.CharacterItem
import kotlin.test.Test
import kotlin.test.assertEquals

class CharacterItemMapperTest {
    @Test
    fun databaseRowsBecomeDomainModelsWithoutLosingBookmarkState() {
        val row = Character(
            id = 42,
            name = "Character",
            description = null,
            thumbnail = "https://example.com/thumbnail.jpg",
            urlCount = 1,
            comicCount = 2,
            storyCount = 3,
            eventCount = 4,
            seriesCount = 5,
            mark = true
        )

        assertEquals(
            CharacterItem(
                id = 42,
                name = "Character",
                description = null,
                thumbnail = "https://example.com/thumbnail.jpg",
                urlCount = 1,
                comicCount = 2,
                storyCount = 3,
                eventCount = 4,
                seriesCount = 5,
                mark = true
            ),
            CharacterItemMapper()(row)
        )
    }
}
