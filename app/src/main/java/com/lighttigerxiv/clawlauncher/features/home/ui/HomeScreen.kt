package com.lighttigerxiv.clawlauncher.features.home.ui

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lighttigerxiv.clawlauncher.features.home.viewmodel.HomeScreenVM

@Composable
fun HomeScreen(
    vm: HomeScreenVM = hiltViewModel()
) {
    val uiState = vm.uiState.collectAsState().value

    uiState?.let {
        Box {
            if (uiState.wallpaper != null) {
                val wallpaper by remember { derivedStateOf { uiState.wallpaper.asImageBitmap() } }

                Image(
                    modifier = Modifier.fillMaxSize(),
                    bitmap = wallpaper,
                    contentDescription = "wallpaper",
                    contentScale = ContentScale.Crop
                )

            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                )
            }

            Column {
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
}