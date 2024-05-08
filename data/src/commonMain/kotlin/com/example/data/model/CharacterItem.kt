package com.example.data.model

data class CharacterItem(
    val id: Int,
    val name: String?,
    val urlCount: Int,
    val thumbnail: String?,
    val description: String?,
    val comicCount: Int,
    val storyCount: Int,
    val eventCount: Int,
    val seriesCount: Int,
    val mark: Boolean
)