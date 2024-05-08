package com.example.shared.platform.media

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHPhotoLibrary
import platform.UIKit.UIImage
import platform.UIKit.UIImageWriteToSavedPhotosAlbum

actual interface Downloader {
    actual fun copyToFile(sourcePath: String, dir: String, fileName: String)
}

actual object MediaUtils : Downloader {
    override fun copyToFile(sourcePath: String, dir: String, fileName: String) {
        val uiImage = UIImage(sourcePath)
        if (PHPhotoLibrary.authorizationStatus() == PHAuthorizationStatusAuthorized) {
            saveImage(uiImage)
        } else {
            PHPhotoLibrary.requestAuthorization { status ->
                if (status == PHAuthorizationStatusAuthorized) {
                    saveImage(uiImage)
                }
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun saveImage(uiImage: UIImage) =
        UIImageWriteToSavedPhotosAlbum(uiImage, null, null, null)
}