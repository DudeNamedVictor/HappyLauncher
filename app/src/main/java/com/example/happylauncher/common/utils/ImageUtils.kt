package com.example.happylauncher.common.utils

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.ParcelFileDescriptor


object ImageUtils {
    private const val FILE_DESCRIPTOR_MODE = "r"

    fun uriToBitmap(
        contentResolver: ContentResolver,
        selectedFileUri: Uri
    ): Bitmap? {
        val parcelFileDescriptor: ParcelFileDescriptor? =
            contentResolver.openFileDescriptor(selectedFileUri, FILE_DESCRIPTOR_MODE)
        val fileDescriptor = parcelFileDescriptor?.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor?.close()

        return image
    }

}