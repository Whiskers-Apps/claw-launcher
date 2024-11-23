package com.whiskersapps.clawlauncher.views.main.view

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
import androidx.navigation.NavController
import com.whiskersapps.clawlauncher.shared.model.Routes
import com.whiskersapps.clawlauncher.shared.utils.OnActivityPaused
import com.whiskersapps.clawlauncher.views.main.intent.MainScreenAction
import com.whiskersapps.clawlauncher.views.main.model.MainScreenVM
import com.whiskersapps.clawlauncher.views.main.views.apps.view.AppsScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.home.view.HomeScreenRoot
import com.whiskersapps.clawlauncher.views.main.views.search.view.SearchScreen
import com.whiskersapps.clawlauncher.views.main.views.search.view.SearchScreenRoot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MainScreenRoot(
    navController: NavController,
    vm: MainScreenVM = hiltViewModel()
) {
    MainScreen(
        vm = vm,
        onAction = { action ->
            when (action) {
                MainScreenAction.OnNavigateToSettings -> navController.navigate(Routes.Main.Settings.MAIN)
                MainScreenAction.OnNavigateToLockSettings -> navController.navigate("${Routes.Main.Settings.LOCK}/true")
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    vm: MainScreenVM,
    onAction: (MainScreenAction) -> Unit
) {
    val state = vm.state.collectAsState().value
    val pagerState = rememberPagerState(pageCount = { if(state.settings.disableAppsScreen) 1 else 2 })
    val sheetState = rememberModalBottomSheetState()
    val scaffoldState = rememberBottomSheetScaffoldState(sheetState)
    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    if (!state.loading) {

        /// Sets the pager page back to 0 and closes the sheet
        fun resetSheetAndPager() {
            if (pagerState.currentPage != 0) {
                scope.launch { pagerState.animateScrollToPage(0) }
            }

            if (sheetState.hasExpandedState) {
                scope.launch { sheetState.hide() }
            }
        }

        OnActivityPaused {
            resetSheetAndPager()
        }

        BackHandler {
            resetSheetAndPager()
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

                    HorizontalPager(modifier = Modifier.fillMaxSize(), state = pagerState) { page ->
                        if (page == 0) {

                            HomeScreenRoot(
                                navigateToSettings = {
                                    onAction(MainScreenAction.OnNavigateToSettings)
                                                     },
                                navigateToLockSettings = {
                                    onAction(MainScreenAction.OnNavigateToLockSettings)
                                },
                                sheetState = sheetState
                            )
                        }

                        if (page == 1) {
                            AppsScreenRoot(
                                pagerState = pagerState
                            )
                        }
                    }
                }
            }
        }
    }
}