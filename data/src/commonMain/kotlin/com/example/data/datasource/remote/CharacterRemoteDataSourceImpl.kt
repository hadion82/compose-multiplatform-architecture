package com.example.data.datasource.remote
import com.example.data.api.MarvelApi
import com.example.data.model.CharacterDataWrapper
import com.example.shared.di.scope.SingleToneScope
import me.tatarka.inject.annotations.Inject

@SingleToneScope
@Inject
class CharacterRemoteDataSourceImpl (
    private val api: MarvelApi
) : CharacterRemoteDataSource {

    override suspend fun getCharacters(offset: Int, limit: Int): CharacterDataWrapper =
        api.getCharacters(offset, limit)
}