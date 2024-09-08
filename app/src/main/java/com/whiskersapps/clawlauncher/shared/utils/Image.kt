package com.whiskersapps.clawlauncher.shared.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.request.CachePolicy
import coil.request.ImageRequest

@Composable
fun getCachedImageRequest(url: String): ImageRequest{
    return ImageRequest.Builder(LocalContext.current)
        .data(url)
        .crossfade(true)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .build()
}