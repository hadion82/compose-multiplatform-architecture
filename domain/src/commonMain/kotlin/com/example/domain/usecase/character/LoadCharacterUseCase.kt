package com.example.domain.usecase.character

import app.cash.paging.PagingData
import app.cash.paging.map
import com.example.data.model.CharacterItem
import com.example.data.repository.CharacterRepository
import com.example.domain.mapper.CharacterItemMapper
import com.example.shared.interaction.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Inject

@Inject
class LoadCharacterUseCase(
    private val characterRepository: CharacterRepository,
    private val mapper: CharacterItemMapper
) : UseCase<Unit, Flow<PagingData<CharacterItem>>> {

    override fun execute(params: Unit) =
        characterRepository.loadPagingData()
            .map { it.map(mapper) }
}