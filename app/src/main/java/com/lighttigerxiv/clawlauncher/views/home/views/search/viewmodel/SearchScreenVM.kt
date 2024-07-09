package com.lighttigerxiv.clawlauncher.views.home.views.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lighttigerxiv.clawlauncher.shared.data.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenVM @Inject constructor(
    settingsRepository: SettingsRepository
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.Main) {

        }
    }
}