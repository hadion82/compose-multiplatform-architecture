package com.example.domain.usecase.character

import androidx.paging.PagingData
import com.example.domain.model.CharacterItem
import com.example.domain.repository.CharacterRepository
import com.example.shared.interaction.UseCase
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject

@Inject
class LoadCharacterUseCase(
    private val characterRepository: CharacterRepository
) : UseCase<Unit, Flow<PagingData<CharacterItem>>> {

    override fun execute(params: Unit) =
        characterRepository.loadPagingData()
}
