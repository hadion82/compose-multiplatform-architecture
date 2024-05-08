package com.example.kmp.ui.main

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.shared.dispatcher.IntentDispatcher
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.flow.MutableSharedFlow

class AndroidMainPresenter @OptIn(ExperimentalPermissionsApi::class) constructor(
    private val dispatcher: MainScopedDispatcher,
    private val permissionState: PermissionState
): MultiPlatformMainPresenterDelegate, IntentDispatcher<Intention> by dispatcher {

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onThumbnailClick(url: String?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ||
            permissionState.status.isGranted
        ) {
            dispatcher.dispatch(Intention.Effect.SaveThumbnail(url))
        } else {
            permissionState.launchPermissionRequest()
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
actual fun ComposeMainPresenter (intents: MutableSharedFlow<Intention>): MainPresenter {
    val composeScope = rememberCoroutineScope()
    val dispatcher = MainScopedDispatcher(intents, composeScope)
    val permissionState = rememberPermissionState(Manifest.permission.WRITE_EXTERNAL_STORAGE)

    return remember {
        val delegate = AndroidMainPresenter(dispatcher, permissionState)
        MainPresenterImpl(dispatcher, delegate)
    }
}