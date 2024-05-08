package com.example.data

import app.cash.sqldelight.db.SqlDriver
import com.example.data.adapter.ColumnIntAdapter
import com.example.data.database.Character
import com.example.data.database.LocalDatabase
import com.example.data.database.PagingKey

expect class DriverFactory() {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): LocalDatabase {
    val driver = driverFactory.createDriver()
    return LocalDatabase(
        driver,
        createCharacterAdapter(),
        createPagingAdapter()
    )
}

private fun createCharacterAdapter() = with(ColumnIntAdapter()) {
    Character.Adapter(
        idAdapter = this,
        urlCountAdapter = this,
        comicCountAdapter = this,
        storyCountAdapter = this,
        eventCountAdapter = this,
        seriesCountAdapter = this
    )
}

private fun createPagingAdapter() = with(ColumnIntAdapter()) {
    PagingKey.Adapter(
        keyAdapter = this
    )
}