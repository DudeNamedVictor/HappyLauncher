package com.example.happylauncher.domain.usecases

import android.content.pm.LauncherApps
import android.content.pm.PackageManager
import android.os.UserManager
import com.example.happylauncher.common.base.BaseViewModel
import com.example.happylauncher.presentation.allapps.AllAppsScreenState
import com.example.happylauncher.presentation.allapps.AppUIState
import javax.inject.Inject


class AllAppsUseCase @Inject constructor(
    private val userManager: UserManager,
    private val launcherApps: LauncherApps,
    private val packageManager: PackageManager
) {
    private val appList: MutableList<AppUIState> = mutableListOf()

    fun invoke(): List<AppUIState> {
        for (profile in userManager.userProfiles) {
            for (app in launcherApps.getActivityList(null, profile)) {
                val appUIState = AppUIState(
                    appLabel = app.label.toString(),
                    packageName = app.applicationInfo.packageName,
                    icon = getIcon(app.applicationInfo.packageName),
                    user = profile
                )
                appList.add(appUIState)
            }
        }

        return appList
    }

    fun searchByName(name: String) =
        BaseViewModel.ScreenState.Success(AllAppsScreenState(appList.filter {
            it.appLabel.lowercase().contains(name)
        }))

    private fun getIcon(packageName: String) = packageManager.getApplicationIcon(packageName)

}