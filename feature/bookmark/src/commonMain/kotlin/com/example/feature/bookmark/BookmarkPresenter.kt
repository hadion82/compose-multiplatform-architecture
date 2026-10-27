package com.example.feature.bookmark

import com.example.domain.model.CharacterItem


interface CommonBookmarkPresenter {
    fun onBookmarkClick(item: CharacterItem)
}

interface PlatformBookmarkPresenterDelegate {
    fun onThumbnailClick(url: String?)
}

interface BookmarkPresenter : CommonBookmarkPresenter, PlatformBookmarkPresenterDelegate
