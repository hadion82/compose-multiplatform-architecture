package com.example.kmp.di.component

import com.example.data.DriverFactory
import com.example.data.createDatabase
import com.example.data.dao.CharacterDao
import com.example.data.dao.CharacterDaoImpl
import com.example.data.dao.PagingKeyDao
import com.example.data.dao.PagingKeyDaoImpl
import com.example.data.database.Character
import com.example.data.database.LocalDatabase
import com.example.data.datasource.local.BookmarkLocalDataSourceImpl
import com.example.shared.di.scope.SingleToneScope
import me.tatarka.inject.annotations.Provides

interface DatabaseComponent {

    @SingleToneScope
    @Provides
    fun provideDatabase(): LocalDatabase = createDatabase(DriverFactory())

//    @Provides
//    fun CharacterDaoImpl.bind(): CharacterDao = this
//
//    @Provides
//    fun PagingKeyDaoImpl.bind(): PagingKeyDao = this
}