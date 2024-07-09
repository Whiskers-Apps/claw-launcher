package com.lighttigerxiv.clawlauncher.features.setup.permissions.viewmodel

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lighttigerxiv.clawlauncher.features.setup.permissions.ui.isAtLeastAndroid13
import com.lighttigerxiv.clawlauncher.shared.utils.hasManageAllFilesPermission
import com.lighttigerxiv.clawlauncher.shared.utils.hasReadMediaPermission
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PermissionsScreenVM @Inject constructor(
    private val app: Application
) : ViewModel() {

    private val _uiState = MutableStateFlow<PermissionsScreenUiState?>(null)
    val uiState = _uiState.asStateFlow()

    init {
        checkPermissions()
    }

    fun checkPermissions() {
        viewModelScope.launch(Dispatchers.Main) {

            if(isAtLeastAndroid13()){
                println("Permission: ${hasManageAllFilesPermission()}")
            }

            _uiState.update {
                PermissionsScreenUiState(
                    readMediaPermissions = if (isAtLeastAndroid13()) hasReadMediaPermission(app) else true,
                    allFilesPermission = if (isAtLeastAndroid13()) hasManageAllFilesPermission() else true
                )
            }
        }
    }
}