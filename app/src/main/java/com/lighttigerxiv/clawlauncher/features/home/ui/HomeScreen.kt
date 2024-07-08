package com.lighttigerxiv.clawlauncher.features.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lighttigerxiv.clawlauncher.features.home.viewmodel.HomeScreenVM

@Composable
fun HomeScreen(
    vm: HomeScreenVM = hiltViewModel()
) {
    val uiState = vm.uiState.collectAsState().value

    uiState?.let {
        Column(
            modifier = Modifier.systemBarsPadding()
        ) {
            LazyColumn {
                items(items = uiState.appShortcuts) { app ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable { vm.openApp(app.packageName) }
                    ) {

                        val image by remember {
                            derivedStateOf { app.icon.asImageBitmap() }
                        }

                        Image(
                            bitmap = image,
                            contentDescription = "${app.packageName} icon"
                        )

                        Text(text = app.name)
                    }
                }
            }
        }
    }
}