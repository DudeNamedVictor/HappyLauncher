package com.example.happylauncher.domain.usecases

import android.app.WallpaperManager
import android.content.ContentResolver
import android.net.Uri
import com.example.happylauncher.common.utils.ImageUtils
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject


class WallpaperUseCase @Inject constructor(
    private val wallpaperManager: WallpaperManager,
    private val contentResolver: ContentResolver
) {

    suspend fun updateWallpaper(uri: Uri) = coroutineScope {
        wallpaperManager.setBitmap(ImageUtils.uriToBitmap(contentResolver, uri))
    }

}