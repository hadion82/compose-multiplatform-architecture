package com.example.kmp.ui.main

import com.example.domain.usecase.character.LoadCharacterUseCase
import com.example.domain.usecase.thumbnail.SaveThumbnailUseCase
import com.example.shared.hanlder.AbstractEffectHandler
import me.tatarka.inject.annotations.Inject

@Inject
class MainEffectHandler(
    dispatcher: MainActionDispatcher,
    private val loadCharacterUseCase: LoadCharacterUseCase,
    private val saveThumbnailUseCase: SaveThumbnailUseCase
) : AbstractEffectHandler<Intention.Effect>(),
    MainActionDispatcher by dispatcher {

    override suspend fun execute(intent: Intention.Effect) {
        when (intent) {
            is Intention.Effect.LoadCharacters -> {
                loadCharacterUseCase(Unit)
                    .onSuccess { dispatch(Action.LoadPagingData(it)) }
                    .onFailure { dispatch(Action.Message.FailedToLoadData()) }
            }

            is Intention.Effect.SaveThumbnail -> {
                saveThumbnailUseCase(SaveThumbnailUseCase.Params(intent.path))
                    .onSuccess { dispatch(Action.Message.SuccessToSaveImage()) }
                    .onFailure { dispatch(Action.Message.FailedToSaveImage()) }
            }
        }
    }
}