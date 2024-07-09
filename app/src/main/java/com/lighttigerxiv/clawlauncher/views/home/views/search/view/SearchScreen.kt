package com.lighttigerxiv.clawlauncher.views.home.views.search.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lighttigerxiv.clawlauncher.views.home.view.Wallpaper
import com.lighttigerxiv.clawlauncher.views.home.viewmodel.HomeScreenUIState
import com.lighttigerxiv.clawlauncher.views.home.views.search.viewmodel.SearchScreenVM

@Composable
fun SearchScreen(
    homeScreenUIState: HomeScreenUIState,
    vm: SearchScreenVM = hiltViewModel()
) {

    Box {
        Wallpaper(uiState = homeScreenUIState, blurRadius = 0.dp)

        Column {
            SearchBar(text = "", onChange = {}, onDone = {})
        }
    }
}