package com.whiskersapps.clawlauncher.launcher

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.whiskersapps.clawlauncher.launcher.LauncherScreenState.Loaded
import com.whiskersapps.clawlauncher.launcher.LauncherScreenState.Loading
import com.whiskersapps.clawlauncher.launcher.composables.LoadingCat
import com.whiskersapps.clawlauncher.launcher.home.HomeScreenRoot
import com.whiskersapps.clawlauncher.shared.utils.OnActivityPaused
import com.whiskersapps.clawlauncher.launcher.apps.AppsScreenRoot
import com.whiskersapps.clawlauncher.launcher.search.SearchScreenRoot
import com.whiskersapps.clawlauncher.shared.view.theme.useDarkTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun LauncherScreenRoot(
    vm: LauncherScreenVM = koinViewModel()
) {
    LauncherScreen(vm)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LauncherScreen(
    vm: LauncherScreenVM,
) {
    when (val state = vm.state.collectAsState().value) {
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
            val view = LocalView.current
            val useDarkTheme = useDarkTheme(settings.darkMode)
            val window = (view.context as Activity).window

            fun reset() {
                scope.launch(Dispatchers.Main) {
                    pagerState.animateScrollToPage(0)
                    sheetState.hide()
                }
            }

            fun applyStatusBarColor() {
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                    !useDarkTheme
            }

            fun applyHomeBarColor() {
                WindowCompat.getInsetsController(
                    window,
                    window.decorView
                ).isAppearanceLightStatusBars =
                    false
            }

            LaunchedEffect(pagerState.targetPage) {
                val targetPage = pagerState.targetPage

                scope.launch(Dispatchers.Main) {
                    if (targetPage == 0) {
                        applyHomeBarColor()
                    } else {
                        applyStatusBarColor()
                    }
                }
            }

            LaunchedEffect(sheetState.currentValue) {
                val currentValue = sheetState.currentValue

                scope.launch(Dispatchers.Main) {
                    if (currentValue == SheetValue.Hidden || currentValue == SheetValue.PartiallyExpanded) {
                        applyHomeBarColor()
                    } else {
                        applyStatusBarColor()
                    }
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
                            scope.launch(Dispatchers.Main) {
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