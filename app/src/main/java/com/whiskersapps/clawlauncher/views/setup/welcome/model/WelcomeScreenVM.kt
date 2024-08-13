package com.whiskersapps.clawlauncher.views.setup.welcome.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.whiskersapps.clawlauncher.shared.data.SearchEnginesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenVM @Inject constructor(
    private val searchEnginesRepository: SearchEnginesRepository
) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            searchEnginesRepository.data.collect { data ->
                if (!data.loading) {
                    if (data.searchEngines.isEmpty()) {
                        searchEnginesRepository.initEngines()
                    }
                }
            }
        }
    }
}