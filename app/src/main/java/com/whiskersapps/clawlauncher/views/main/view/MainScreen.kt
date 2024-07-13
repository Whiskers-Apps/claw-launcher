package com.whiskersapps.clawlauncher.views.main.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.views.main.viewmodel.MainScreenVM
import com.whiskersapps.clawlauncher.views.main.views.apps.view.AppsScreen
import com.whiskersapps.clawlauncher.views.main.views.home.view.HomeScreen
import com.whiskersapps.clawlauncher.views.main.views.search.view.SearchBar
import com.whiskersapps.clawlauncher.views.main.views.search.view.SearchScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    vm: MainScreenVM = hiltViewModel(),
    navigateToSettings : () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val sheetState = rememberModalBottomSheetState()
    val scaffoldState = rememberBottomSheetScaffoldState(sheetState)
    val scope = rememberCoroutineScope()

    BackHandler {
        if (pagerState.currentPage != 0) {
            scope.launch { pagerState.animateScrollToPage(0) }
        }

        if (sheetState.isVisible) {
            scope.launch { sheetState.hide() }
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            SearchScreen(
                sheetState = sheetState,
                closeSheet = { scope.launch { sheetState.hide() } }
            )
        },
        sheetPeekHeight = 0.dp,
        sheetDragHandle = {},
        sheetShape = AbsoluteCutCornerShape(0.dp),
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
                        HomeScreen(
                            openSearchSheet = { scope.launch { sheetState.expand() } },
                            navigateToSettings = { navigateToSettings() }
                        )
                    }

                    if (page == 1) {
                        AppsScreen(
                            navigateHome = {
                                scope.launch {
                                    pagerState.scrollToPage(0)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}