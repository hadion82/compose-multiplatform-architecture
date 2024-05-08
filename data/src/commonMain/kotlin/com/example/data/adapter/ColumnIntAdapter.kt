package com.example.data.adapter

import app.cash.sqldelight.ColumnAdapter

class ColumnIntAdapter: ColumnAdapter<Int, Long> {
    override fun decode(databaseValue: Long): Int = databaseValue.toInt()
    override fun encode(value: Int): Long = value.toLong()
}