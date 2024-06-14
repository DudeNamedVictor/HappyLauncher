package com.example.happylauncher.di

import android.app.WallpaperManager
import android.content.ContentResolver
import android.content.Context
import android.content.pm.LauncherApps
import android.content.pm.PackageManager
import android.os.BatteryManager
import android.os.UserManager
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    private const val SETTINGS_STORE_NAME = "settings_data_store"

    @Singleton
    @Provides
    fun provideUserManager(@ApplicationContext context: Context): UserManager =
        context.getSystemService(Context.USER_SERVICE) as UserManager

    @Singleton
    @Provides
    fun provideLauncherApps(@ApplicationContext context: Context): LauncherApps =
        context.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps

    @Singleton
    @Provides
    fun providePackageManager(@ApplicationContext context: Context): PackageManager =
        context.packageManager

    @Singleton
    @Provides
    fun provideBatteryLevel(@ApplicationContext context: Context): Int =
        (context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager).getIntProperty(
            BatteryManager.BATTERY_PROPERTY_CAPACITY
        )

    @Singleton
    @Provides
    fun provideSettingsDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            produceFile = { context.preferencesDataStoreFile(SETTINGS_STORE_NAME) }
        )
    }

    @Singleton
    @Provides
    fun provideWallpaperManager(@ApplicationContext context: Context): WallpaperManager =
        WallpaperManager.getInstance(context)

    @Singleton
    @Provides
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver =
        context.contentResolver
}