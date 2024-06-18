package com.example.happylauncher.presentation.allapps

import android.content.ComponentName
import android.content.pm.LauncherApps
import android.os.UserHandle
import com.example.happylauncher.R
import com.example.happylauncher.common.base.BaseViewModel
import com.example.happylauncher.domain.usecases.AllAppsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AllAppsViewModel @Inject constructor(
    private val allAppsUseCase: AllAppsUseCase,
    private val launcherApps: LauncherApps
) : BaseViewModel() {

    init {
        getAppsList()
    }

    private fun getAppsList() {
        _viewState.update { ScreenState.Loading }
        baseViewModelScope.launch {
            _viewState.update { ScreenState.Success(AllAppsScreenState(allAppsUseCase.invoke())) }
        }
    }

    fun searchByName(name: String) {
        _viewState.value = allAppsUseCase.searchByName(name)
    }

    fun launchApp(packageName: String, userHandle: UserHandle) {
        val activityInfo = launcherApps.getActivityList(packageName, userHandle)
        try {
            launcherApps.startMainActivity(
                ComponentName(packageName, activityInfo.first().name),
                userHandle,
                null,
                null
            )
        } catch (e: Exception) {
            showError(R.string.launch_app_error)
        }
    }

}