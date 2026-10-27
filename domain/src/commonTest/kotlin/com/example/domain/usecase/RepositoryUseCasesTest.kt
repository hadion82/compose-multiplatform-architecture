package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.domain.model.CharacterItem
import com.example.domain.repository.BookmarkRepository
import com.example.domain.repository.CharacterRepository
import com.example.domain.repository.ImageStreamRepository
import com.example.domain.usecase.bookmark.AddBookmarkUseCase
import com.example.domain.usecase.bookmark.LoadBookmarkUseCase
import com.example.domain.usecase.bookmark.RemoveBookmarkUseCase
import com.example.domain.usecase.character.LoadCharacterUseCase
import com.example.domain.usecase.thumbnail.SaveThumbnailUseCase
import com.example.shared.di.Dispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertSame
import kotlin.test.assertTrue

class RepositoryUseCasesTest {
    @Test
    fun pagingUseCasesAcceptDomainRepositoriesWithoutDatabaseModels() {
        val characters = flowOf(PagingData.empty<CharacterItem>())
        val characterRepository = object : CharacterRepository {
            override fun loadPagingData() = characters
        }
        val bookmarkRepository = FakeBookmarkRepository()

        assertSame(characters, LoadCharacterUseCase(characterRepository)(Unit).getOrThrow())
        assertSame(bookmarkRepository.pages, LoadBookmarkUseCase(bookmarkRepository)(Unit).getOrThrow())
    }

    @Test
    fun bookmarkUseCasesPassTheSelectedIdToTheDomainContract() = runBlocking {
        val repository = FakeBookmarkRepository()
        val add = AddBookmarkUseCase(Dispatcher.IO(), repository)
        val remove = RemoveBookmarkUseCase(Dispatcher.IO(), repository)

        add(AddBookmarkUseCase.Params(42)).getOrThrow()
        add(AddBookmarkUseCase.Params(7)).getOrThrow()
        remove(RemoveBookmarkUseCase.Params(42)).getOrThrow()

        assertEquals(setOf(7), repository.bookmarks)
    }

    @Test
    fun bookmarkRepositoryFailuresAreReturnedToTheCaller() = runBlocking {
        val failure = IllegalStateException("Bookmark storage unavailable")
        val repository = object : BookmarkRepository {
            override suspend fun addBookmark(id: Int): Unit = throw failure
            override suspend fun removeBookmark(id: Int): Unit = throw failure
            override fun loadPagingBookmarks(): Flow<PagingData<CharacterItem>> = flowOf()
        }

        val addFailure = AddBookmarkUseCase(Dispatcher.IO(), repository)(AddBookmarkUseCase.Params(42)).exceptionOrNull()
        val removeFailure = RemoveBookmarkUseCase(Dispatcher.IO(), repository)(RemoveBookmarkUseCase.Params(42)).exceptionOrNull()
        for (result in listOf(addFailure, removeFailure)) {
            assertEquals(failure.message, assertIs<IllegalStateException>(result).message)
        }
    }

    @Test
    fun savingThumbnailUsesTheDomainContractAndRejectsMissingUrls() = runBlocking {
        val saves = mutableListOf<Triple<String, String, String>>()
        val repository = object : ImageStreamRepository {
            override suspend fun saveImage(url: String, dir: String, fileName: String) {
                saves += Triple(url, dir, fileName)
            }
        }
        val save = SaveThumbnailUseCase(Dispatcher.IO(), repository)

        assertTrue(save(SaveThumbnailUseCase.Params(null)).isFailure)
        assertTrue(saves.isEmpty())
        save(SaveThumbnailUseCase.Params("https://example.com/thumbnail.jpg")).getOrThrow()

        val (url, dir, fileName) = saves.single()
        assertEquals("https://example.com/thumbnail.jpg", url)
        assertEquals("/MARVEL", dir)
        assertTrue(fileName.startsWith("marvel_"))
        assertTrue(fileName.endsWith(".jpg"))
        assertFalse(fileName.contains('/'))
    }

    private class FakeBookmarkRepository : BookmarkRepository {
        val bookmarks = mutableSetOf<Int>()
        val pages = flowOf(PagingData.empty<CharacterItem>())

        override suspend fun addBookmark(id: Int) {
            bookmarks += id
        }

        override suspend fun removeBookmark(id: Int) {
            bookmarks -= id
        }

        override fun loadPagingBookmarks() = pages
    }
}
