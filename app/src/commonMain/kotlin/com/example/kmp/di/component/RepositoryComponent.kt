package com.example.kmp.di.component

import com.example.data.repository.ImageStreamRepository
import com.example.data.repository.ImageStreamRepositoryImpl
import com.example.data.repository.BookmarkRepository
import com.example.data.repository.BookmarkRepositoryImpl
import com.example.data.repository.CharacterRepository
import com.example.data.repository.CharacterRepositoryImpl
import me.tatarka.inject.annotations.Provides

interface RepositoryComponent {

    @Provides
    fun CharacterRepositoryImpl.bind(): CharacterRepository = this

    @Provides
    fun BookmarkRepositoryImpl.bind(): BookmarkRepository = this

    @Provides
    fun ImageStreamRepositoryImpl.bind(): ImageStreamRepository = this
}