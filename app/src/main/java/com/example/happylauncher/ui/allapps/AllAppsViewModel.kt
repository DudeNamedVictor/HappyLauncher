package com.example.happylauncher.ui.allapps

import android.content.ComponentName
import android.content.pm.LauncherApps
import android.content.pm.PackageManager
import android.os.UserHandle
import android.os.UserManager
import com.example.happylauncher.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AllAppsViewModel @Inject constructor(
    private val userManager: UserManager,
    private val launcherApps: LauncherApps,
    private val packageManager: PackageManager
) : BaseViewModel() {
    private val appList: MutableList<AppUIState> = mutableListOf()

    init {
        getAppsList()
    }

    private fun getAppsList() {
        baseViewModelScope.launch {
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

            _viewState.update { ScreenState.Success(AllAppsScreenState(appList)) }
        }
    }

    fun searchByName(name: String) {
        _viewState.value = ScreenState.Success(AllAppsScreenState(appList.filter {
            it.appLabel.lowercase().contains(name)
        }))
    }

    fun launchApp(packageName: String, userHandle: UserHandle) {
        val activityInfo = launcherApps.getActivityList(packageName, userHandle)
        launcherApps.startMainActivity(
            ComponentName(packageName, activityInfo.first().name),
            userHandle,
            null,
            null
        )
    }

    private fun getIcon(packageName: String) = packageManager.getApplicationIcon(packageName)

}