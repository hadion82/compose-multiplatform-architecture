package com.example.feature.bookmark

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.PagingData
import com.example.domain.model.CharacterItem
import com.example.domain.repository.BookmarkRepository
import com.example.domain.repository.ImageStreamRepository
import com.example.domain.usecase.bookmark.LoadBookmarkUseCase
import com.example.domain.usecase.bookmark.RemoveBookmarkUseCase
import com.example.domain.usecase.thumbnail.SaveThumbnailUseCase
import com.example.shared.di.Dispatcher
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BookmarkViewModelTest {
    private lateinit var mainDispatcher: TestDispatcher
    private val stores = mutableListOf<ViewModelStore>()

    @BeforeTest
    fun setUp() {
        mainDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(mainDispatcher)
    }

    @AfterTest
    fun tearDown() {
        stores.forEach { it.clear() }
        mainDispatcher.scheduler.runCurrent()
        Dispatchers.resetMain()
    }

    @Test
    fun initialLoadFailureIsIsolatedFromOtherBackStackEntry() = runTest {
        val failingRepository = FakeBookmarkRepository(
            loadFailure = IllegalStateException("Bookmarks unavailable")
        )
        val healthyRepository = FakeBookmarkRepository()
        val failedEntry = createViewModel(failingRepository)
        val healthyEntry = createViewModel(healthyRepository)

        advanceUntilIdle()

        assertIs<Action.Message.FailedToLoadData>(failedEntry.message.value)
        assertNull(failedEntry.pagingData.value)
        assertNotNull(healthyEntry.pagingData.value)
        assertNull(healthyEntry.message.value)
        assertEquals(1, failingRepository.loadCalls)
        assertEquals(1, healthyRepository.loadCalls)
    }

    @Test
    fun bookmarkAndThumbnailActionsUpdateOnlyTheirEntry() = runTest {
        val bookmarks = FakeBookmarkRepository()
        val images = FakeImageStreamRepository()
        val activeEntry = createViewModel(bookmarks, images)
        val otherEntry = createViewModel(bookmarks, images)
        advanceUntilIdle()

        activeEntry.onBookmarkClick(character(id = 42))
        assertEquals(42, bookmarks.removedId.await())

        activeEntry.onThumbnailClick("https://example.com/thumbnail.jpg")
        assertIs<Action.Message.SuccessToSaveImage>(activeEntry.message.filterNotNull().first())

        assertEquals("https://example.com/thumbnail.jpg", images.saved.await().url)
        assertNull(otherEntry.message.value)
        assertNotNull(otherEntry.pagingData.value)
        assertEquals(2, bookmarks.loadCalls)
        advanceUntilIdle()
    }

    @Test
    fun clearingViewModelStoreCancelsInFlightThumbnailSave() = runTest {
        val cancelled = CompletableDeferred<Unit>()
        val images = FakeImageStreamRepository {
            try {
                awaitCancellation()
            } finally {
                cancelled.complete(Unit)
            }
        }
        val viewModel = createViewModel(FakeBookmarkRepository(), images)
        val scopeJob = assertNotNull(viewModel.viewModelScope.coroutineContext[Job])
        advanceUntilIdle()

        viewModel.onThumbnailClick("https://example.com/thumbnail.jpg")
        images.saved.await()
        stores.single().clear()
        cancelled.await()
        scopeJob.join()

        assertTrue(scopeJob.isCancelled)
    }

    private fun createViewModel(
        bookmarks: FakeBookmarkRepository,
        images: FakeImageStreamRepository = FakeImageStreamRepository()
    ): BookmarkViewModel {
        val owner = object : ViewModelStoreOwner {
            override val viewModelStore = ViewModelStore().also { stores += it }
        }
        val factory = viewModelFactory {
            initializer {
                BookmarkViewModel(
                    LoadBookmarkUseCase(bookmarks),
                    RemoveBookmarkUseCase(Dispatcher.IO(), bookmarks),
                    SaveThumbnailUseCase(Dispatcher.IO(), images)
                )
            }
        }
        return ViewModelProvider.create(owner, factory)[BookmarkViewModel::class]
    }

    private class FakeBookmarkRepository(
        private val loadFailure: Exception? = null
    ) : BookmarkRepository {
        var loadCalls = 0
            private set
        val removedId = CompletableDeferred<Int>()

        override fun loadPagingBookmarks(): Flow<PagingData<CharacterItem>> {
            loadCalls++
            loadFailure?.let { throw it }
            return flowOf(PagingData.empty<CharacterItem>())
        }

        override suspend fun addBookmark(id: Int) = Unit

        override suspend fun removeBookmark(id: Int) {
            removedId.complete(id)
        }
    }

    private class FakeImageStreamRepository(
        private val onSave: suspend () -> Unit = {}
    ) : ImageStreamRepository {
        val saved = CompletableDeferred<SavedImage>()

        override suspend fun saveImage(url: String, dir: String, fileName: String) {
            saved.complete(SavedImage(url))
            onSave()
        }
    }

    private data class SavedImage(val url: String)

    private fun character(id: Int) = CharacterItem(
        id = id,
        name = "Character $id",
        urlCount = 0,
        thumbnail = null,
        description = null,
        comicCount = 0,
        storyCount = 0,
        eventCount = 0,
        seriesCount = 0,
        mark = true
    )
}
