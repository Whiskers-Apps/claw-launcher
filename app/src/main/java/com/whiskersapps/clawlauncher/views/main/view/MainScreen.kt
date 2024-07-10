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
import com.whiskersapps.clawlauncher.views.main.viewmodel.MainScreenUiState
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
    vm: MainScreenVM = hiltViewModel()
) {
    val uiState = vm.uiState.collectAsState().value
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

    uiState?.let {

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                SearchScreen(
                    mainScreenUiState = uiState,
                    closeSheet = { scope.launch { sheetState.hide() } })
            },
            sheetPeekHeight = 0.dp,
            sheetDragHandle = {},
            sheetShape = AbsoluteCutCornerShape(0.dp)
        ) {
            Box {
                Wallpaper(uiState = uiState, blurRadius = 0.dp)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    HorizontalPager(modifier = Modifier.fillMaxSize(), state = pagerState) { page ->
                        if (page == 0) {
                            HomeScreen(openSearchSheet = { scope.launch { sheetState.expand() } })
                        }

                        if (page == 1) {
                            AppsScreen(
                                mainScreenUiState = uiState,
                                navigateHome = {
                                    scope.launch {
                                        delay(500)
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
}

@Composable
fun Wallpaper(uiState: MainScreenUiState, blurRadius: Dp) {
    if (uiState.wallpaper != null) {
        val wallpaper by remember { derivedStateOf { uiState.wallpaper.asImageBitmap() } }

        Image(
            modifier = Modifier
                .fillMaxSize()
                .blur(blurRadius),
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
}