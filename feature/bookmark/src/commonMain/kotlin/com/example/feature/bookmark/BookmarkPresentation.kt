package com.example.feature.bookmark

sealed interface Action {

    sealed interface Message : Action {
        class FailedToRemoveBookmark : Message
        class SuccessToSaveImage : Message
        class FailedToSaveImage : Message
        class FailedToLoadData : Message
    }
}
