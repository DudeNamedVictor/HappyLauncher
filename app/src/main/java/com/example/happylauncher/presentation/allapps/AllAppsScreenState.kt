package com.example.happylauncher.presentation.allapps

import android.graphics.drawable.Drawable
import android.os.UserHandle


data class AllAppsScreenState(
    val apps: List<AppUIState>
)

data class AppUIState(
    val appLabel: String,
    val packageName: String,
    val icon: Drawable,
    val user: UserHandle
)