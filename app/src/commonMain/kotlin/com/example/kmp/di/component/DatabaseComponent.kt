package com.example.kmp.di.component

import com.example.data.DriverFactory
import com.example.data.createDatabase
import com.example.data.database.LocalDatabase
import com.example.shared.di.scope.SingleToneScope
import me.tatarka.inject.annotations.Provides

interface DatabaseComponent {

    @SingleToneScope
    @Provides
    fun provideDatabase(): LocalDatabase = createDatabase(DriverFactory())
}
