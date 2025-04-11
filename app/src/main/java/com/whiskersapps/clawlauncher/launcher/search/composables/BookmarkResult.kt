package com.whiskersapps.clawlauncher.launcher.search.composables

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.whiskersapps.clawlauncher.launcher.search.SearchScreenAction.OnCloseSheet
import com.whiskersapps.clawlauncher.launcher.search.SearchScreenAction.OnOpenUrl
import com.whiskersapps.clawlauncher.shared.model.Bookmark
import com.whiskersapps.clawlauncher.shared.utils.getCachedImageRequest
import com.whiskersapps.clawlauncher.shared.utils.getFaviconUrl
import com.whiskersapps.clawlauncher.shared.view.theme.SMALL_LABEL_STYLE
import com.whiskersapps.clawlauncher.shared.view.theme.TINY_LABEL_STYLE
import com.whiskersapps.clawlauncher.shared.view.theme.Typography

@Composable
fun BookmarkResult(
    bookmark: Bookmark,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable {
                onClick()
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .clip(CircleShape)
                .size(42.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            model = getCachedImageRequest(getFaviconUrl(bookmark.url)),
            contentDescription = "${bookmark.name} icon"
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = bookmark.name,
                color = MaterialTheme.colorScheme.onBackground,
                style = SMALL_LABEL_STYLE
            )

            Text(
                text = bookmark.url,
                color = MaterialTheme.colorScheme.onBackground,
                style = TINY_LABEL_STYLE
            )
        }
    }
}