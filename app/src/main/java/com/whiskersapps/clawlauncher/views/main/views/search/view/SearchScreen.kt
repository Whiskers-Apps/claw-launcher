package com.whiskersapps.clawlauncher.views.main.views.search.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.view.theme.Typography
import com.whiskersapps.clawlauncher.views.main.view.Wallpaper
import com.whiskersapps.clawlauncher.views.main.viewmodel.MainScreenUiState
import com.whiskersapps.clawlauncher.views.main.views.search.viewmodel.SearchScreenVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    mainScreenUiState: MainScreenUiState,
    vm: SearchScreenVM = hiltViewModel(),
    closeSheet: () -> Unit
) {

    val uiState = vm.uiState.collectAsState().value
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    uiState?.let {

        Box {
            Wallpaper(uiState = mainScreenUiState, blurRadius = 4.dp)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.9f)
                    .background(MaterialTheme.colorScheme.background)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .padding(16.dp)
            ) {

                SearchBar(
                    text = uiState.searchText,
                    onChange = { vm.updateSearchText(it) },
                    placeholder = stringResource(R.string.Search_apps_and_much_more)
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (uiState.appShortcuts.isNotEmpty()) {

                    Text(
                        text = "Apps",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Typography.titleSmall
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                    ) {
                        itemsIndexed(
                            uiState.appShortcuts,
                            key = { index, app -> "${index}-${app.packageName}" }) { _, app ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        scope.launch {
                                            vm.openApp(app.packageName)
                                            delay(1000)

                                            focusManager.clearFocus()
                                            keyboardController?.hide()

                                            closeSheet()
                                        }
                                    }
                                    .padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                val image by remember {
                                    derivedStateOf { app.icon.asImageBitmap() }
                                }

                                Box(
                                    modifier = Modifier
                                        .size(56.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.background)
                                ) {
                                    Image(
                                        modifier = Modifier.fillMaxSize(),
                                        bitmap = image,
                                        contentDescription = "${app.packageName} icon"
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = app.name,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    style = Typography.labelMedium,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }

                    Text(
                        text = "Bookmarks",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Typography.titleSmall
                    )

                    Text(
                        text = "Web",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = Typography.titleSmall
                    )
                }
            }
        }
    }
}