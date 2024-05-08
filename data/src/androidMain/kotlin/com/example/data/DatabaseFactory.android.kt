package com.example.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.data.database.LocalDatabase
import com.example.shared.platform.startup.androidContext

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(LocalDatabase.Schema, androidContext, "database.db")
    }
}