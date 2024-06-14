package com.example.happylauncher.ui.settings

import android.app.WallpaperManager
import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.ParcelFileDescriptor
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import com.example.happylauncher.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDataStore: DataStore<Preferences>,
    private val wallpaperManager: WallpaperManager,
    private val contentResolver: ContentResolver
): BaseViewModel() {
    private val transparent: Channel<Float> = Channel()

    init {
        _viewState.update { ScreenState.Loading }
        getTransparentStep()
        initializeScreen()
    }

    private fun getTransparentStep() {
        baseViewModelScope.launch {
            settingsDataStore.data.collect {
                transparent.send(it[TRANSPARENT] ?: DEFAULT_BACKGROUND_TRANSPARENT)
            }
        }
    }

    private fun initializeScreen() {
        baseViewModelScope.launch {
            val a = transparent.receive()
            _viewState.update {
                ScreenState.Success(SettingsScreenState(TransparentWidgetState(a)))
            }
        }
    }

    fun updateTransparent(transparentStep: Float) {
        baseViewModelScope.launch {
            settingsDataStore.edit { it[TRANSPARENT] = transparentStep }
        }
        _viewState.update {
            ScreenState.Success(SettingsScreenState(TransparentWidgetState(transparentStep)))
        }
    }

    fun updateWallpaper(uri: Uri) {
        wallpaperManager.setBitmap(uriToBitmap(uri))
    }

    private fun uriToBitmap(selectedFileUri: Uri): Bitmap? {
        val parcelFileDescriptor: ParcelFileDescriptor? =
            contentResolver.openFileDescriptor(selectedFileUri, FILE_DESCRIPTOR_MODE)
        val fileDescriptor = parcelFileDescriptor?.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor?.close()

        return image
    }

    companion object {
        val TRANSPARENT = floatPreferencesKey("transparent")
        const val DEFAULT_BACKGROUND_TRANSPARENT = 2f
        const val FILE_DESCRIPTOR_MODE = "r"
    }

}