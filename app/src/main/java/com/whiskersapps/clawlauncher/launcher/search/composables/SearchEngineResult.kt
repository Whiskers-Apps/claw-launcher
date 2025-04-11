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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.whiskersapps.clawlauncher.R
import com.whiskersapps.clawlauncher.shared.model.SearchEngine
import com.whiskersapps.clawlauncher.shared.utils.getCachedImageRequest
import com.whiskersapps.clawlauncher.shared.utils.getFaviconUrl
import com.whiskersapps.clawlauncher.shared.view.composables.sidePadding
import com.whiskersapps.clawlauncher.shared.view.theme.REGULAR_LABEL_STYLE
import com.whiskersapps.clawlauncher.shared.view.theme.SMALL_LABEL_STYLE
import com.whiskersapps.clawlauncher.shared.view.theme.Typography

@Composable
fun SearchEngineResult(
    searchEngine: SearchEngine,
    searchText: String,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.sidePadding()) {
        Text(
            text = stringResource(R.string.SearchScreen_web),
            color = MaterialTheme.colorScheme.onBackground,
            style = Typography.titleSmall
        )

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
                    .size(42.dp),
                model = getCachedImageRequest(getFaviconUrl(searchEngine.query)),
                contentDescription = "${searchEngine.name} icon"
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = stringResource(R.string.SearchScreen_search_on_for).replace(
                    "{engine}",
                    searchEngine.name
                ).replace("{search}", searchText),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = SMALL_LABEL_STYLE
            )
        }
    }
}