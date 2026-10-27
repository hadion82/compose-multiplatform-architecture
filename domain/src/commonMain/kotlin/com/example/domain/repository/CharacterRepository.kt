package com.example.domain.repository


import androidx.paging.PagingData
import com.example.domain.model.CharacterItem
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    @Throws(Exception::class)
    fun loadPagingData(): Flow<PagingData<CharacterItem>>
}
