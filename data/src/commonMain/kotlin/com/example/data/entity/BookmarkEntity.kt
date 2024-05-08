package com.example.data.entity


data class BookmarkEntity(
    val id: Int,
    val name: String?,
    val description: String?,
    val thumbnail: String?,
    val urlCount: Int,
    val comicCount: Int,
    val storyCount: Int,
    val eventCount: Int,
    val seriesCount: Int,
    val mark: Boolean
)