package com.example.shared.translator

fun interface Combine<T1, T2, R>: (T1, T2) -> R