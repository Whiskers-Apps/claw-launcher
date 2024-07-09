package com.whiskersapps.clawlauncher.views.home.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.whiskersapps.clawlauncher.views.home.viewmodel.HomeScreenUIState
import com.whiskersapps.clawlauncher.views.home.viewmodel.HomeScreenVM
import com.whiskersapps.clawlauncher.views.home.views.apps.view.AppsScreen
import com.whiskersapps.clawlauncher.views.home.views.search.view.SearchScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    vm: HomeScreenVM = hiltViewModel()
) {
    val uiState = vm.uiState.collectAsState().value
    val pagerState = rememberPagerState(pageCount = { 2 })
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BackHandler {
        if (pagerState.currentPage != 0) {
            scope.launch { pagerState.animateScrollToPage(0) }
        }
    }

    uiState?.let {

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
                SearchScreen(homeScreenUIState = uiState)
            },
            sheetPeekHeight = 0.dp
        ) {
            Box {
                Wallpaper(uiState = uiState, blurRadius = 0.dp)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    HorizontalPager(modifier = Modifier.fillMaxSize(), state = pagerState) { page ->
                        if (page == 0) {
                            Column(modifier = Modifier.systemBarsPadding()) {
                                Spacer(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight(1f, fill = true)
                                )


                            }
                        }

                        if (page == 1) {
                            AppsScreen(
                                homeScreenUiState = uiState,
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
fun Wallpaper(uiState: HomeScreenUIState, blurRadius: Dp) {
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