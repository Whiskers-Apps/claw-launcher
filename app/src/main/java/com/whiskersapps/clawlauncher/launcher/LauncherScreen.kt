package com.whiskersapps.clawlauncher.launcher

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whiskersapps.clawlauncher.launcher.composables.LoadingCat
import com.whiskersapps.clawlauncher.shared.utils.OnActivityPaused
import com.whiskersapps.clawlauncher.launcher.LauncherScreenState.*
import com.whiskersapps.clawlauncher.views.main.views.apps.view.AppsScreenRoot
import com.whiskersapps.clawlauncher.launcher.home.HomeScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.search.view.SearchScreenRoot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LauncherScreenRoot(
    vm: LauncherScreenVM = hiltViewModel()
) {
    LauncherScreen(vm)
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LauncherScreen(
    vm: LauncherScreenVM,
) {
    val state = vm.state.collectAsState().value

    when (state) {
        Loading -> {
            LoadingCat()
        }

        is Loaded -> {
            val settings = state.settings
            val sheetState = rememberModalBottomSheetState()
            val scaffoldState = rememberBottomSheetScaffoldState(sheetState)
            val scope = rememberCoroutineScope()
            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current
            val pagerState =
                rememberPagerState(pageCount = { if (settings.disableAppsScreen) 1 else 2 })

            fun reset() {
                scope.launch(Dispatchers.Main) {
                    pagerState.animateScrollToPage(0)
                    sheetState.hide()
                }
            }

            BackHandler {
                reset()
            }

            OnActivityPaused {
                reset()
            }

            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetContent = {
                    SearchScreenRoot(
                        sheetState = sheetState,
                        onCloseSheet = {
                            scope.launch(Dispatchers.IO) {
                                sheetState.hide()
                                focusManager.clearFocus()
                                keyboardController?.hide()
                            }
                        }
                    )
                },
                sheetPeekHeight = 0.dp,
                sheetDragHandle = {},
                sheetShape = RoundedCornerShape(0.dp),
                sheetMaxWidth = Dp.Unspecified,
                contentColor = Color.Transparent,
                containerColor = Color.Transparent,
                sheetContentColor = Color.Transparent,
                sheetContainerColor = Color.Transparent,
                sheetShadowElevation = 0.dp,
                sheetTonalElevation = 0.dp
            ) {
                Box {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {

                        HorizontalPager(
                            modifier = Modifier.fillMaxSize(),
                            state = pagerState
                        ) { page ->
                            if (page == 0) {

                                HomeScreenRoot(sheetState)
                            }

                            if (page == 1) {
                                AppsScreenRoot(pagerState)
                            }
                        }
                    }

                }
            }
        }
    }
}