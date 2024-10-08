package com.whiskersapps.clawlauncher.views.main.views.settings.views.search_engines.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.whiskersapps.clawlauncher.shared.model.SearchEngine
import com.whiskersapps.clawlauncher.shared.utils.getCachedImageRequest
import com.whiskersapps.clawlauncher.shared.utils.getFaviconUrl
import com.whiskersapps.clawlauncher.shared.view.theme.Typography

@Composable
fun SearchEngineCard(
    onClick: () -> Unit = {},
    searchEngine: SearchEngine,
    padding: Dp = 16.dp,
    url: String = searchEngine.query,
    backgroundColor: Color = MaterialTheme.colorScheme.background
){

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(backgroundColor)
        .clickable { onClick() }
        .padding(padding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(32.dp),
            model = getCachedImageRequest(getFaviconUrl(searchEngine.query)),
            contentDescription = "${searchEngine.name} icon"
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = searchEngine.name,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = url,
                color = MaterialTheme.colorScheme.onBackground,
                style = Typography.labelSmall
            )
        }
    }
}