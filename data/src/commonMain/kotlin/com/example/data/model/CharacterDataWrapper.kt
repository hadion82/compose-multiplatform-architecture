package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterDataWrapper(
    val code: Int?,
    val status: String?,
    val copyright: String?,
    val attributionText: String?,
    val attributionHTML: String?,
    val data: CharacterDataContainer?,
    val etag: String?,
)

@Serializable
data class CharacterDataContainer(
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val results: List<CharacterData>?
)

@Serializable
data class CharacterData(
    val id: Int?,
    val name: String?,
    val description: String?,
    val modified: String?,
    val resourceURI: String?,
    val urls: List<Url>?,
    val thumbnail: Image?,
    val comics: ComicList?,
    val stories: StoryList?,
    val events: EventList?,
    val series: SeriesList?,
)

@Serializable
data class Url(
    val type: String?,
    val url: String?,
)

@Serializable
data class Image(
    val path: String?,
    val extension: String?
)

@Serializable
data class ComicList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<ComicSummary>?,
)

@Serializable
data class ComicSummary(
    val resourceURI: String?,
    val name: String?
)

@Serializable
data class StoryList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<StorySummary>?,
)

@Serializable
data class StorySummary(
    val resourceURI: String?,
    val name: String?,
    val type: String?
)

@Serializable
data class EventList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<EventSummary>?,
)

@Serializable
data class EventSummary(
    val resourceURI: String?,
    val name: String?
)

@Serializable
data class SeriesList(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<SeriesSummary>?,
)

@Serializable
data class SeriesSummary(
    val resourceURI: String?,
    val name: String?
)