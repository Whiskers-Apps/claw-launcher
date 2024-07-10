package com.whiskersapps.clawlauncher.views.main.views.home.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.views.main.views.home.viewmodel.HomeScreenVM
import com.whiskersapps.clawlauncher.views.main.views.search.view.SearchBar

@Composable
fun HomeScreen(
    openSearchSheet: () -> Unit,
    vm: HomeScreenVM = hiltViewModel()
){
    Column(modifier = Modifier
        .systemBarsPadding()
        .pointerInput(Unit) {
            detectVerticalDragGestures { _, dragAmount ->
                if (dragAmount < 0) {
                    openSearchSheet()
                } else {
                    vm.openNotificationPanel()
                }
            }
        }
        .padding(16.dp)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f, fill = true)
        )

        Column(modifier = Modifier.clickable { openSearchSheet() }) {
            SearchBar(
                text = "",
                onChange = {},
                onDone = {},
                enabled = false,
                placeholder = stringResource(R.string.Search_apps_and_much_more)
            )
        }
    }
}