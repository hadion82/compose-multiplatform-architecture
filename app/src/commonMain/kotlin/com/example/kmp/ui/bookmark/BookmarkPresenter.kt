package com.example.kmp.ui.bookmark

import com.example.data.model.CharacterItem


interface CommonBookmarkPresenter {
    fun onBookmarkClick(item: CharacterItem)
}

interface PlatformBookmarkPresenterDelegate {
    fun onThumbnailClick(url: String?)
}

interface BookmarkPresenter : CommonBookmarkPresenter, PlatformBookmarkPresenterDelegate
