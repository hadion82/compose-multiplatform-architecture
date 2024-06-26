package com.example.kmp.di.viewmodel

import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.viewModelFactory


inline fun <reified VM : ViewModel> ComponentActivity.viewModels(crossinline factory: () -> VM): Lazy<VM> =
    viewModels {
        viewModelFactory {
            addInitializer(VM::class) { factory() }
        }
    }

/**
 * [viewModels] helper that allows you to pass a single factory function.
 */
inline fun <reified VM : ViewModel> Fragment.viewModels(crossinline factory: () -> VM): Lazy<VM> =
    viewModels {
        viewModelFactory {
            addInitializer(VM::class) { factory() }
        }
    }

/**
 * [viewModels] helper that allows you to pass a single factory function using a [SavedStateHandle].
 */
inline fun <reified VM : ViewModel> Fragment.viewModels(crossinline factory: (SavedStateHandle) -> VM): Lazy<VM> =
    viewModels {
        viewModelFactory {
            addInitializer(VM::class) {
                factory(createSavedStateHandle())
            }
        }
    }