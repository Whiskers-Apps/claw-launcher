package com.whiskersapps.clawlauncher.settings.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.whiskersapps.clawlauncher.shared.view.composables.WhiskersText
import com.whiskersapps.clawlauncher.shared.view.theme.REGULAR_LABEL_STYLE
import com.whiskersapps.clawlauncher.shared.view.theme.SMALL_LABEL_STYLE


val topShape =
    RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 4.dp, bottomEnd = 4.dp)

val middleShape =
    RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp, bottomStart = 4.dp, bottomEnd = 4.dp)

val bottomShape =
    RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp, bottomStart = 16.dp, bottomEnd = 16.dp)

val singleShape =
    RoundedCornerShape(16.dp)

enum class SectionPosition {
    TOP,
    MIDDLE,
    BOTTOM,
    SINGLE
}

@Composable
fun SettingsSection(
    icon: Int,
    title: String,
    description: String,
    position: SectionPosition = SectionPosition.MIDDLE,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                when (position) {
                    SectionPosition.TOP -> topShape
                    SectionPosition.MIDDLE -> middleShape
                    SectionPosition.BOTTOM -> bottomShape
                    SectionPosition.SINGLE -> singleShape
                }
            )
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground
        )

        Column {
            WhiskersText(text = title, style = REGULAR_LABEL_STYLE)
            WhiskersText(text = description, style = SMALL_LABEL_STYLE)
        }
    }
}