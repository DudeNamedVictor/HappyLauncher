package com.example.happylauncher.di

import android.content.Context
import android.content.pm.LauncherApps
import android.content.pm.PackageManager
import android.os.BatteryManager
import android.os.UserManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

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

}