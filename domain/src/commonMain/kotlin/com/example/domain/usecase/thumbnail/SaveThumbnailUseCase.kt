package com.example.domain.usecase.thumbnail

import com.example.data.repository.ImageStreamRepository
import com.example.shared.di.Dispatcher
import com.example.shared.interaction.SuspendingUseCase
import com.example.shared.utils.FileNameUtils
import kotlinx.datetime.Clock
import me.tatarka.inject.annotations.Inject

@Inject
class SaveThumbnailUseCase (
    dispatcher: Dispatcher.IO,
    private val repository: ImageStreamRepository
) : SuspendingUseCase<SaveThumbnailUseCase.Params, Unit>(dispatcher.dispatcher) {

    override suspend fun execute(params: Params) {
        val url = params.url
        requireNotNull(url)
        repository.saveImage(
            url = url,
            dir = "/MARVEL",
            fileName = getFileName(url)
        )
    }

    private fun getFileName(path: String) =
        "${PREFIX}_${Clock.System.now().epochSeconds}.${FileNameUtils.getExtension(path)}"



    class Params(val url: String?)

    companion object {
        const val PREFIX = "marvel"
    }
}